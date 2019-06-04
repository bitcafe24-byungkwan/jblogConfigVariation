package com.cafe24.jblog.controller;

import java.util.List;

import javax.validation.Valid;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe24.jblog.service.BlogService;
import com.cafe24.jblog.service.UserService;
import com.cafe24.jblog.vo.CategoryVo;
import com.cafe24.jblog.vo.PostVo;
import com.cafe24.jblog.vo.UserVo;
import com.cafe24.security.CheckValid;
import com.cafe24.security.CheckValid.Role;




@Controller
public class BlogController {
	@Autowired BlogService blogService;
	@Autowired UserService userService;
	
	@CheckValid(role=Role.EXIST )
	@RequestMapping(value = {"/{idx:[\\w]+}"} ,method = RequestMethod.GET)
	public String list(@PathVariable String idx, Model model) {
		
		if("logos".equals(idx))
			return "main/index";
		PostVo viewPost = blogService.getPost(idx,-1L);
		//System.out.println(viewPost);
		//model.addAttribute("selectedPost",viewPost);
		if(viewPost==null) {
			return "blog/blog-main";
		}
		
		return String.format("redirect:/%s/0/%d", idx, viewPost.getId());
	}
	
	@CheckValid(role=Role.EXIST )
	@RequestMapping(value = {"/{blogId:[\\w]+}/{catId:[\\d]+}/{postId:[\\d]+}"} ,method = RequestMethod.GET)
	public String blogMain(@PathVariable String blogId,
							@PathVariable Long catId,
							@PathVariable Long postId,
							Model model){
		
		List<CategoryVo> catLists = blogService.getCategoryList(blogId);
		model.addAttribute("categoryList", catLists);
		
		model.addAttribute("curCatId", catId);
		model.addAttribute("curPostId", postId);
		
		List<PostVo> postLists = blogService.getPostList(blogId,catId);
		model.addAttribute("postList",postLists);
		
		PostVo viewPost = blogService.getPost(blogId,postId);

		model.addAttribute("selectedPost",viewPost);
		return "blog/blog-main";
	}
	
	@CheckValid(role=Role.EXIST )
	@RequestMapping(value = {"/{idx:[\\w]+}/{idx2:[\\d]+}"},method = RequestMethod.GET)
	public String list2(@PathVariable String idx,@PathVariable Long idx2) {
		return String.format("redirect:/%s", idx);
	}
	
	
	@CheckValid(role=Role.ADMIN )
	@RequestMapping(value = {"/{id:[\\w]+}/admin/basic","/{id:[\\w]+}/admin"},method = RequestMethod.GET)
	public String adminBasic(@PathVariable String id,Model model) {
		UserVo user= new UserVo();
		user.setId(id);
		user.setPassword("%%");
		user = userService.getUser(user);
		
		model.addAttribute("blogInfo", user);
		return "blog/blog-admin-basic";
	}
	@CheckValid(role=Role.ADMIN )
	@RequestMapping(value = {"/{userId:[\\w]+}/admin/basic"},method = RequestMethod.POST)
	public String updateBlogInfo(@PathVariable String userId, UserVo vo, BindingResult result, Model model) {
		System.out.println(userId);
		System.out.println(vo);
		
		if(result.hasErrors()) {
			List<ObjectError> errors=result.getAllErrors();
			for(ObjectError error : errors)
				System.out.println(error.toString());
			model.addAllAttributes(result.getModel());
			return "blog/blog-admin-basic";
		}

		userService.updateBlog(vo,userId);
		return String.format("redirect:/%s/admin/basic", userId);
	}
	
	
	
	
	
	@CheckValid(role=Role.ADMIN )
	@RequestMapping(value = {"/{id:[\\w]+}/admin/category"},method = RequestMethod.GET)
	public String blogCat(@PathVariable String id, Model model) {
		List<CategoryVo> lists = blogService.getCategoryList(id);

		model.addAttribute("categoryList", lists);
		Integer catCount = blogService.getCategoryCount(id,0L);
		model.addAttribute("unclassifiedCount",catCount);
		return "blog/blog-admin-category";
	}
	
	@CheckValid(role=Role.ADMIN )
	@RequestMapping(value = {"/{id:[\\w]+}/admin/category"},method = RequestMethod.POST)
	public String addCat(@PathVariable String id, String name, String description) {		
		blogService.addCategory(id,name,description);
		return String.format("redirect:/%s/admin/category", id);
	}
	
	@CheckValid(role=Role.ADMIN )
	@RequestMapping(value = {"/{usrId:[\\w]+}/admin/category/delete/{catIdx:[\\d]+}"},method = RequestMethod.GET)
	public String delCat(@PathVariable String usrId, @PathVariable Long catIdx) {		
		blogService.delete(usrId, catIdx);
		
		return String.format("redirect:/%s/admin/category", usrId);
	}
	
	@CheckValid(role=Role.ADMIN )
	@RequestMapping(value = {"/{userId:[\\w]+}/admin/write"},method = RequestMethod.GET)
	public String postFrom(@PathVariable String userId, Model model) {
	
		List<CategoryVo> lists = blogService.getCategoryList(userId);
		model.addAttribute("categoryList", lists);
		
		return "blog/blog-admin-write";
	}
	
	@CheckValid(role=Role.ADMIN )
	@RequestMapping(value = {"/{userId:[\\w]+}/admin/write"},method = RequestMethod.POST)
	public String writePost(@Valid PostVo postVo, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			List<ObjectError> errors=result.getAllErrors();
			for(ObjectError error : errors)
				System.out.println(error.toString());
			model.addAllAttributes(result.getModel());
			return "blog/blog-admin-write";
		}
		blogService.addWrite(postVo);
		
		return "blog/blog-admin-write";
	}
		
	
}
