package com.board.review.dto;

import com.board.review.domain.Board;
import com.board.review.domain.Member;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDto {
    private Long id;
    private String title;
    private String content;
    private String nickname;
    private LocalDateTime createdAt;
    private int likes;
    private int views;
    private List<CommentResponse> comments;

    public Board toEntity(Member member) {
        return Board.builder()
                .member(member)
                .title(this.title)
                .content(this.content)
                .build();
    }

    public static BoardDto from(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .nickname(board.getMember() != null ? board.getMember().getNickname() : "알 수 없는 사용자")
                .createdAt(board.getCreatedAt())
                .likes(board.getLikes())
                .views(board.getViews())
                // 댓글 리스트도 DTO로 변환하여 담아줍니다.
                .comments(board.getComments().stream()
                        .map(CommentResponse::new)
                        .collect(Collectors.toList()))
                .build();
    }



}
