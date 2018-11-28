package de.lathspell.test.frontend.web;

import static org.springframework.http.HttpStatus.IM_USED;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/")
    public String index() {
        return "admin/index";
    }

    @GetMapping("/tell-secret")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(IM_USED)
    @ResponseBody
    public String tellSecret(Model model) {
        return "Kratufibu!";
    }
}
