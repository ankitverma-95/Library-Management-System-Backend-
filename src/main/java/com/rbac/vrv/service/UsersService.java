package com.rbac.vrv.service;

import com.rbac.vrv.modal.Role;
import com.rbac.vrv.modal.Users;
import com.rbac.vrv.repo.RoleRepo;
import com.rbac.vrv.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UsersService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users register(Users user) {
        Role newRole = new Role();
        newRole.setName("USER");
        roleRepo.save(newRole);
        user.setPassword(encoder.encode(user.getPassword()));
        Collection<Role> roles = new ArrayList<>();
        roles.add(newRole);
        user.setRoles(roles);
        return userRepo.save(user);
    }

    public String verify(Users user) throws Exception {

        try {
            Authentication authentication =
                    authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            if(authentication.isAuthenticated()) {
                return jwtService.generateToken(user.getUsername(), authentication.getAuthorities());
            } else {
                return "Fail";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public List<Users> getUsers() {
       return userRepo.findAll();
    }

    public Users addAdmin(Users user) {
        Role newRole = new Role();
        newRole.setName("ADMIN");
        roleRepo.save(newRole);
        user.setPassword(encoder.encode(user.getPassword()));
        Collection<Role> roles = new ArrayList<>();
        roles.add(newRole);
        user.setRoles(roles);
        return userRepo.save(user);
    }
}
