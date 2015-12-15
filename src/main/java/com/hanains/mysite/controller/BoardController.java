package com.hanains.mysite.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hanains.mysite.service.BoardService;


@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	
	@RequestMapping("")
	public String list(@RequestParam(value="kw",required=true,defaultValue="") String searchKeyword,
					@RequestParam(value="p",required=true,defaultValue="1") Long page,
					Model model){
		Map<String,Object> map = boardService.listBoard(searchKeyword,page);
		model.addAttribute("listData",map);
		return "/board/list";
	}
	


}
