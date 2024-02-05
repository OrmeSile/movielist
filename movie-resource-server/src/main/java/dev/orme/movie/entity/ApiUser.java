package dev.orme.movie.entity;
import jakarta.persistence.*;

//TODO https://www.baeldung.com/role-and-privilege-for-spring-security-registration
@Entity
public class ApiUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    private String roles;
    public ApiUser() {}

    public ApiUser(String username, String password, String roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "ApiUser{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", roles='" + roles + '\'' +
                '}';
    }
}
