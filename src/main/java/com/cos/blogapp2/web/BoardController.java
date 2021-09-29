package com.cos.blogapp2.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blogapp2.domain.board.Board;
import com.cos.blogapp2.domain.board.BoardRepository;
import com.cos.blogapp2.domain.user.User;
import com.cos.blogapp2.web.dto.BoardSaveReqDto;
import com.cos.blogapp2.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardController {

	private final BoardRepository boardRepository;
	private final HttpSession session;
	
	@PutMapping("/board/{id}")
	public @ResponseBody CMRespDto<?> update(@PathVariable int id, @RequestBody BoardSaveReqDto dto) {
		// 1. (id, title, content) save 함수 호출
		
		User principal = (User) session.getAttribute("principal");
		
		Board board = dto.toEntity();
		board.setUser(principal);
		board.setId(id);
		boardRepository.save(board);
		
		return new CMRespDto<>(1, "성공", null);
	}
	
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("boardEntity",boardRepository.findById(id).get());
		return "board/updateForm";
		
	}
	
	
	@PostMapping("/board")
	public String save(BoardSaveReqDto dto) {
		
		Board board = dto.toEntity();
		User principal = (User) session.getAttribute("principal");
		board.setUser(principal);
		boardRepository.save(board);
		
		return "redirect:/";
	}

	@GetMapping("/board")
	public String list(Model model) {
		
		List<Board> boardsEntity = boardRepository.findAll();
		model.addAttribute("boardsEntity", boardsEntity);
		
		return "board/list";
	}
	
	@GetMapping("/board/{id}")
	public String detail(@PathVariable int id, Model model) {
		
		Board boardEntity = boardRepository.findById(id).get();
		
		model.addAttribute("boardEntity", boardEntity);
		
		return "board/detail";
	}

	@GetMapping("/board/saveForm")
	public String boardSaveForm() {
		return "board/saveForm" ;
	}
}
