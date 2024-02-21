package br.web.dto;



import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseDTO {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @JsonIgnore
    public Boolean isNew(){
        return id == null;
    }
}
