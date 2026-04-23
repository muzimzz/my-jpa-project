package com.board.review.dto;

import com.board.review.domain.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {
    private final Long id;
    private final String content;
    private final String nickname;
    private final LocalDateTime createdAt;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        // (주의) 여기서 comment.getMember()를 호출할 때
        // 트랜잭션이 살아있어야 지연 로딩이 성공합니다!
        this.nickname = comment.getMember() != null ? comment.getMember().getNickname() : "익명";
        this.createdAt = comment.getCreatedAt();
    }
}
