package web.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import web.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    public void addUser(User user);
    public List<User> getAllUser();
    public void updateUser(User user);
    public void deleteUserById(Long id);
    public User getUser(Long id);
    public void getUserByRole();

    @Override
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;
}