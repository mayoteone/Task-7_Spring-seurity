package ru.itmentor.spring.boot_security.demo.contrillers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.Person;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.service.PersonService;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final RoleService roleService;

    private final PersonService personService;

//   private final PasswordEncoder passwordEncoder;


    @Autowired
    public AdminController(RoleService roleService, PersonService personService, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.personService = personService;
//        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String index(Model model) { //Получим всех людей из Dao и передадим на отображение в представление
        model.addAttribute("users", personService.findAll());

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
//        Set<Role> role = personDetails.getPerson().getRoles();
//        System.out.println(role.stream().findAny().get().getName());

        return "admin/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) { //Получим одного человека по id из Dao и передадим на отображение в представление
        model.addAttribute("user", personService.findOne(id));

        return "admin/show";
    }

    @GetMapping("/new") //Получим форму для добавления нового человека
    public String newUser(Model model) {
//        List<Role> allRoles = roleService.getAllRoles(); // Получаем все роли для списка выбора ролей для нового пользователя
//        model.addAttribute("allRoles", allRoles);

        model.addAttribute("user", new Person()); //Эту строку можно убрать добавив аннотацию в параметр (@ModelAttribute("user") User user) - Создается пустой пользователь и помещается в модель

        return "admin/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") @Valid Person person,
                         BindingResult bindingResult, Model model
                         ) { // Добавляем нового человека в БД из формы по адресу admin/new. После чего нас перенаправляют на страницу /people со всеми пользователями @RequestParam("roles") List<Long> roleIds

        if (bindingResult.hasErrors())
            return "admin/new";

//        List<Role> roles = roleService.getRolesByIds(roleIds);

//        String encodedPassword = passwordEncoder.encode(person.getPassword());
//        person.setPassword(encodedPassword);

//        person.setRoles(new HashSet<>(roles));
        personService.save(person);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", personService.findOne(id));
        return "admin/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid Person person,
                         BindingResult bindingResult, @PathVariable("id") int id) { // добавил для того, чтобы получить список ролей нового пользователя @RequestParam("roles") List<Long> roleIds

        if (bindingResult.hasErrors())
            return "admin/edit";
//        List<Role> roles = roleService.getRolesByIds(roleIds);
//        String encodedPassword = passwordEncoder.encode(person.getPassword());
//        person.setPassword(encodedPassword);

        personService.update(person, id);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personService.delete(id);
        return "redirect:/admin";
    }

}
