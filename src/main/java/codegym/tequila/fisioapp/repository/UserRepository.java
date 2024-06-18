package codegym.tequila.fisioapp.repository;

import codegym.tequila.fisioapp.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    private final List<User> users = new ArrayList<>();

    public void createUser(User user){
        users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }
}
