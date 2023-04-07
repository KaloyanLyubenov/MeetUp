package com.example.meetup.repository;

import com.example.meetup.domain.entities.MeetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetRepository extends JpaRepository<MeetEntity, Long> {

    List<MeetEntity> findFirst4ByOrderByIdAsc();

    List<MeetEntity> findAllByAnnouncer_Id(Long id);
}
