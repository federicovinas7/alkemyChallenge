package com.alkemy.challenge.controller;

import com.alkemy.challenge.model.DTO.LoginRequestDTO;
import com.alkemy.challenge.model.DTO.LoginResponseDTO;
import com.alkemy.challenge.model.DTO.UserDTO;
import com.alkemy.challenge.model.User;
import com.alkemy.challenge.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.alkemy.challenge.utils.Constants.AUTH_USER;
import static com.alkemy.challenge.utils.Constants.JWT_SECRET;
import static com.alkemy.challenge.utils.EntityURIBuilder.buildURI;

@RestController
@RequestMapping("/auth")
public class UserController {

    UserService userService;
    ObjectMapper objectMapper;

    @Autowired
    public UserController(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }


    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user){

        User u = userService.register(user);
        return ResponseEntity.created(buildURI(u.getId())).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO>login(@RequestBody LoginRequestDTO loginRequestDTO) throws JsonProcessingException {
        User user = userService.getByUserAndPass(loginRequestDTO.getUsername(),loginRequestDTO.getPassword());
        if(user != null){
            UserDTO userDTO = UserDTO.builder().username(user.getUsername()).role(user.getRole()).build();
            return ResponseEntity.ok(LoginResponseDTO.builder().token(this.generateToken(userDTO)).build());
        }else
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid username or password");
    }

    private String generateToken(UserDTO userDTO) throws JsonProcessingException {

        String authRole = userDTO.getRole();
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authRole);
        String token = Jwts.builder()
                .setId("JWT")
                .setSubject(userDTO.getUsername())
                .claim("user",objectMapper.writeValueAsString(userDTO))
                .claim("authorities",grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis() + 1800000 ))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET.getBytes()).compact();

        return token;
    }
}
