package com.example.meetup.service;

import com.example.meetup.domain.customExceptions.ObjectNotFoundException;
import com.example.meetup.domain.dto.binding.AddCommentDTO;
import com.example.meetup.domain.dto.binding.CommentDTO;
import com.example.meetup.domain.entities.CommentEntity;
import com.example.meetup.domain.entities.MeetEntity;
import com.example.meetup.domain.entities.UserEntity;
import com.example.meetup.repository.CommentRepository;
import com.example.meetup.repository.MeetRepository;
import com.example.meetup.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//ErrorHandling implemented

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final MeetRepository meetRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, MeetRepository meetRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.meetRepository = meetRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public List<CommentEntity> getCommentsByMeetId(Long meetId){
        List<CommentEntity> meets = this.commentRepository.findAllByMeet_Id(meetId);

        if(meets == null){
            throw new ObjectNotFoundException(meetId.toString(), "MeetID", "List of Comments");
        }

        return meets;
    }

    public void saveComment(AddCommentDTO addCommentDTO){
        MeetEntity meet = this.modelMapper.map(this.meetRepository.findById(addCommentDTO.getMeetId()), MeetEntity.class);

        if(meet == null){
            throw new ObjectNotFoundException(addCommentDTO.getMeetId().toString(), "MeetID", "Meet");
        }

        CommentEntity commentToSave = new CommentEntity()
                .setAuthor(this.modelMapper.map(this.userRepository.findById(addCommentDTO.getAuthorId()), UserEntity.class))
                .setMeet(meet)
                .setContent(addCommentDTO.getContent());

        this.commentRepository.save(commentToSave);
    }

    public Long getCommentIdByContent(String content){
        CommentEntity comment = this.modelMapper.map(this.commentRepository.findByContent(content), CommentEntity.class);

        if(comment == null){
            throw new ObjectNotFoundException(content, "Content", "Comment");
        }

        return comment.getId();
    }
}
