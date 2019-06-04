package com.cafe24.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cafe24.jblog.repository.UserDao;
import com.cafe24.jblog.vo.UserVo;
import com.cafe24.security.CheckValid.Role;

//import com.cafe24.mysite.vo.UserVo;
//import com.cafe24.mysite.vo.UserVo.UserRole;
//import com.cafe24.security.Auth.Role;

public class CheckValidInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private UserDao userDao;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		//1. Handler 종류 확인
		if( handler instanceof HandlerMethod == false)
			return true;
		
		//2. casting
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		//3. Method의 @Auth 받아오기
		CheckValid checkValid = handlerMethod.getMethodAnnotation(CheckValid.class);
		
		//4. HandlerMethod에 @Auth 없으면 Class(Type)에 @Auth를 받아오기
//		if(checkValid == null) {
//			//auth = handlerMethod.get(Auth.class);		
//			auth = handlerMethod.getBeanType().getAnnotation(Auth.class);
//		}		
		
		//5. @Auth가 안 붙어있는 경우
		if(checkValid == null) return true;			
		
		String reqServPath=request.getServletPath();
		
		
		String[] tokens = reqServPath.split("/");

		UserVo userVo = userDao.get(tokens[1], "%%");

		if (userVo == null) {
			//response.sendError(HttpServletResponse.SC_NOT_FOUND);
			response.sendRedirect(request.getContextPath());
			return false;
		}
			
		
		//6. @Auth(in class or method)가 붙어 있기 때문에
		// 		인증 여부 Check
		request.setAttribute("blogId", userVo.getId());
		request.setAttribute("blogTitle", userVo.getTitle());
		request.setAttribute("blogLogo", userVo.getLogo());
		//7. Role 가져오기
		CheckValid.Role role = checkValid.role();
		if(role == Role.EXIST)
		{
//			if(tokens.length>=3) {
//			return true;
			return true;
//		}	
		}
		// role == admin
		else
		{
			HttpSession session = request.getSession();
			if(session == null ) {
				response.sendRedirect(request.getContextPath() + "/user/login");
				return false;
			}

			UserVo authUser = (UserVo)session.getAttribute("authUser");
			if(authUser == null ) {
				response.sendRedirect(request.getContextPath());
				return false;
			}
			if(tokens[1].equals(authUser.getId()) == false) {
				response.sendRedirect(request.getContextPath() + "/"+tokens[1]);
				return false;				
			}
		}
//		
		return true;
//		//8. role이 USER 라면
//		//   인증된 모든 사용자는 접근 가능
//
//		if(role == Role.USER) {
//			response.sendRedirect(request.getContextPath());
//			return true;
//		}
//
//		if(role == Role.ADMIN) {
//			
//			if (authUser.getRole() != UserRole.ADMIN) {
//				response.sendRedirect(request.getContextPath());
//				return false;
//			}
//			
//		}
//		
//		//9. Admin Role 접근 check
//		//authuser.getrole().equals(admin);
//		
		
	}

}
