package com.cafe24.jblog.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.jblog.vo.UserVo;



@Repository
public class UserDao {
	
	@Autowired
	SqlSession sqlSession;
	
	public Boolean insert(UserVo vo) {
		int count = sqlSession.insert("user.insert", vo);
		return 1 == count;
	}
	
	public UserVo get(String id, String password) {
		Map<String,String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("password", password);
		UserVo userVo = sqlSession.selectOne("user.getByIdAndPassword", map);
		return userVo;
	}

	public Boolean updateBlog(UserVo vo) {
		int count = sqlSession.update("user.updateBlog", vo);
		return 1 == count;
	}
}
