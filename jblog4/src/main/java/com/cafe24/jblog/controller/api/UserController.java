package com.cafe24.jblog.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe24.jblog.service.UserService;



@Controller("userAPIController")
@RequestMapping("/user/api")
public class UserController {
	
	@Autowired
	private UserService userService;
	

	@ResponseBody
	@GetMapping("/checkIdDup")
	public JSONResult checkId(
			@RequestParam(value="blogId", required=true, defaultValue = "") String blogId) {

		Boolean isExist = userService.existEmail(blogId);
		
		JSONResult res = JSONResult.success(isExist);
//		res.setResult("");
//		res.setResult("");
//		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("result", "success");
//		map.put("data", isExist);
		//map.put("message", "....."); //error message
		
		return res;
	}
	
}
