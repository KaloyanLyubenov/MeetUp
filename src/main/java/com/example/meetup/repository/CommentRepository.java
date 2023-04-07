package com.example.meetup.repository;

import com.example.meetup.domain.entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findAllByMeet_Id(Long id);

    Optional<CommentEntity> findByContent(String content);
}
