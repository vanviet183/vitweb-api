package com.vitweb.vitwebapi.application.repositories;

import com.vitweb.vitwebapi.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

  @Query("select u from User u where u.email = ?1")
  Optional<User> findByEmail(String email);

  @Query("select u from User u where u.idName = ?1")
  Optional<User> findByIdName(String idName);
}
