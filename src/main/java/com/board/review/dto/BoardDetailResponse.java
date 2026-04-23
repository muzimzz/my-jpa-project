package com.board.review.dto;

import com.board.review.domain.Board;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BoardDetailResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final String nickname;
    private final LocalDateTime createdAt;
    private final int likes;
    private final int views;
    private final List<CommentResponse> comments;

    public BoardDetailResponse(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.nickname = board.getMember() != null ? board.getMember().getNickname() : "알 수 없는 사용자";
        this.createdAt = board.getCreatedAt();
        this.likes = board.getLikes();
        this.views = board.getViews();

        // 중요: 엔티티 리스트를 DTO 리스트로 변환합니다.
        // 이 과정에서 지연 로딩된 댓글들이 "실제 데이터"로 로딩됩니다.
        this.comments = board.getComments().stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }

}
