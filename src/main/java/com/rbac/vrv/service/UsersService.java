package com.rbac.vrv.service;

import com.rbac.vrv.modal.Users;
import com.rbac.vrv.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private UserRepo userRepo;

    public Users register(Users user) {
        return userRepo.save(user);
    }

}
