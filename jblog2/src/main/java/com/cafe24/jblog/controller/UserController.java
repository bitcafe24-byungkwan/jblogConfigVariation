package com.cafe24.jblog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cafe24.jblog.service.UserService;
import com.cafe24.jblog.vo.UserVo;


@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired UserService userService;
	
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join() {
		
		return "user/join";
	}
	
	
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVo userVo
			,BindingResult result
			,Model model) {
		System.out.println(userVo);
		
		
		if(result.hasErrors()) {
			List<ObjectError> errors=result.getAllErrors();
			for(ObjectError error : errors)
				System.out.println(error.toString());
			model.addAllAttributes(result.getModel());
			return "user/join";
		}
		
		
		userService.join(userVo);
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping(value = "/joinsuccess")
	public String joinSuccess() {
		
		return "user/joinsuccess";
	}
	
	
	@RequestMapping(value = "/login", method = RequestMethod.GET )
	public String login() {
		return "/user/login";
	}
}
