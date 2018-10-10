package br.com.fervo.FervoApp.repository;

import br.com.fervo.FervoApp.dto.user.UserLoginDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserLoginRepository extends JpaRepository<UserLoginDTO, Long> {

    String GET_LOCATION = "SELECT * FROM LOCATION where city = :cidade ";

    UserLoginDTO findAllByEmailOrUsername(String email, String username);

    UserLoginDTO findFirstById(Integer id);

    @Query(value = GET_LOCATION, nativeQuery = false)
    UserLoginDTO getSeiLa(@Param("cidade") String cidade);
}
