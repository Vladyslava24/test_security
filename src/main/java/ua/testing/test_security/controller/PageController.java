package ua.testing.test_security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.testing.test_security.entity.RoleType;
import ua.testing.test_security.entity.User;


@Controller
public class PageController {
    @RequestMapping("/")
    public String getMainPage(){
        return "index.html";
    }

    @RequestMapping("/login")
    public String getLogin(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "logout", required = false) String logout,
                           Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "login.html";
    }

    @RequestMapping("/admin")
    public String usersPage(){
        if (role().equals(RoleType.ROLE_ADMIN))
            return "admin.html";
        else
            return "access_denied.html";
    }
    @RequestMapping("/registration")
    public String regForm(){
        return "registration.html";
    }

    public RoleType role(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user.getRole();
    }
}