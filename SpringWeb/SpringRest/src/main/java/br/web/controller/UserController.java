package br.web.controller;

import br.web.api.UserApi;
import br.web.dto.UserDTO;
import br.web.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController extends AbstractCRUDLController<User, UserDTO> {

    private UserApi userApi;

    @Autowired
    public UserController(UserApi userApi) {
        super(userApi);
    }
}
