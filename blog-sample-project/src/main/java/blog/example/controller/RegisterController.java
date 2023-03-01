package blog.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import blog.example.service.UserService;

@Controller
@RequestMapping("/admin")
public class RegisterController {
	@Autowired
	private UserService accountService;
	
	@GetMapping("/register")
	public String getRegisterPage() {
		return "admin_register.html";
	}
	
	@PostMapping("/register")
	public String register(@RequestParam String userName,@RequestParam String userEmail,
			@RequestParam String password) {
		if(accountService.createAccount(userName,userEmail,password)) {
			return "redirect:/admin/login";
		}else {
			return "admin_register.html";
		}
	}
}
