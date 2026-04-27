package com.board.review.controller;

import com.board.review.domain.Board;
import com.board.review.dto.BoardDto;
import com.board.review.dto.CommentDto;
import com.board.review.service.BoardService;
import com.board.review.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final BoardService boardService;

    @PostMapping("/{boardId}")
    public String save(@PathVariable Long boardId,
                       @ModelAttribute CommentDto commentDto,
                       RedirectAttributes redirectAttributes) {

        commentService.save(boardId, commentDto);

        // 댓글 작성 후 다시 그 상세 페이지로 돌아가되, 조회수 증가 방지 파라미터 추가
        redirectAttributes.addAttribute("isUpdated", true);

        return "redirect:/board/detail/" + boardId;
    }
}
