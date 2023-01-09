package com.vitweb.vitwebapi.application.repositories;

import com.vitweb.vitwebapi.domain.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPostRepository extends JpaRepository<Post, Long> {

}
