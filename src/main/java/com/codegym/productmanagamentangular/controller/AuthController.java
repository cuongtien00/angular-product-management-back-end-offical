package com.codegym.productmanagamentangular.controller;

import com.codegym.productmanagamentangular.dto.request.SignInForm;
import com.codegym.productmanagamentangular.dto.request.SignUpForm;
import com.codegym.productmanagamentangular.dto.response.JwtResponse;
import com.codegym.productmanagamentangular.dto.response.ResponseMessage;
import com.codegym.productmanagamentangular.model.*;
import com.codegym.productmanagamentangular.security.jwt.JwtProvider;
import com.codegym.productmanagamentangular.security.userprimcipal.UserDetailService;
import com.codegym.productmanagamentangular.security.userprimcipal.UserPrinciple;
import com.codegym.productmanagamentangular.service.impl.MailServiceImpl;
import com.codegym.productmanagamentangular.service.impl.RoleServiceImpl;
import com.codegym.productmanagamentangular.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin("*")
public class AuthController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private MailServiceImpl mailService;

    @GetMapping("/verify/{id}")
    public ResponseEntity<?> verifyAccount(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        if (!userOptional.isPresent()){
            return new ResponseEntity<>(new ResponseMessage("Not found!"), HttpStatus.NOT_FOUND);
        }
        else {
            User user = userOptional.get();
            user.setStatus(1L);
            userService.save(user);
            return new ResponseEntity<>(new ResponseMessage("Verify Account is Success, Please login to Use!"),HttpStatus.OK);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm){
        if (userService.existsByUsername(signUpForm.getUsername())){
            return new ResponseEntity<>(new ResponseMessage("user has exists!"), HttpStatus.OK);
        }
        if (userService.existsByEmail(signUpForm.getEmail())){
            return new ResponseEntity<>(new ResponseMessage("email has exists!"),HttpStatus.OK);
        }
        if(signUpForm.getAvatar() == null || signUpForm.getAvatar().trim().isEmpty()){
            signUpForm.setAvatar("https://firebasestorage.googleapis.com/v0/b/chinhbeo-18d3b.appspot.com/o/avatar.png?alt=media&token=3511cf81-8df2-4483-82a8-17becfd03211");
        }
        User user = new User(signUpForm.getName(), signUpForm.getUsername(), signUpForm.getEmail(), passwordEncoder.encode(signUpForm.getPassword()), signUpForm.getAvatar());
        Set<String> strRoles = signUpForm.getRoles();
        Set<Role> roles = new HashSet<>();
        strRoles.forEach(role -> {
            switch (role){
                case "admin":
                    Role adminRole = roleService.findByName(RoleName.ADMIN).orElseThrow(
                            ()->
                                    new RuntimeException("Role not found!"));
                    roles.add(adminRole);
                    break;
                case "pm":
                    Role pmRole = roleService.findByName(RoleName.PM).orElseThrow(
                            ()-> new RuntimeException("Role not found!")
                    );
                    roles.add(pmRole);
                    break;
                default:
                    Role userRole = roleService.findByName(RoleName.USER).orElseThrow(
                            ()-> new RuntimeException("Role not found!")
                    );
                    roles.add(userRole);
            }
        });
        user.setStatus(0L);
        user.setRoles(roles);
        userService.save(user);
        Long userId = userService.findByUsername(signUpForm.getUsername()).get().getId();
        Mail mail = new Mail("cuongTien.com", signUpForm.getEmail(), "Account Verified", "Welcome to cuongTien app, Please click on the link below to verify this account!!,"+"\nhttp://localhost:8080/verify/"+userId);
        mailService.sendEmail(mail);
        return new ResponseEntity<>(new ResponseMessage("Success!"),HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody SignInForm signInForm){
        Authentication authentication  = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInForm.getUsername(), signInForm.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        Long status = userPrinciple.getStatus();
        if (status == 0) {
            return new ResponseEntity<>(new ResponseMessage("Need to verification account, Please check your email!"),HttpStatus.OK);
        }
        else if (status == 1){
            String token = jwtProvider.createToken(authentication);
            return ResponseEntity.ok(new JwtResponse(userPrinciple.getId(),token, userPrinciple.getName(), userPrinciple.getAvatar(), userPrinciple.getAuthorities()));
        }
        else {
            return new ResponseEntity<>(new ResponseMessage("Your account was blocked!"),HttpStatus.OK);
        }
    }
    @PutMapping("/change-avatar")
    public ResponseEntity<?> updateAvatar(@RequestBody ChangeAvatar changeAvatar){
        User user = userDetailService.getCurrentUser();
        if (user.getUsername().equals("Anonymous")){
            return new ResponseEntity<>(new ResponseMessage("Please Login!"),HttpStatus.OK);
        }
        else {
            user.setAvatar(changeAvatar.getAvatar());
            userService.save(user);
            return new ResponseEntity<>(new ResponseMessage("Success!"), HttpStatus.OK);
        }

    }
}