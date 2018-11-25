package de.lathspell.test.frontend.web;

import de.lathspell.test.model.MyUser;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@Controller
@Slf4j
public class LoginController {

    @Autowired
    private MyUser myUser;

    @GetMapping("/login")
    public Model login(Model model) {
        model.addAttribute("user", new LoginModel());
        return model;
    }

    @PostMapping("/login")
    @SneakyThrows
    public ModelAndView loginSubmit(LoginModel loginUser, ModelAndView mv, HttpServletResponse resp) {
        if ("secret".equals(loginUser.getPassword())) {
            myUser.setName(loginUser.getName());
            resp.sendRedirect("/");
            return null;
        } else {
            mv.getModel().put("msg", "Wrong password!");
            mv.setViewName("login");
        }
        return mv;
    }

}
