package yr.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import yr.ppmtool.domain.User;
import yr.ppmtool.exceptions.UsernameAlreadyExistsException;
import yr.ppmtool.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder; // convert password to others in database

    public User saveUser (User newUser) {
        try {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            // username has to be unique (exception)
            newUser.setUsername(newUser.getUsername());
            // make sure password and confirmpassword match
            // do not persist or show confirmpassword
            newUser.setConfirmPassword(""); // do not want to persist this. cannot user jsonIgnore, or it can't compare password
            return userRepository.save(newUser);
        } catch (Exception e) {
            throw new UsernameAlreadyExistsException("Username '" + newUser.getUsername() + "' already exists");
        }
    }
}
