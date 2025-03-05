package org.srebnaje.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.srebnaje.service.UserService;
import org.srebnaje.service.PostService;
import org.srebnaje.service.ServiceException;
import org.srebnaje.model.User;
import org.srebnaje.model.Post;
import jakarta.servlet.http.HttpSession;

@Controller
public class PageController {

	private final UserService userService;
	private final PostService postService;

	public PageController(UserService userService, PostService postService) {
		this.userService = userService;
		this.postService = postService;
	}

	@GetMapping("/")
	public String showHomePage() {
		return "login";
	}

	@GetMapping("/register")
	public String showRegisterPage() {
		return "register";
	}

	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}

	@GetMapping("/main")
	public String showMainPage(HttpSession session, Model model) {
		try {
			Long userId = (Long) session.getAttribute("userId");

			if (userId == null) {
				return "redirect:/login";
			}

			User user = userService.getUserById(userId);
			session.setAttribute("currentUser", user);

			List<Post> posts = postService.getAllPosts();

			model.addAttribute("username", user.getUsername());
			model.addAttribute("posts", posts);

			return "main";
		} catch (ServiceException e) {
			model.addAttribute("error", "Error loading the main page: " + e.getMessage());
			return "errorPage";
		}
	}
}
