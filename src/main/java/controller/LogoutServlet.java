package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class LogoutServlet {

    private static final Logger LOG = LoggerFactory.getLogger(LogoutServlet.class);

    @RequestMapping(value = "/logout", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<?> logout() {
        LOG.info("Logout endpoint called");
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }
}
