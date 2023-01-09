package com.vitweb.vitwebapi.application.repositories;

import com.vitweb.vitwebapi.domain.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {

}
