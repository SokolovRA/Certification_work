package ru.skypro.homework.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.AdsCommentDTO;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private AdsRepository adsRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    public void testAddAdsComment() throws Exception {
        Integer adId = 1;
        AdsCommentDTO adsCommentDTO = new AdsCommentDTO();
        adsCommentDTO.setText("test comment");
        Authentication authentication = mock(Authentication.class);
        User user = new User();
        user.setUsername("user1");
        Comment comment = new Comment();
        Ads ads = new Ads();
        ads.setId(adId);
        when(userRepository.findByUsernameIgnoreCase(authentication.getName())).thenReturn(Optional.of(user));
        when(commentMapper.toEntity(adsCommentDTO)).thenReturn(comment);
        when(adsRepository.findById(adId)).thenReturn(Optional.of(ads));
        when(commentRepository.save(comment)).thenReturn(comment);

        AdsCommentDTO result = commentService.addAdsComment(adId, adsCommentDTO, authentication);

        assertEquals(comment.getAuthor(), user);
        assertEquals(comment.getAds(), ads);
        assertNotNull(comment.getCreatedAt());
        verify(commentRepository).save(comment);
        verify(commentMapper).toDto(comment);
    }

    @Test
    public void testGetComments() {

        Integer adId = 1;
        List<Comment> comments = new ArrayList<>();
        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        comments.add(comment1);
        comments.add(comment2);
        when(commentRepository.findAllByAdsId(adId)).thenReturn(comments);
        when(commentMapper.toDto(comment1)).thenReturn(new AdsCommentDTO());
        when(commentMapper.toDto(comment2)).thenReturn(new AdsCommentDTO());


        List<AdsCommentDTO> result = commentService.getComments(adId);


        assertEquals(result.size(), comments.size());
        verify(commentRepository).findAllByAdsId(adId);
        verify(commentMapper, times(comments.size())).toDto(any(Comment.class));
    }

    @Test
    public void testDeleteAdsComment() throws Exception {
        Integer adId = 1;
        Integer commentId = 1;
        Comment comment = new Comment();
        when(commentRepository.findByIdAndAdsId(commentId, adId)).thenReturn(Optional.of(comment));

        commentService.deleteAdsComment(adId, commentId);

        verify(commentRepository).findByIdAndAdsId(commentId, adId);
        verify(commentRepository).delete(comment);
    }

    @Test
    public void testUpdateComments() throws Exception {
        Integer adId = 1;
        Integer commentId = 1;
        AdsCommentDTO adsCommentDTO = new AdsCommentDTO();
        adsCommentDTO.setText("updated comment");
        Comment comment = new Comment();
        when(commentRepository.findByIdAndAdsId(commentId, adId)).thenReturn(Optional.of(comment));

        AdsCommentDTO result = commentService.updateComments(adId, commentId, adsCommentDTO);

        assertEquals(comment.getText(), adsCommentDTO.getText());
        verify(commentRepository).findByIdAndAdsId(commentId, adId);
        verify(commentRepository).save(comment);
        verify(commentMapper).toDto(comment);
    }
    @Test
    public void testGetAdsComment_WhenCommentNotFound() {
        Integer adId = 1;
        Integer commentId = 1;
        when(commentRepository.findByIdAndAdsId(commentId, adId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> commentService.getAdsComment(commentId, adId));
    }
}
