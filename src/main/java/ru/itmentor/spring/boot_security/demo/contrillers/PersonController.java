package ru.itmentor.spring.boot_security.demo.contrillers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itmentor.spring.boot_security.demo.security.PersonDetails;
import ru.itmentor.spring.boot_security.demo.service.PersonService;


@Controller
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping()
    public String show(Model model) { // Получим информацию о текущем пользователе и передайте ее в представление
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        model.addAttribute("user", personDetails.getPerson());

        return "person/info";
    }
}
