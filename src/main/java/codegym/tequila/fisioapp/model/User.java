package codegym.tequila.fisioapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
    @Id
    private String id;
    private String name;
    @Column(name = "last_name")
    private String lastName;
    private String avatar;
    private String user;
    private String password;
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user1 = (User) o;
        return Objects.equals(id, user1.id) && Objects.equals(name, user1.name) && Objects.equals(lastName, user1.lastName) && Objects.equals(avatar, user1.avatar) && Objects.equals(user, user1.user) && Objects.equals(password, user1.password) && Objects.equals(email, user1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, avatar, user, password, email);
    }
}
