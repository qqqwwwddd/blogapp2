package com.cos.blogapp2.web.dto;

import com.cos.blogapp2.domain.board.Board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BoardSaveReqDto {
	private String title;
	private String content;

	public Board toEntity() {
		Board board = Board.builder()
				.title(title)
				.content(content)
				.build();
		return board;
	}
}
