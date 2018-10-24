package br.com.fervo.FervoApp.repository;

import br.com.fervo.FervoApp.dto.rewards.UserPointsDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPointsRepository  extends JpaRepository<UserPointsDTO, Long> {

    UserPointsDTO findByUserId(Long user);
}
