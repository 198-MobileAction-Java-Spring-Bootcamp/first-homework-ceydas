package com.ceydog.mobileactionbootcamp.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`user_comment`") //DB objects named with lower case letters for conventional purposes
public class UserComment {
    @Id
    @SequenceGenerator(name = "UserComment", sequenceName = "USERCOMMENT_ID_SEQ")
    @GeneratedValue(generator = "UserComment")
    @Column (name = "comment_id", nullable = false)
    private Long userCommentId;

    @Column (name = "comment", nullable = false)
    private String userComment;

    @Temporal(TemporalType.DATE)
    private Date userCommentDate;

    @Column (name = "product_id", nullable = false)
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY) //Load the user information when needed.
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User commentOwner;






}
