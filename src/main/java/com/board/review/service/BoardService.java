package com.board.review.service;

import com.board.review.domain.Board;
import com.board.review.domain.Member;
import com.board.review.dto.BoardDto;
import com.board.review.repository.BoardRepository;
import com.board.review.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional  // fetch join 해보기
    public List<BoardDto> findAll() {

//        List<Board> boards = boardRepository.findAll();
//        List<BoardDto> boardDtoList = new ArrayList<>();
//        for (Board board : boards) {
//            boardDtoList.add(BoardDto.from(board));
//        }
//        return boardDtoList;

        return boardRepository.findAll().stream()
                .map(BoardDto::from)
                .collect(Collectors.toList());
    }

    public void save(BoardDto boardDto) {

        // 세션, 로그인 기능이 없어 일단 sample Member를 만들어 사용
        Member sampleMember = memberRepository.findByLoginId(("sample"))
                .orElseGet(() -> memberRepository.save(Member.sample()));

        Board board = boardDto.toEntity(sampleMember);

        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public BoardDto findById(Long id) {

        Board board = boardRepository.findById(id).orElseThrow(
                () -> new RuntimeException("게시글이 존재하지 않습니다."));

        // board.getComments().size();
        // LazyInitializationException을 해결하기 위한 임시방편.
        // https://freestrokes.tistory.com/176

        return BoardDto.from(board);
    }

    @Transactional
    public void update(Long id, BoardDto boardDto) {

        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글 없음"));

        board.update(boardDto);
    }

    @Transactional
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }
}
