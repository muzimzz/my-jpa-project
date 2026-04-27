package com.board.review.dto;

import com.board.review.domain.Board;
import com.board.review.domain.Comment;
import com.board.review.domain.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    private String content;
    private String nickname;
    private LocalDateTime createdAt;

//    public CommentDto(Comment comment) {
//        this.id = comment.getId();
//        this.content = comment.getContent();
//        // (주의) 여기서 comment.getMember()를 호출할 때
//        // 트랜잭션이 살아있어야 지연 로딩이 성공합니다!
//        this.nickname = comment.getMember() != null ? comment.getMember().getNickname() : "익명";
//        this.createdAt = comment.getCreatedAt();
//    }

    public Comment toEntity(Member member, Board board) {
        return Comment.builder()
                .content(this.content)
                .member(member)
                .board(board)
                .build();
    }

    public static CommentDto from(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .nickname(comment.getMember() != null ? comment.getMember().getNickname() : "익명")
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
