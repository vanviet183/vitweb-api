package com.vitweb.vitwebapi.application.repositories;

import com.vitweb.vitwebapi.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);
}
