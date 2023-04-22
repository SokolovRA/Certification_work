package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.AdsCommentDTO;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.wrapper.ResponseWrapper;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{id}/comments")
    public ResponseWrapper<AdsCommentDTO> getComments(@PathVariable("id") Integer id) {
        return ResponseWrapper.of(commentService.getComments(id));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<AdsCommentDTO> addAdsComment(@PathVariable("id") Integer id,
                                                       @RequestBody AdsCommentDTO adsCommentDTO,Authentication authentication) throws Exception {
        return ResponseEntity.ok(commentService.addAdsComment(id,adsCommentDTO,authentication));

    }
    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<AdsCommentDTO> updateComments(@PathVariable("adId") Integer adId,
                                                        @PathVariable("commentId") Integer commentId,
                                                        @RequestBody AdsCommentDTO adsCommentDto) throws Exception {
        return ResponseEntity.ok(commentService.updateComments(adId, commentId, adsCommentDto));
    }
    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<?> deleteAdsComment(@PathVariable("adId") Integer adId,
                                                       @PathVariable("commentId") Integer commentId) throws Exception {
        commentService.deleteAdsComment(adId, commentId);
        return ResponseEntity.ok().build();
    }
}


