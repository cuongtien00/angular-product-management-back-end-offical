package com.codegym.productmanagamentangular.security.userprimcipal;

import com.codegym.productmanagamentangular.model.User;
import com.codegym.productmanagamentangular.repository.IUserRepository;
import com.codegym.productmanagamentangular.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("USer not found -> username or password" + username));
        return UserPrinciple.build(user);
    }
    public  User getCurrentUser() {
        Optional<User> user;
        String username;
//        lay 1 obj principal trong securityContext
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails){
            username = ((UserDetails) principal).getUsername();
        }
        else {
            username = principal.toString();
        }
        if (userRepository.existsByUsername(username)){
            user = userService.findByUsername(username);
        }
        else {
            user = Optional.of(new User());
            user.get().setUsername("Anonymous");
        }
        return user.get();
    }


}