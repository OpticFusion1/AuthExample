package optic_fusion1.authexample.account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Account {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String username;
    @Column
    private String password;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private Account() {

    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
}
