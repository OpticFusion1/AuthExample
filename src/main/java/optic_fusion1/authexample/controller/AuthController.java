package optic_fusion1.authexample.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.HashMap;
import java.util.Map;
import optic_fusion1.authexample.login.LoginRequest;
import optic_fusion1.authexample.repository.UserRepository;
import optic_fusion1.authexample.user.User;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;

/*
TODO: Implement the following endpoints. These should most likely be handled via a "SessionController" class

/sessions/renew (Accepts a JWT within the expiration date. Verifys signature & renewal token is still valid, then copies claims into a new JWT with a future expiration)
/sessions/active (Returns a list of all JWT details (no JWT or the renewal token) currently issued to the user.)
/sessions/revoke (Basic auth required. Accepts some form of ID mapping to the JWT detail. Server revokes the renewal token preventing the JWT from being used again) (edited)
 */
@RestController
@EnableAutoConfiguration
public class AuthController {

    private static final String SECRET_KEY = "CHANGE_ME";
    private UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        // TODO: Hash password and check that instead of plain-text
        if (userRepository.findByUsername(username) == null || !userRepository.findByUsername(username).getPassword().equals(password)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        String jwt = createJWT(username);

        Map<String, String> response = new HashMap<>();
        response.put("jwt", jwt);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // TODO: Improve this w/ an expiration date
    private String createJWT(String username) {
        String jtw = JWT.create().withSubject(username).sign(Algorithm.HMAC256(SECRET_KEY));
        return jtw;
    }

    @PostMapping("/register")
    public HttpStatus register(@RequestBody LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        if (userRepository.findByUsername(username) != null) {
            return HttpStatus.BAD_REQUEST;
        }
        userRepository.save(new User(username, password));
        return HttpStatus.OK;
    }

    @PostMapping("/forgotpassword")
    public HttpStatus forgotPassword(@RequestHeader(value = "Authorization") String auth) {
        String jwt = auth.split(" ")[1];
        String username = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(jwt).getSubject();

        if (userRepository.findByUsername(username) == null) {
            return HttpStatus.BAD_REQUEST;
        }

        // Send password reset email
        return HttpStatus.OK;
    }

}
