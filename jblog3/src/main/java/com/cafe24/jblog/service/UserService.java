package com.cafe24.jblog.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafe24.jblog.repository.UserDao;
import com.cafe24.jblog.vo.UserVo;

@Service
public class UserService {
	@Autowired
	UserDao userDao;

	private final String SAVE_PATH = "/jblog2-uploads";

	public UserVo getUser(UserVo userVo) {
		return userDao.get(userVo.getId(), userVo.getPassword());
	}

	// @Transactional
	public Boolean join(UserVo userVo) {
		userVo.setTitle(userVo.getName() + "의 블로오오오그");
		return userDao.insert(userVo);
	}

	public Boolean updateBlog(UserVo vo, String userId) {

		vo.setTitle(vo.getTitle().replaceAll("(?i)<script", "&lt;script"));
		try {
			if (!vo.getAttach().isEmpty()) {
				String logo = vo.getAttach().getOriginalFilename();
				byte[] fileData = vo.getAttach().getBytes();

				OutputStream os = new FileOutputStream(SAVE_PATH + "/" + logo);
				os.write(fileData);
				os.close();

				vo.setLogo(logo);
			}
			vo.setId(userId);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 이미지 안바끼는경 Wu
//	      if(blogvo.getAttach().isEmpty()) {
//	         return blogdao.updateBlogInfoExceptImage(blogvo);
//	      }
		// return blogdao.updateBlogInfo(blogvo);

		return userDao.updateBlog(vo);
	}

	public Boolean existEmail(String userId) {
		
		return ((userDao.get(userId, "%%")) != null);
		
	}
}
