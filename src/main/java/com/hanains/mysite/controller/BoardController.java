package com.hanains.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hanains.mysite.service.BoardService;
import com.hanains.mysite.vo.BoardVo;
import com.hanains.mysite.vo.UserVo;


@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	//리스트 요청
	@RequestMapping("")
	public String list(@RequestParam(value="kw",required=true,defaultValue="") String searchKeyword,
					@RequestParam(value="p",required=true,defaultValue="1") Long page,
					Model model){
		Map<String,Object> map = boardService.listBoard(searchKeyword,page);
		model.addAttribute("listData",map);
		return "/board/list";
	}
	
	//글 (새글/ 답급) 보기 요청
	@RequestMapping("/view/{no}")
	public String view(@PathVariable("no")Long no, Model model){
		BoardVo vo = boardService.viewBoard(no);
		model.addAttribute("vo",vo);
		return "/board/view";
		
	}
	
	//글쓰기 폼 요청
	@RequestMapping("/write")
	public String write(HttpSession session){
		//로그인 사용자체크
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null){
			return "redirect:/user/loginform";
		}
		return "/board/write";
	}
	//답글달기 폼 요청
	@RequestMapping("/reply/{no}")
	public String reply(HttpSession session, @PathVariable("no")Long no, Model model){
		UserVo authUser=(UserVo)session.getAttribute("authUser");
		if(authUser == null){
			return "redirect:/user/loginform";
		}
		BoardVo vo =boardService.viewBoard(no);
		model.addAttribute("vo",vo);
		return "/board/write";
	}
	//글(새글/답글) 등록 요청
	@RequestMapping("/insert")
	public String insert(HttpSession session, @ModelAttribute BoardVo vo){
		//로그인 사용자 체크
		UserVo authUser=(UserVo)session.getAttribute("authUser");
		if(authUser == null){
			return "redirect:/user/loginform";
			
		}
		vo.setMemberNo(authUser.getNo());
		boardService.writeBoard(vo);
		return "redirect:/board";
	}
	//글(새글/답글) 수정폼 요청
	@RequestMapping("/modify/{no}")
	public String modify(HttpSession session,@PathVariable("no") Long no, Model model){
		// 로그인 사용자 체크
		 UserVo authUser = (UserVo)session.getAttribute("authUser");
		 if(authUser == null){
			 return "redirect:/user/loginform";
		 }
		 BoardVo vo = boardService.viewBoard(no);
		 model.addAttribute("vo",vo);
		 return "/board/modify";
	
	}
	//글(새글/답글)수정요청
	@RequestMapping("/update")
	public String update(HttpSession session,@ModelAttribute BoardVo vo){
		//로그인 사용자 체크
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null){
			return "redirect:/user/loginform";
		}
		vo.setMemberNo(authUser.getNo());
		boardService.updateBoard(vo);
		return "redirect:/board";
	}
	@RequestMapping("/delete/{no}")
	public String delete(HttpSession session,@PathVariable("no") Long no){
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null){
			return "redirect:/user/loginform";
		}
		boardService.deleteBoard(no, authUser.getNo());
		return "redirect:/board";
		
	}


}
