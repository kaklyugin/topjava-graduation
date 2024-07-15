package ru.topjava.lunchvoter.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"}, name = "user_unique_email_idx")})
public class User extends AbstractNamedBaseEntity {
    @Column(name = "email", nullable = false)
    @NotBlank
    @Email
    @Size(max = 128)
    private String email;
    
    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(max = 128)
    private String password;
    
    @Enumerated(EnumType.STRING)
    //TODO Провести эксперимент - выключить FetchType.EAGER
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "uk_user_role")})
    @Column(name = "role")
    private Set<Role> roles;
    
    public User() {
        super();
    }
    
    public User(Long id, String name, String email, Set<Role> roles) {
        super(id, name);
        this.email = email;
        this.roles = roles;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public Set<Role> getRoles() {
        return roles;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", roles=" + roles +
                ", id=" + id +
                '}';
    }
}
