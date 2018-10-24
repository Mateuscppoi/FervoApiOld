package br.com.fervo.FervoApp.repository;

import br.com.fervo.FervoApp.dto.location.UserLocationDTO;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserLocationRepository extends MongoRepository<UserLocationDTO, Long> {

    List<UserLocationDTO> findAllByCity(String city);
    List<UserLocationDTO> findAllByUserIdNotNull();

    List<UserLocationDTO> findAllByCreationDateBefore(DateTime date);
}
