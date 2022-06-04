package com.ceydog.mobileactionbootcamp.controller;

import com.ceydog.mobileactionbootcamp.dto.UserCommentDto;
import com.ceydog.mobileactionbootcamp.entity.User;
import com.ceydog.mobileactionbootcamp.entity.UserComment;
import com.ceydog.mobileactionbootcamp.repository.UserCommentRepository;
import com.ceydog.mobileactionbootcamp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor //for commentRepository
@RequestMapping("/api/v1/comments")
public class UserCommentController {

    private final UserCommentRepository userCommentRepository;
    private final UserRepository userRepository;

    @PostMapping
    public Object addNewComment(@Valid @RequestBody UserCommentDto comment){

        //Check if user id exists.
        boolean userExists = userRepository.existsById(comment.getCommentOwnerId());

        if (!userExists){
            throw new RuntimeException("User does not exist!");
        }
        //User exists...
        User commentOwner = userRepository.findById(comment.getCommentOwnerId()).orElseThrow();
        Long userCommentId = comment.getCommentOwnerId();
        String userCommentText = comment.getUserComment();
        Date userCommentDate = comment.getUserCommentDate();
        Long productId = comment.getProductId();


        UserComment userComment = new UserComment( userCommentId, userCommentText, userCommentDate, productId, commentOwner);
        userCommentRepository.save(userComment);

        return ResponseEntity.ok(comment);
    }

    @GetMapping
    public ResponseEntity<List<UserComment>> getAllComments(){

        List<UserComment> commentList = userCommentRepository.findAll();

        if (commentList.isEmpty()) {
            throw new RuntimeException("Cannot access users, or there are no users.");
        }

        return ResponseEntity.ok(commentList);
    }

    @PatchMapping
    public ResponseEntity<UserComment> setUserPassive(@RequestParam Long commentId,
                                               @RequestParam String commentText){

        //Attempt to find comment by id
        UserComment foundComment = userCommentRepository.findById(commentId).orElseThrow();

        //If not found, an exception has been thrown.
        //Below this line is safe...
        foundComment.setUserComment(commentText);

        return new ResponseEntity<>(foundComment, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserComment> deleteComment(@PathVariable Long id){

        //Attempt to find comment by id
        UserComment foundCommentToBeDeleted = userCommentRepository.findById(id).orElseThrow();

        //If not found, an exception has been thrown.
        //Below this line is safe...
        userCommentRepository.delete(foundCommentToBeDeleted);

        return new ResponseEntity<>(foundCommentToBeDeleted, HttpStatus.CREATED);
    }
}
