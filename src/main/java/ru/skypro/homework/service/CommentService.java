package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdsCommentDTO;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;
    private final AdsRepository adsRepository;

    public AdsCommentDTO addAdsComment(Integer id, AdsCommentDTO adsCommentDTO, Authentication authentication) throws Exception {
        log.info("Used method  is - addAdsComment");
        User user = userRepository.findByUsernameIgnoreCase(authentication.getName()).orElseThrow(() -> new Exception("User not found"));
        Comment comment = commentMapper.toEntity(adsCommentDTO);
        comment.setAuthor(user);
        comment.setAds(adsRepository.findById(id).orElseThrow(() -> new Exception("Ad not found")));
        comment.setCreatedAt(Instant.now());
        commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }
    public List<AdsCommentDTO> getComments(Integer id) {
        log.info("Used method  is - getComments");
        return commentRepository.findAllByAdsId(id)
                .stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteAdsComment(Integer adId, Integer commentId) throws Exception {
        log.info("Used method  is - deleteAdsComment");
        Comment comment = getAdsComment( commentId,adId);
        commentRepository.delete(comment);
                log.info("Comment removed successfully");
    }

    public AdsCommentDTO updateComments( Integer adId,Integer commentId, AdsCommentDTO adsCommentDTO) throws Exception {
        log.info("Used method  is - updateComments");
        Comment comment = getAdsComment(commentId,adId);
        comment.setText(adsCommentDTO.getText());
        commentRepository.save(comment);
        return commentMapper.toDto(comment);

    }


    public Comment getAdsComment(Integer commentId, Integer adId) throws Exception {
        return commentRepository.findByIdAndAdsId(commentId, adId).orElseThrow(Exception::new);
    }
    public Comment getComment(Integer id) throws Exception {
        log.info("Used method  is - getComments");
        return commentRepository.findById(id).orElseThrow(Exception::new);     }
}


