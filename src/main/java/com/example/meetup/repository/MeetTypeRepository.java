package com.example.meetup.repository;

import com.example.meetup.domain.entities.MeetTypeEntity;
import com.example.meetup.domain.enums.MeetTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeetTypeRepository extends JpaRepository<MeetTypeEntity, Long> {

    Optional<MeetTypeEntity> findByType(MeetTypeEnum type);
}
