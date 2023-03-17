package com.example.meetup.repository;

import com.example.meetup.domain.entities.VehicleTypeEntity;
import com.example.meetup.domain.enums.VehicleTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleTypeEntity, Long> {

    Optional<VehicleTypeEntity> findByType(VehicleTypeEnum type);

}
