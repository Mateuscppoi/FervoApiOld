package br.com.fervo.FervoApp.repository;

import br.com.fervo.FervoApp.dto.user.UserLoginDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginRepository extends JpaRepository<UserLoginDTO, Long> {
}
