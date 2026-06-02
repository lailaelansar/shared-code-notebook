package controller;

import dao.PersonDAO;
import model.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserEditServlet {
    private final PersonDAO personDAO;

    public UserEditServlet(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Integer id) {
        Person user = personDAO.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody Map<String, String> request) {
        String firstName = request.get("firstName");
        String lastName = request.get("lastName");
        String email = request.get("email");
        String role = request.get("role");

        if (firstName == null || lastName == null || email == null || role == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "All fields are required"));
        }

        Person editUser = new Person();
        editUser.setId(id);
        editUser.setFirstName(firstName);
        editUser.setLastName(lastName);
        editUser.setEmail(email);
        editUser.setRole(role);
        personDAO.updateUser(editUser);

        return ResponseEntity.ok(Map.of("message", "User updated successfully"));
    }
}
