package br.com.fervo.FervoApp.api.controller;

import br.com.fervo.FervoApp.dto.location.*;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private static final String LOCATION_URL = "https://maps.googleapis.com/maps/api/geocode/json?latlng=CORDENADAS&key=AIzaSyA5QDW1a0Mx90c9wxstHslqhAB5ufUievM";

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserPointsRepository userPointsRepository;

    @Autowired
    private UserLocationRepository userLocationRepository;

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

    @GetMapping("/testMongo")
    public List<UserLocationDTO> testMongo() {
        UserLocationDTO userLocationDTO = new UserLocationDTO();
        userLocationDTO.setCity("Maringá");
        userLocationDTO.setCreationDate(DateTime.now());
        userLocationDTO.setLatitude("-23.4412418");
        userLocationDTO.setLongitude("-51.918569700000006");
        userLocationDTO.setState("Pr");
        userLocationDTO.setUserId(1234L);
        userLocationRepository.save(userLocationDTO);
        return userLocationRepository.findAll();
    }

    @GetMapping("/getMongo")
    public List<UserLocationDTO> getMongo() {
        return userLocationRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/getLocations")
    public List<GoogleMapsDTO> getLocations(@RequestParam String lat, @RequestParam String lon){
//        Optional<Coordenates> coordenates = getCityByCoordenates(lat, lon);
//        List<UserLocationDTO> usersLocations = userLocationRepository.findAllByCity(coordenates.get().getCity());
        List<UserLocationDTO> usersLocations = userLocationRepository.findAll();
        List<GoogleMapsDTO> googleMaps =  new ArrayList<>();
        usersLocations.forEach(location -> {
            GoogleMapsDTO googleMapsDTO = new GoogleMapsDTO(location.getLatitude(), location.getLongitude());
            googleMaps.add(googleMapsDTO);
        });
        return googleMaps;
    }

    @CrossOrigin
    @GetMapping("/testLocation")
    public Optional<Coordenates> testCidade(@RequestParam String lat, @RequestParam String lon){
        return getCityByCoordenates(lat, lon);
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

    @CrossOrigin
    @PostMapping("/insertLocation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void insertLocation(@RequestParam String lat, @RequestParam String lon){
        LOGGER.info("lat: {} , lon: {}", lat, lon);
        UserLocationDTO userLocationDTO = new UserLocationDTO();
        try {
            userLocationDTO.setCity(getCityByCoordenates(lat, lon).get().getCity());
        } catch (ResourceAccessException e){
            LOGGER.error("Não foi possível realizar a chamada, Causa: {}", e.getLocalizedMessage());
        }
        userLocationDTO.setCreationDate(DateTime.now());
        userLocationDTO.setLatitude(lat);
        userLocationDTO.setLongitude(lon);
        userLocationDTO.setState("Pr");
        userLocationDTO.setUserId(1234L);
        userLocationRepository.save(userLocationDTO);
    }

    @GetMapping("/getLocation")
    public List<UserLocationDTO> getLocation(@RequestParam String lat, @RequestParam String lon){
        LOGGER.info("passou");
        Optional<Coordenates> coordenates = getCityByCoordenates(lat, lon);
            return userLocationRepository.findAllByCity(coordenates.get().getCity());
    }

    private Optional<Coordenates> getCityByCoordenates(String lat, String lon) {
        LOGGER.info("pegou coordenadas");
        RestTemplate restTemplate = new RestTemplate();
        GoogleReverseDTO googleReverseDTO;
        String userCurrentCity = LOCATION_URL.replace("CORDENADAS", (lat.concat(",").concat(lon)));
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<GoogleReverseDTO> response = restTemplate.exchange(userCurrentCity, HttpMethod.GET, entity, GoogleReverseDTO.class);
        googleReverseDTO = response.getBody();
        if (!Objects.isNull(googleReverseDTO)) {
            List<AddressComponentsDTO> addressComponentsDTO = googleReverseDTO.getResults().get(0).getAddressComponents();
            return Optional.of(new Coordenates(lat, lon, addressComponentsDTO.get(3).getLongName(),
                    addressComponentsDTO.get(4).getLongName(), addressComponentsDTO.get(5).getLongName()));
        }
        return Optional.empty();
    }

    @PostMapping("/recalculatePoints")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void recalculatePoints(@RequestParam Long userId, @RequestParam Integer points) {
        UserPointsDTO userPoints = userPointsRepository.findByUserId(userId);
        Integer currentPoints = userPoints.getPoints();
        userPoints.setPoints(currentPoints + points);
    }

}
