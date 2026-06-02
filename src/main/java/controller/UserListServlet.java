package controller;

import dao.PersonDAO;
import model.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserListServlet {
    private final PersonDAO personDAO;

    public UserListServlet(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping
    public List<Person> listUsers() {
        return personDAO.getAllUsers();
    }
}
