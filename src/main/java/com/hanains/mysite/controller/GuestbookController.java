package com.hanains.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hanains.mysite.service.GuestbookService;
import com.hanains.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {

		@Autowired
		private GuestbookService guestbookService;
		
		@RequestMapping("")
		public String list(Model model){
			List<GuestbookVo> list = guestbookService.listMessage();
			model.addAttribute("list",list);
			return "/guestbook/list";
		}
		@RequestMapping("/insert")
		public String insert(@ModelAttribute GuestbookVo vo){
			guestbookService.writeMessage(vo);
			return "redirect:/guestbook";
		}
		@RequestMapping("/deleteform/{no}")
		public String deleteform(@PathVariable("no") Long no , Model model){
			model.addAttribute("no",no);
			return "guestbook/deleteform";
		}
		@RequestMapping("/delete")
		public String delete(@ModelAttribute GuestbookVo vo){
			guestbookService.deleteMessage(vo);
			return "redirect:/guestbook";
		}
}
