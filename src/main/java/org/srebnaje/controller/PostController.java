package org.srebnaje.controller;

import org.srebnaje.model.Post;
import org.srebnaje.model.User;
import org.srebnaje.service.PostService;
import org.srebnaje.service.ServiceException;
import org.srebnaje.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

	private final PostService postService;

	@Autowired
	public PostController(PostService postService, UserService userService) {
		this.postService = postService;

	}

	@GetMapping("/all")
	public String getAllPosts(HttpSession session, Model model) {
		try {
			List<Post> posts = postService.getAllPosts();
			model.addAttribute("posts", posts);

			User currentUser = (User) session.getAttribute("currentUser");
			if (currentUser != null) {
				model.addAttribute("username", currentUser.getUsername());
			} else {
				return "redirect:/login";
			}

		} catch (ServiceException e) {
			model.addAttribute("error", "Error getting posts: " + e.getMessage());
			return "errorPage";
		}
		return "main";
	}

	@GetMapping("/add")
	public String showPostFormPage(HttpSession session, Model model) {
		User currentUser = (User) session.getAttribute("currentUser");

		if (currentUser == null) {
			return "redirect:/login";
		}

		model.addAttribute("username", currentUser.getUsername());
		return "postForm";
	}

	@PostMapping("/add")
	public String addPost(@RequestParam String title, @RequestParam String content, HttpSession session, Model model) {
		User currentUser = (User) session.getAttribute("currentUser");

		if (currentUser == null) {
			return "redirect:/login";
		}

		try {
			Post post = new Post();
			post.setTitle(title);
			post.setContent(content);
			post.setAuthor(currentUser);

			postService.createPost(post);

			return "redirect:/posts/all";
		} catch (ServiceException e) {
			model.addAttribute("error", "Error adding post: " + e.getMessage());
			return "errorPage";
		}
	}

	@GetMapping("/author/{username}")
	public String getPostsByAuthor(@PathVariable String username, HttpSession session, Model model) {
		try {
			List<Post> authorPosts = postService.getPostsByAuthor(username);

			model.addAttribute("author", username);
			model.addAttribute("posts", authorPosts);

			User currentUser = (User) session.getAttribute("currentUser");
			if (currentUser != null) {
				model.addAttribute("username", currentUser.getUsername());
			} else {
				return "redirect:/login";
			}

		} catch (ServiceException e) {
			model.addAttribute("error", "Error getting posts by author: " + username);
			return "errorPage";
		}
		return "author-posts";
	}

}
