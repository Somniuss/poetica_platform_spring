package org.srebnaje.controller;

import org.srebnaje.model.User;
import org.srebnaje.service.ServiceException;
import org.srebnaje.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("username") String username,
                            @RequestParam("password") String password, 
                            HttpSession session, 
                            Model model) {
        try {
            
            if (!userService.authenticateUser(username, password)) {
                model.addAttribute("error", "Invalid username or password.");
                return "login";
            }

            
            User user = userService.getUserByName(username);

            
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());

            return "redirect:/main";
        } catch (ServiceException e) {
            model.addAttribute("error", "Login error: " + e.getMessage());
            return "errorPage";
        }
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username, 
                               @RequestParam("password") String password,
                               Model model) {
        try {
            // Validation, in production 2 turn to 7
            if (password.length() < 2) {
                model.addAttribute("error", "Password must be at least 2 characters long.");
                return "register";
            }

            
            if (userService.getUserByName(username) != null) {
                model.addAttribute("error", "A user with this username already exists.");
                return "register";
            }

            
            User newUser = new User(username, password);
            userService.addUser(newUser);

            return "redirect:/login";
        } catch (ServiceException e) {
            model.addAttribute("error", "Registration error: " + e.getMessage());
            return "errorPage";
        }
    }


    @PostMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
