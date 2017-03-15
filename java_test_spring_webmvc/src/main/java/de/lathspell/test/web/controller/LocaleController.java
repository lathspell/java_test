package de.lathspell.test.web.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/locale")
@Slf4j
public class LocaleController {

   
    
    /**
     * Call with "/locale/en" or "/locale/de".
     * 
     * @see MessageConfiguration where the current locale is retrieved by LocaleResolver for JSP
     */
    @RequestMapping("/{lang:(?:en|de)}")
    public String index(@PathVariable("lang") String lang, ModelMap map, HttpServletRequest request) {
        log.info("index({})", lang);
        map.put("lang", lang);
        
        // Save the locale in the current session so that our LocaleResolver can find it there.
        request.getSession().setAttribute("myLocale", Locale.forLanguageTag(lang));
        
        return "locale";
    }
}
