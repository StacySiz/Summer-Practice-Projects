package ru.itis.mq.app.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itis.mq.app.commands.RegistrationForm;
import ru.itis.mq.app.converters.UserToUserForm;
import ru.itis.mq.app.domain.User;
import ru.itis.mq.app.services.UserService;

import javax.validation.Valid;


@Controller
public class UserController {

    private static final Logger log = LogManager.getLogger(UserController.class);

    private UserService userService;

    private UserToUserForm userToUserForm;



    @Autowired
    public void setUserToUserForm(UserToUserForm userToUserForm) {
        this.userToUserForm = userToUserForm;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/")
    public String redirToList(){
        return "redirect:/user/list";
    }

    @CrossOrigin(origins = "http://localhost:8083")
    @RequestMapping({"/user/list", "/user"})
    public String listUsers(Model model){
        model.addAttribute("users", userService.listAll());
        return "user/list";
    }

    @RequestMapping("/user/show/{id}")
    public String getUser(@PathVariable String id, Model model){
        model.addAttribute("user", userService.getById(Long.valueOf(id)));
        return "user/show";
    }

    @RequestMapping("user/edit/{id}")
    public String edit(@PathVariable String id, Model model){
        User user = userService.getById(Long.valueOf(id));
        RegistrationForm userForm = userToUserForm.convert(user);

        model.addAttribute("userForm", userForm);
        return "user/userForm";
    }

    @RequestMapping("/user/new")
    public String newUser(Model model){
        model.addAttribute("userForm", new RegistrationForm());
        return "user/userForm";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String saveOrUpdateUser(@Valid RegistrationForm registrationForm, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "user/userForm";
        }

        User savedUser = userService.saveOrUpdateUserForm(registrationForm);

        return "redirect:/user/show/" + savedUser.getId();
    }

    @RequestMapping("/user/delete/{id}")
    public String delete(@PathVariable String id){
        userService.delete(Long.valueOf(id));
        return "redirect:/user/list";
    }

    @RequestMapping("/user/indexUser/{id}")
    public String indexUser(@PathVariable String id){
        userService.sendUserMessage(id);
        return "redirect:/user/show/"+id;
    }
}
