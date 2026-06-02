package controller;

import dao.PersonDAO;
import dto.AuthResponse;
import dto.UserDto;
import model.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.TokenUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class LoginServlet {
    private final PersonDAO personDAO;

    public LoginServlet(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @PostMapping("/login")
    public ResponseEntity<?> handleLogin(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        if (email == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email and password are required"));
        }

        Person person = personDAO.login(email, hashPassword(password));
        if (person != null) {
            String token = TokenUtil.generateToken();
            UserDto userDto = new UserDto(person.getId(), person.getFirstName(), person.getLastName(), person.getEmail(), person.getRole());
            return ResponseEntity.ok(new AuthResponse(token, userDto));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid email or password"));
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
