package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//Утилитный GET для записи пользователя в DB.
    @GetMapping("user-role")
    public void makeUserByRole(ModelMap model) {
        userService.getUserByRole();
    }

    @GetMapping("/admin")
    public String getListOfUsers(Model model) {
        List<User> userList = userService.getAllUser();
        model.addAttribute("user", userList);
        return "user_list";
    }

    @GetMapping("admin/edit/{id}")
    public String editUser(Model model, @PathVariable("id") long id) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "update_user";
    }

    @PostMapping("admin/edit/update/{id}")
    public String editUser(@PathVariable("id") long id, User user, Model model) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("admin/delete/{id}")
    public String deleteUser(Model model, @PathVariable("id") long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

    @GetMapping("user")
    public String getCurrentUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user",user);
        return "user_info";
    }
    @GetMapping("login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }
    @GetMapping("admin/addUser")
    public String addUser(User user){
        return "add_user";
    }
    @PostMapping("/admin/saveUser")
    public String saveUser(User user){
        userService.addUser(user);
        return "redirect:/admin";
    }
}