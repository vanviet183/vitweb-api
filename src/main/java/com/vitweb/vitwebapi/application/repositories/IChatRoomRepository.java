package com.vitweb.vitwebapi.application.repositories;

import com.vitweb.vitwebapi.domain.entities.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
