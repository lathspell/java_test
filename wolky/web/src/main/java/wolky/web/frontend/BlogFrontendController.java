package wolky.storage.web.frontend;

import java.time.LocalDateTime;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import wolky.web.model.BlogEntry;
import wolky.web.backend.BlogStorageClient;

@Controller
@Slf4j
public class BlogFrontendController {

    @Autowired
    private BlogStorageClient blogStorageClient;

    @RequestMapping("/blog/")
    public String index(Model model) {
        log.info("/blog/ -> index()");
        List<BlogEntry> entries = blogStorageClient.findAll();

        model.addAttribute("date", LocalDateTime.now());
        model.addAttribute("entries", entries);
        log.info("model: " + model);
        return "blog/index";
    }

}
