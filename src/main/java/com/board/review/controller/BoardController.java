package com.board.review.controller;

import com.board.review.domain.Board;
import com.board.review.dto.BoardDto;
import com.board.review.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/write")
    public String saveForm() {
        return "write";
    }

    @PostMapping("/write")
    public String save(@ModelAttribute BoardDto boardDto) {

        // 아직 로그인 기능이 없어서 SampleMember를 사용
        boardService.save(boardDto);

        return "redirect:/board/list";
    }

    @GetMapping("/detail/{id}")
    public String findById(@PathVariable Long id,
                           @RequestParam(defaultValue = "false") boolean isUpdated,
                           Model model) {
        if (!isUpdated)
            boardService.updateViews(id);

        BoardDto board = boardService.findById(id);
        model.addAttribute("board", board);
        return "detail";
    }

    @PostMapping("/like/{id}")
    @ResponseBody
    public int updateLikes(@PathVariable Long id) {
        System.out.println("좋아요 버튼 클릭");
        return boardService.updateLikes(id);
    }

    @GetMapping("/list")
    public String findAll(Model model) {
        List<BoardDto> boardList = boardService.findAll();
        model.addAttribute("boards", boardList);
        return "list";
    }


    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        BoardDto board = boardService.findById(id);
        model.addAttribute("board", board);
        return "update";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute BoardDto boardDto, RedirectAttributes redirectAttributes) {
        boolean isUpdated = true;
        redirectAttributes.addAttribute("isUpdated", true);
        boardService.update(id, boardDto);
        return "redirect:/board/detail/" + id;
    }

    // @GetMapping 사용 위험 (Javascript에서는 @DeleteMapping) 사용
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        boardService.deleteById(id);
        return "redirect:/board/list";
    }
}
