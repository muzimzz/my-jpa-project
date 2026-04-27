package com.board.review.service;

import com.board.review.domain.Board;
import com.board.review.domain.Comment;
import com.board.review.domain.Member;
import com.board.review.dto.CommentDto;
import com.board.review.repository.BoardRepository;
import com.board.review.repository.CommentRepository;
import com.board.review.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public void save(Long boardId, CommentDto commentDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("게시글 찾을 수 없음"));

        Member sampleMember = memberRepository.findByLoginId(("sample"))
                .orElseGet(() -> memberRepository.save(Member.sample()));

        commentRepository.save(commentDto.toEntity(sampleMember, board));
    }
}
