package com.cafe24.jblog.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class MainController {
	//@Autowired
	//private MainService mainService;
		
	@RequestMapping({"/","/main"})
	public String main(Model model) {
		
	//	MainVo vo = mainService.getMainSettings();

//		model.addAttribute("mainvalues",vo);
		return "main/index";
	}
	
//	@ResponseBody
//	@RequestMapping("/hello")
//	public String hello() {
//		
//		return "<h1>hello</h1>";
//	}
//	
//	@ResponseBody
//	@RequestMapping("/hello2")
//	public UserVo hello2() {
//		UserVo vo = new UserVo();
//		vo.setNo(10L);;
//		vo.setName("안대혁");
//		vo.setEmail("kicksl@hgam.co");		
//		return vo;
//	}
	
}
