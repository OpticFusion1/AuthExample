package optic_fusion1.authexample.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@RequestMapping("/api/v1/sessions")
public class SessionsController {

    // Accepts a JWT within the expiration date. Verifys signature & renewal token is still valid, then copies claims into a new JWT with a future expiration
    @PostMapping("/renew")
    public void renew() {
    }

    // Returns a list of all JWT details (no JWT or the renewal token) currently issued to the user.
    @PostMapping("/active")
    public void active() {

    }

    // Basic auth required. Accepts some form of ID mapping to the JWT detail. Server revokes the renewal token preventing the JWT from being used again
    @PostMapping("/revoke")
    public void revoke() {
    }

}
