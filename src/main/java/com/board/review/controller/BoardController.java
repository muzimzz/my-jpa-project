package com.board.review.controller;

import com.board.review.domain.Board;
import com.board.review.domain.Member;
import com.board.review.dto.BoardDetailResponse;
import com.board.review.service.BoardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public String findAll(Model model) {
        List<Board> boardList = boardService.findAll();
        model.addAttribute("boards", boardList);
        return "list";
    }

    @GetMapping("/write")
    public String saveForm() {
        return "write";
    }

    @PostMapping("/write")
    public String save(@ModelAttribute Board board) {

        // 아직 로그인 기능이 없어서 SampleMember를 사용
        boardService.save(board);

        return "redirect:/board/list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        BoardDetailResponse board = boardService.findById(id);
        model.addAttribute("board", board);
        return "detail";
    }


}
