package blog.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.example.conifg.WebSecurityConfig;
import blog.example.model.dao.UserDao;
import blog.example.model.entity.UserEntity;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	public boolean createAccount(String userName,String userEmail,String password) {
		UserEntity userEntity = userDao.findByUserEmail(userEmail);
		
		if(userEntity == null) {
			userDao.save(new UserEntity(userName,userEmail,password));
			WebSecurityConfig.addUser(userEmail, password);
			return true;
		}else {
			return false;
		}
	}
	
	public List<UserEntity> getAllAccounts(){
		return userDao.findAll();
	}	
	
	public UserEntity selectById(String userEmail) {
		return userDao.findByUserEmail(userEmail);
	}
}
