package br.web.service;

import br.web.api.UserApi;
import br.web.converter.AbstractDTOConverter;
import br.web.converter.UserDTOConverter;
import br.web.dto.UserDTO;
import br.web.entities.User;
import br.web.repository.DistributedRepository;
import br.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractCRUDLService<User, UserDTO> implements UserApi {

    private UserRepository userRepository;
    private UserDTOConverter userDTOConverter;

    @Autowired
    public UserService(UserRepository userRepository, UserDTOConverter userDTOConverter) {
        super(userRepository, userDTOConverter);
        this.userRepository = userRepository;
        this.userDTOConverter = userDTOConverter;
    }

    @Override
    protected void updateEntity(User entity, UserDTO dto) {
        entity.setName(dto.getName());
        entity.setUsername(dto.getUsername());
    }
}
