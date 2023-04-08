package com.example.meetup.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.meetup.domain.customExceptions.ObjectNotFoundException;
import com.example.meetup.domain.entities.CommentEntity;
import com.example.meetup.domain.entities.MeetEntity;
import com.example.meetup.domain.entities.UserEntity;
import com.example.meetup.repository.CommentRepository;
import com.example.meetup.repository.MeetRepository;
import com.example.meetup.repository.UserRepository;
import com.example.meetup.service.CommentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    private final Long EXISTING_MEET_ID = (long)1;
    private final String EXISTING_MEET_TITLE = "title";
    private final String EXISTING_USER_USERNAME = "user";

    private final String EXISTING_COMMENT_CONTENT = "content";
    private final String NOT_EXISTING_COMMENT_CONTENT = "no content";

    private final Long NOT_EXISTING_MEET_ID = (long)2;

    private CommentService toTest;

    @Mock
    private MeetRepository mockMeetRepository;
    @Mock
    private CommentRepository mockCommentRepository;
    @Mock
    private ModelMapper mockModelMapper;
    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp(){
        toTest = new CommentService(mockCommentRepository,
                mockMeetRepository,
                mockModelMapper,
                mockUserRepository);
    }

    @Test
    void testCommentsFound(){
        //ARRANGE

        MeetEntity testMeet = new MeetEntity().setMeetTitle(EXISTING_MEET_TITLE);
        UserEntity testUser = new UserEntity().setUsername(EXISTING_USER_USERNAME);


        List<CommentEntity> testCommentEntities = List.of(new CommentEntity()
                .setContent("nonsense")
                .setAuthor(testUser)
                .setMeet(testMeet));


        when(mockCommentRepository.findAllByMeet_Id(EXISTING_MEET_ID))
                .thenReturn(testCommentEntities);
        //ACT

        List<CommentEntity> comments = toTest.getCommentsByMeetId(EXISTING_MEET_ID);

        //ASSERT
        Assertions.assertNotNull(comments);
        Assertions.assertEquals(EXISTING_MEET_TITLE, comments.get(0).getMeet().getMeetTitle());
        Assertions.assertEquals(EXISTING_USER_USERNAME, comments.get(0).getAuthor().getUsername());

    }

    @Test
    void testCommentsNotFound() {
        assertThrows(
                ObjectNotFoundException.class,
                () -> toTest.getCommentsByMeetId(NOT_EXISTING_MEET_ID)
        );
    }

    @Test
    void testCommentIdFoundByContent() {
        //ARRANGE
        CommentEntity testCommentEntity = new CommentEntity().setContent(EXISTING_COMMENT_CONTENT);

        testCommentEntity.setId((long)1);

        when(mockCommentRepository.findByContent(EXISTING_COMMENT_CONTENT))
                .thenReturn(Optional.of(testCommentEntity));

        when(mockModelMapper.map(Optional.of(testCommentEntity), CommentEntity.class))
                .thenReturn(testCommentEntity);


        //ACT

        Long commentId = toTest.getCommentIdByContent(EXISTING_COMMENT_CONTENT);

        //ASSERT

        Assertions.assertNotNull(commentId);
        Assertions.assertEquals((long)1, commentId);
    }

    @Test
    void testContentIDNotFoundByContent() {
        assertThrows(
                ObjectNotFoundException.class,
                () -> toTest.getCommentIdByContent(NOT_EXISTING_COMMENT_CONTENT)
        );
    }
}