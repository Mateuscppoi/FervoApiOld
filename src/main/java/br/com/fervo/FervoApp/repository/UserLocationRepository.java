package br.com.fervo.FervoApp.repository;

import br.com.fervo.FervoApp.dto.location.UserLocationDTO;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserLocationRepository extends JpaRepository<UserLocationDTO, Long> {

    UserLocationDTO findAllByCity(String city);

    List<UserLocationDTO> findAllByCreationDateBefore(DateTime date);
}
