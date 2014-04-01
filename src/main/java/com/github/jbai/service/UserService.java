package com.github.jbai.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.jbai.APIException;
import com.github.jbai.entity.User;
import com.github.jbai.repository.UserRepository;
import com.github.jbai.util.SecurityUtil;

@Service("userService")
public class UserService {
	
	private @Value("${app.image.store.path}") String path;
	
	@Autowired
	private UserRepository userRepository;

	@Transactional
	public User addUser(User u) {
		if (u.getLoginName() == null || u.getName() == null || u.getPassword() == null) {
			throw new APIException(400, "bad request");
		}
		List<User> users = userRepository.findByLoginName(u.getLoginName());
		if (users.size() > 0) {
			throw new APIException(400, "duplicated login name");
		}		
		//Encrypt password for security
		u.setPassword(SecurityUtil.encrypt(u.getName()));		
		return userRepository.saveAndFlush(u);
	}

	public List<User> findAllUsers() {
		return userRepository.findAll();
	}
	
	@Transactional
	public void updateProfile(long userId, MultipartFile file) throws Exception {
		MediaType mediaType = MediaType.parseMediaType(file.getContentType());		
		File f = new File(path + File.separator + System.currentTimeMillis() + "." + mediaType.getSubtype());
		file.transferTo(f);
		User user = userRepository.findOne(userId);
		user.setImagePath(f.getAbsolutePath());
		userRepository.saveAndFlush(user);
	}
}
