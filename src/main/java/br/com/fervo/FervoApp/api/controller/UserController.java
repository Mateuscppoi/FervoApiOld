package br.com.fervo.FervoApp.api.controller;

import br.com.fervo.FervoApp.dto.location.UserLocationDTO;
import br.com.fervo.FervoApp.dto.rewards.UserPointsDTO;
import br.com.fervo.FervoApp.dto.user.ConnectionType;
import br.com.fervo.FervoApp.dto.user.ProfileDTO;
import br.com.fervo.FervoApp.dto.user.UserAccountDTO;
import br.com.fervo.FervoApp.dto.user.UserLoginDTO;
import br.com.fervo.FervoApp.repository.UserLocationRepository;
import br.com.fervo.FervoApp.repository.UserLoginRepository;
import br.com.fervo.FervoApp.repository.UserPointsRepository;
import br.com.fervo.FervoApp.repository.UserProfileRepository;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserLoginRepository userLoginRepository;

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    UserPointsRepository userPointsRepository;


    @Autowired
    UserLocationRepository userLocationRepository;

    @PostMapping("/createUser")
    @ResponseStatus(HttpStatus.CREATED)
    public String createUser(@Valid @RequestBody UserAccountDTO user){
        if(!user.getUserLogin().getConnectionType().equals(ConnectionType.LOCAL)) {
            UserLoginDTO existentUser = userLoginRepository.findAllByEmailOrUsername(user.getUserLogin().getEmail(), user.getUserLogin().getUsername());
            if (!Objects.isNull(existentUser)) {
                user.getProfile().setLoginId(userLoginRepository.save(user.getUserLogin()).getId());
                userProfileRepository.save(user.getProfile());
                return "Ok";
            }
        }
        return "deu ruim";
    }

    private boolean isLoginCreated(Integer UserId) {
        UserLoginDTO user = userLoginRepository.findFirstById(UserId);
        return !Objects.isNull(user);
    }

    @GetMapping("/ping")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String ping(){
        return "Pong";
    }

    @PostMapping("/insertLocation")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void insertLocation(@RequestBody UserLocationDTO userLocation){
        userLocationRepository.save(userLocation);
    }

//    @GetMapping("/getLocation")
//    public List<String> getLocation(){
//        return userLoginRepository.findAll().;
//    }

    @PostMapping("/recalculatePoints")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void recalculatePoints(@RequestParam Long userId, @RequestParam Integer points) {
        UserPointsDTO userPoints = userPointsRepository.findByUser(userId);
        Integer currentPoints = userPoints.getPoints();
        userPoints.setPoints(currentPoints + points);
    }

    @Scheduled(cron =  "* 0/20 * ? * *")
    public void removeCache(){
        List<UserLocationDTO> users = userLocationRepository.findAllByCreationDateBefore(DateTime.now().minusMinutes(20));
        if (users.size() > 0){
            userLocationRepository.deleteAll(users);
        }
    }

}
