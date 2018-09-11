package br.com.fervo.FervoApp.api;

import br.com.fervo.FervoApp.dto.user.UserLoginDTO;
import br.com.fervo.FervoApp.repository.UserLoginRepository;
import br.com.fervo.FervoApp.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserProfileRepository userRepository;

    @Autowired
    private UserLoginRepository userLoginRepository;

    @PostMapping("/createUser")
    @ResponseStatus(HttpStatus.CREATED)
    public void CreateUser(@Valid @RequestBody UserLoginDTO user){
        userLoginRepository.save(user);
    }
}
