package com.ssafy.deathnotelive.repository;

import com.ssafy.deathnotelive.entity.Participants;
import com.ssafy.deathnotelive.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantsRepository extends JpaRepository<Participants, Long> {

    List<Participants> findAllByRoom(Room room);
}
