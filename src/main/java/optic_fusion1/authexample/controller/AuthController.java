package optic_fusion1.authexample.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import optic_fusion1.authexample.login.LoginRequest;
import optic_fusion1.authexample.account.Account;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import optic_fusion1.authexample.repository.AccountRepository;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@EnableAutoConfiguration
@RequestMapping("/api/v1")
public class AuthController {

    private static final String SECRET_KEY = "CHANGE_ME";
    private AccountRepository accountRepository;

    public AuthController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        // TODO: Hash password and check that instead of plain-text
        Account account = accountRepository.findByUsername(username);
        if (account == null || !account.getPassword().equals(password)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        String jwt = createJWT(username);

        Map<String, String> response = new HashMap<>();
        response.put("jwt", jwt);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // TODO: Improve this w/ an expiration date
    private String createJWT(String username) {
        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expiration = issuedAt.plus(3, ChronoUnit.MINUTES);
        String jwt = JWT.create().withSubject(username)
                .withJWTId(UUID.randomUUID().toString()) // Used to ensure that a token has not been replayed or reused.
                .withIssuedAt(Date.from(issuedAt))
                .withExpiresAt(Date.from(expiration))
                .sign(Algorithm.HMAC256(SECRET_KEY));
        return jwt;
    }

    @PostMapping("/register")
    public HttpStatus register(@RequestBody LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        if (accountRepository.findByUsername(username) != null) {
            return HttpStatus.FORBIDDEN;
        }
        accountRepository.save(new Account(username, password));
        return HttpStatus.OK;
    }

    @PostMapping("/forgotpassword")
    public HttpStatus forgotPassword(@RequestHeader(value = "Authorization") String auth) {
        try {
            String jwt = auth.split(" ")[1];
            String username = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(jwt).getSubject();
            if (accountRepository.findByUsername(username) == null) {
                return HttpStatus.BAD_REQUEST;
            }
            // Send password reset email
            return HttpStatus.OK;
        } catch (TokenExpiredException e) {
            // Token has expired
            return HttpStatus.UNAUTHORIZED;
        } catch (JWTVerificationException e) {
            // Invalid token
            return HttpStatus.BAD_REQUEST;
        }
    }

}
