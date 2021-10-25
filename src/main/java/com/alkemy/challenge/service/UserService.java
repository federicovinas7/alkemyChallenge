package com.alkemy.challenge.service;

import com.alkemy.challenge.model.User;
import com.alkemy.challenge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

import static com.alkemy.challenge.service.EmailService.sendMail;

@Service
public class UserService {

    UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public User getById(Integer id){

        return userRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("User with id %d not found",id)));
    }
    public User register(User user) throws IOException {

        if(getByUsername(user.getUsername())==null){
            sendMail();
            return  userRepository.save(user);
        }

        else
            throw new ResponseStatusException(HttpStatus.CONFLICT,"username already exist");
    }

    private User getByUsername(String username) {

        return userRepository.getByUsername(username);
    }

    public User getByUserAndPass(String username, String password) {
        return userRepository.getByUserAndPass(username,password);
    }
}
