package app1.frontend.web;

import java.time.LocalDate;
import java.util.List;

import app1.db.BlogRepo;
import app1.model.BlogEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class BlogController {

    @Autowired
    private BlogRepo blogRepo;

    @RequestMapping("/blog/")
    public String index(Model model) {
        log.info("/blog/ -> index()");
        List<BlogEntry> entries = blogRepo.findAll();

        model.addAttribute("date", LocalDate.now());
        model.addAttribute("entries", entries);
        log.info("model: " + model);
        return "blog/index";
    }

}
