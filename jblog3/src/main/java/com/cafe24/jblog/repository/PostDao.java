package com.cafe24.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.jblog.vo.PostVo;





@Repository
public class PostDao {
	
	@Autowired
	SqlSession sqlSession;
	
	public Boolean insert(PostVo vo) {
		int count = sqlSession.insert("post.insert", vo);
		return 1 == count;
	}

	public List<PostVo> getList(String idx, Long catNo) {
		PostVo vo = new PostVo();
		vo.setUserId(idx);
		vo.setCategoryId(catNo);
		return sqlSession.selectList("post.getListByUserIdAndCat",vo);
	}

	public PostVo get(String idx, Long postNo) {
		
		PostVo resVo = null;
		if(postNo==-1) {
			resVo=sqlSession.selectOne("post.getLatestPost",idx);
		}
		else {
			PostVo vo = new PostVo();
			vo.setUserId(idx);
			vo.setId(postNo);
			resVo=sqlSession.selectOne("post.getPostByNo",vo);			
		}
		return  resVo;
	}

	public Integer getCatCount(String id, Long catId) {
		PostVo vo = new PostVo();
		vo.setUserId(id);
		vo.setCategoryId(catId);
		
		return sqlSession.selectOne("post.getCatCount",vo);
	}


}
