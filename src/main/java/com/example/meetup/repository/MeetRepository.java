package com.example.meetup.repository;

import com.example.meetup.domain.entities.MeetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetRepository extends JpaRepository<MeetEntity, Long> {
}
