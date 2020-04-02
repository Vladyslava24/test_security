package ua.testing.test_security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.testing.test_security.dto.EditionDTO;
import ua.testing.test_security.dto.UsersDTO;
import ua.testing.test_security.entity.Edition;
import ua.testing.test_security.entity.EditionStatus;
import ua.testing.test_security.entity.RoleType;
import ua.testing.test_security.entity.User;
import ua.testing.test_security.service.EditionService;
import ua.testing.test_security.service.UserService;


@Controller
public class PageController {
    private final EditionService editionService;
    private final UserService userService;

    public PageController(EditionService editionService, UserService userService) {
        this.editionService = editionService;
        this.userService = userService;
    }


    @RequestMapping("/")
    public String getMainPage(Model model){
        model.addAttribute("editions", editionService.getAllEditions());
        return "order.html";
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
    public String usersPage(Model model){
        if (role().equals(RoleType.ROLE_ADMIN)) {
            model.addAttribute("editions", editionService.getAllEditions());
            return "admin";
        }
        else{
            model.addAttribute("username", userName());
        }
        return "access_denied";
    }

    @RequestMapping("/catalog")
    public String catalogPage(Model model){
        if (role().equals(RoleType.ROLE_ADMIN)) {
            model.addAttribute("editions", editionService.getAllEditions());
            return "catalog_admin";
        }
        else{
            model.addAttribute("username", userName());
        }
        return "access_denied";
    }

    @RequestMapping("/registration")
    public String regForm(){
        return "registration.html";
    }

    @RequestMapping("/guest")
    public String getGuestPage(Model model){
        model.addAttribute("editions", editionService.getAllEditions());
        return "guest.html";
    }

    @RequestMapping("/order")
    public String orderPage(Model model){
        model.addAttribute("editions", editionService.getAllEditions());
        return "order";
    }

    @RequestMapping(value="/catalog/add", method = RequestMethod.POST)
    public String contactAdd(@RequestParam String name,
                             @RequestParam String author,
                             @RequestParam long price,
                             @RequestParam int amountEdition,
                             @RequestParam EditionStatus editionStatus,
                             @RequestParam int amountAvailable) {
        //Group group = (groupId != DEFAULT_GROUP_ID) ? contactService.findGroup(groupId) : null;
        Edition edition = new Edition(name, author, price, amountEdition, editionStatus, amountAvailable);
        //if(edition.equals(editionService.getAllEditions()))  edition=null;
        editionService.addEdition(edition);
        return "redirect:/catalog";
    }

    public RoleType role(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user.getRole();
    }

    public String userName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user.getUsername();
    }
}