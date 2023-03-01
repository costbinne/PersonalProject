package blog.example.controller;

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

import blog.example.model.entity.CategoryEntity;
import blog.example.model.entity.UserEntity;
import blog.example.service.CategoryService;
import blog.example.service.UserService;





@Controller
@RequestMapping("/admin/category")
public class CategoryController {


	@Autowired
	private CategoryService categoryService;

	@Autowired
	private UserService userService;

	@GetMapping("/all")
	public String getCategoryAll(Model model) {
	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		String userEmail = auth.getName();

		UserEntity user = userService.selectById(userEmail);

		String userName = user.getUserName();

		List<CategoryEntity>categorylist = categoryService.findByAll();


		model.addAttribute("categorylist",categorylist);
		model.addAttribute("userName",userName);

		return "admin_category_all.html";
	}

	@GetMapping("/register")
	public String geCategoryListRegister(Model model) {
	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userEmail = auth.getName();
		UserEntity user = userService.selectById(userEmail);
		String userName = user.getUserName();
		model.addAttribute("userName",userName);
		return "admin_category_register.html";
	}

	@PostMapping("/register")
	public String register(@RequestParam String categoryName) {
		categoryService.insert(categoryName);
		return "redirect:/admin/category/all";
	}


	@GetMapping("/edit/{categoryId}")
	public String getCategroyEditPage(@PathVariable Long categoryId,Model model) {
	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userEmail = auth.getName();
		UserEntity user = userService.selectById(userEmail);
		String userName = user.getUserName();
		CategoryEntity categoryEntity = categoryService.selectCategoryId(categoryId);

		model.addAttribute("userName",userName);
		model.addAttribute("category",categoryEntity);
		return "admin_category_edit.html";
	}


	@PostMapping("/update")
	public String update(@RequestParam String categoryName,@RequestParam Long categoryId) {
		categoryService.update(categoryId, categoryName);
		return "redirect:/admin/category/all";
	}

	@PostMapping("/delete")
	public String deleteCategory(@RequestParam Long categoryId) {
		categoryService.delete(categoryId);
		return "redirect:/admin/category/all";
	}


}

