package br.web.repository;

import br.web.entities.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends DistributedRepository<User>{
}
