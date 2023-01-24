package optic_fusion1.authexample.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;
    @Column
    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    private User() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
    
}
