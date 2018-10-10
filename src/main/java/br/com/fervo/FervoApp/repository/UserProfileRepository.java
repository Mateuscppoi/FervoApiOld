package br.com.fervo.FervoApp.repository;

import br.com.fervo.FervoApp.dto.user.ProfileDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProfileRepository extends JpaRepository<ProfileDTO, Long> {



}
