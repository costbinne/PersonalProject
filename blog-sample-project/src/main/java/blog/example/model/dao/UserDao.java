package blog.example.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blog.example.model.entity.UserEntity;

@Repository
public interface UserDao extends JpaRepository<UserEntity, Long> {
	UserEntity findByUserEmail(String userEmail);
	
	UserEntity save(UserEntity userEntity);
	
	List<UserEntity> findAll();
}
