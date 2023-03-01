package blog.example.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import blog.example.model.entity.BlogEntity;
import blog.example.model.entity.UserEntity;
import jakarta.transaction.Transactional;

@Repository
public interface BlogDao extends JpaRepository<BlogEntity, Long> {
	BlogEntity save(BlogEntity blogEntity);
	
	@Query(value="SELECT b.blog_id,b.blog_title,b.blog_image,b.category_name,b.message,b.user_id From blog b INNER JOIN account a ON b.user_id = a.user_id WHERE b.user_id=?1",nativeQuery = true)
	List<BlogEntity>findByUserId(Long userId);
	//使用blogId在DB里面检索
		BlogEntity findByBlogId(Long blogId);
		
		//blogtable的所有情报取得
		List<BlogEntity>findAll();
		
		//使用category名去DB检索
		List<BlogEntity>findByCategoryName(String categoryName);

		//消除blogId对应的情报
		@Transactional
		List<BlogEntity> deleteByBlogId(Long blogId);
	}


