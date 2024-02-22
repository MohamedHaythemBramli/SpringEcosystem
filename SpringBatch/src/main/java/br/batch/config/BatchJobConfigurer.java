package br.batch.config;


import br.batch.config.listener.CustomSkipListener;
import br.batch.config.partitionner.CustomPartitioner;
import br.batch.config.processor.SalesInfoProcessor;
import br.batch.config.skippolicy.CustomSkipPolicy;
import br.batch.dto.SalesInfoDTO;
import br.batch.entities.SalesInfo;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchJobConfigurer {

    private final EntityManagerFactory entityManagerFactory;
    private final SalesInfoProcessor salesInfoProcessor;
    private final CustomSkipPolicy customSkipPolicy;


    @Bean
    public Job importSalesInfo(JobRepository jobRepository,PlatformTransactionManager transactionManager){
        return new JobBuilder("importSalesInfo",jobRepository)
                .preventRestart()
                .incrementer(new RunIdIncrementer())
                .start(masterStep(jobRepository,transactionManager))
                .build();
    }

    @Bean
    public Step masterStep(JobRepository jobRepository,PlatformTransactionManager transactionManager){
        return new StepBuilder("masterStep",jobRepository)
                .partitioner(salveStepFromFileToDataBase(jobRepository,transactionManager).getName(),partitioner())
                .partitionHandler(partitionHandler(jobRepository,transactionManager))
                .build();
    }

    @Bean
    public Step salveStepFromFileToDataBase(JobRepository jobRepository,
                                   PlatformTransactionManager transactionManager){
        return new StepBuilder("fromFileToDataBase",jobRepository)
                .<SalesInfoDTO, SalesInfo>chunk(10,transactionManager)
                .reader(salesInfoFileReader())
                .processor(salesInfoProcessor)
                .writer(salesInfoItemWriter())
                .taskExecutor(taskExecutor())
                .faultTolerant()
                //.skip(FlatFileParseException.class)
                //.skipLimit(2)
                //.noSkip(CustomExceptionToNotSkip.class)
                .listener(skipListener())
                .skipPolicy(customSkipPolicy)
                .build();
    }

    @Bean
    public FlatFileItemReader<SalesInfoDTO> salesInfoFileReader(){
        return new FlatFileItemReaderBuilder<SalesInfoDTO>()
                .resource(new ClassPathResource("data/SaleInfo.csv"))
                .name("salesInfoFileReader")
                .delimited()
                .delimiter(",")
                .names(new String[]{"product","seller","sellerId","price","city","category"})
                .linesToSkip(1)
                .targetType(SalesInfoDTO.class)
                .build();
    }

    @Bean
    public JpaItemWriter<SalesInfo> salesInfoItemWriter(){
        return new JpaItemWriterBuilder<SalesInfo>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("Thread N-> :");
        return executor;
    }
    @Bean
    public CustomPartitioner partitioner() {
        return new CustomPartitioner();
    }

    public PartitionHandler partitionHandler(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        TaskExecutorPartitionHandler taskExecutorPartitionHandler = new TaskExecutorPartitionHandler();
        taskExecutorPartitionHandler.setGridSize(2); // it will create 2 partition of 1-500 and 501 to 1000
        taskExecutorPartitionHandler.setTaskExecutor(taskExecutor());
        taskExecutorPartitionHandler.setStep(salveStepFromFileToDataBase(jobRepository, transactionManager));

        return taskExecutorPartitionHandler;
    }
    @Bean
    public SkipListener<SalesInfoDTO,SalesInfo> skipListener() {
        return new CustomSkipListener();
    }

}

