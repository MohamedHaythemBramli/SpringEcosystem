package br.web.converter;

import br.web.dto.UserDTO;
import br.web.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserDTOConverter extends AbstractDTOConverter<User, UserDTO>{
    @Override
    public UserDTO convert(User entity) {
        UserDTO dto = new UserDTO();
        super.convert(entity,dto);
        dto.setUsername(entity.getUsername());
        dto.setName(entity.getName());
        return dto;
    }
}
