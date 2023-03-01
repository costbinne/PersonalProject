package blog.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.qos.logback.core.model.Model;

@Controller
@RequestMapping("/blog")
public class BlogController {
	@GetMapping("/all")
	public String getLoginPage(Model model) {
		//		現在のリクエストに紐づく Authentication を取得するには SecurityContextHolder.getContext().getAuthentication() とする。
		//		SecurityContextHolder.getContext() は、現在のリクエストに紐づく SecurityContext を返している。
		//		Authentication.getAuthorities() で、現在のログインユーザーに付与されている権限（GrantedAuthority のコレクション）を取得できる。
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		//ログインした人のメールアドレスを取得
		//String userEmail = auth.getName();
		//accountテーブルの中から、ユーザーのEmailで検索をかけて該当するユーザーのID情報を引っ張り出す。
		//UserEntity user = userService.selectById(userEmail);

		//accountテーブルの中からログインしているユーザーの名前の取得
		//String userName = user.getUserName();

		//accountテーブルの中からログインしているユーザーのIDを取得
		//Long userId = user.getUserId();

		//ブログテーブルの中からユーザーIDを使って、そのユーザーが書いたブログ記事のみを取得する
		//List<BlogEntity>blogList = blogService.selectByUserId(userId);
		//blogList（ブログの記事一覧情報）とuserName（管理者の名前）をmodelにセットし
		//admin_blog_all.htmlから参照可能にする。
		////model.addAttribute("blogList",blogList);
		////model.addAttribute("userName",userName);
		return "admin_blog_all.html";
	}
}
