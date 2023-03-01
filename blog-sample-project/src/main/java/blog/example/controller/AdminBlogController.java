package blog.example.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



import blog.example.model.entity.BlogEntity;
import blog.example.model.entity.CategoryEntity;
import blog.example.model.entity.UserEntity;
import blog.example.service.BlogService;
import blog.example.service.CategoryService;
import blog.example.service.UserService;


@Controller
@RequestMapping("/admin/blog")
public class AdminBlogController {

	@Autowired
	private UserService userService;

	@Autowired
	BlogService blogService;

	@Autowired
	CategoryService categoryServie;


	@GetMapping("/all")
	public String getLoginPage(Model model) {
	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();


		String userEmail = auth.getName();

		UserEntity user = userService.selectById(userEmail);


		String userName = user.getUserName();


		Long userId = user.getUserId();


		List<BlogEntity>blogList = blogService.selectByUserId(userId);

		model.addAttribute("blogList",blogList);
		model.addAttribute("userName",userName);
		return "admin_blog_all.html";
	}


	@GetMapping("/register")
	public String getBlogCreatePage(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		String userEmail = auth.getName();

		UserEntity user = userService.selectById(userEmail);

		Long userId = user.getUserId();

		String userName = user.getUserName();

		List<CategoryEntity>categoryList = categoryServie.findByAll();

		model.addAttribute("userId",userId);
		model.addAttribute("categoryList",categoryList);
		model.addAttribute("userName",userName);

		return "admin_blog_register.html";
	}


	@PostMapping("/register")
	public String register(@RequestParam String blogTitle,@RequestParam("blogImage") MultipartFile blogImage,@RequestParam String categoryName,@RequestParam String message,@RequestParam Long userId) {


		String fileName = blogImage.getOriginalFilename();


		try {

			File blogFile = new File("./src/main/resources/static/blog-image/"+fileName);
			byte[] bytes = blogImage.getBytes();
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(blogFile));
			out.write(bytes);
			out.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		blogService.insert(blogTitle, fileName, categoryName, message, userId);

		return "redirect:/admin/blog/all";
	}

	@GetMapping("/edit/{blogId}")
	public String getBlogDetailPage(@PathVariable Long blogId, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		String userEmail = auth.getName();
		UserEntity user = userService.selectById(userEmail);
		String userName = user.getUserName();
		Long userId = user.getUserId();
		List<CategoryEntity>categoryList = categoryServie.findByAll();
		BlogEntity blogs = blogService.selectByBlogId(blogId);

		model.addAttribute("userId",userId);
		model.addAttribute("blogs",blogs);	
		model.addAttribute("categoryList",categoryList);
		model.addAttribute("userName",userName);
		return "admin_blog_edit.html";
	}

	@PostMapping("/update")
	public String updateData(@RequestParam Long blogId,@RequestParam String blogTitle,@RequestParam("blogImage") MultipartFile blogImage,@RequestParam String categoryName,@RequestParam String message,@RequestParam Long userId) {

		String fileName = blogImage.getOriginalFilename();

		try {
			File blogFile = new File("./src/main/resources/static/blog-image/"+fileName);
			byte[] bytes = blogImage.getBytes();
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(blogFile));
			out.write(bytes);
			out.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		blogService.update(blogId, blogTitle, fileName, categoryName, message, userId);

		return "redirect:/admin/blog/all";
	}


	@PostMapping("/delete")
	public String blogDelete(@RequestParam Long blogId) {
		blogService.deleteBlog(blogId);
		return "redirect:/admin/blog/all";
	}


}