package com.board.review.service;

import com.board.review.domain.Board;
import com.board.review.domain.Member;
import com.board.review.dto.BoardDetailResponse;
import com.board.review.repository.BoardRepository;
import com.board.review.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public void save(Board formBoard) {

        // 세션, 로그인 기능이 없어 일단 sample Member를 만들어 사용
        Member sampleMember = memberRepository.findByLoginId(("sample"))
                .orElseGet(() -> memberRepository.save(Member.sample()));

        Board board = Board.builder()
                .member(sampleMember)
                .title(formBoard.getTitle())
                .content(formBoard.getContent())
                .build();

        boardRepository.save(board);

    }

    @Transactional(readOnly = true)
    public BoardDetailResponse findById(Long id) {

        Board board = boardRepository.findById(id).orElseThrow(
                () -> new RuntimeException("게시글이 존재하지 않습니다."));

        // board.getComments().size();
        // LazyInitializationException을 해결하기 위한 임시방편.
        // https://freestrokes.tistory.com/176

        return new BoardDetailResponse(board);


    }
}
