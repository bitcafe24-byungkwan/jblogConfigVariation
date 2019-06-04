package com.cafe24.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.jblog.vo.CategoryVo;



@Repository
public class CategoryDao {
	
	@Autowired
	SqlSession sqlSession;
	
	public Boolean insert(CategoryVo vo) {
		int count = sqlSession.insert("category.insert", vo);
		return 1 == count;
	}

	public List<CategoryVo> getList(String userId) {		
		List<CategoryVo> result = sqlSession.selectList("category.getListByUserId",userId);		
		return result;
	}

	public Boolean delete(CategoryVo vo) {
		int cnt = sqlSession.delete("category.delete", vo);
		return 1==cnt;
	}

}
