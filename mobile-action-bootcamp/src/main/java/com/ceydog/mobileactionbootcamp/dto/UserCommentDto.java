package com.ceydog.mobileactionbootcamp.dto;

import com.ceydog.mobileactionbootcamp.entity.User;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;


@Getter
@Data
public class UserCommentDto {
    private Long userCommentId;
    private Long commentOwnerId;
    private String userComment;
    private Date userCommentDate;
    private Long productId;
}
