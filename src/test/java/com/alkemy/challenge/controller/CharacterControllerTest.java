package com.alkemy.challenge.controller;

import com.alkemy.challenge.model.DTO.CharacterDTO;
import com.alkemy.challenge.model.DTO.UserDTO;
import com.alkemy.challenge.service.CharacterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static com.alkemy.challenge.utils.Constants.AUTH_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CharacterControllerTest  {

   private CharacterController characterController;
   private CharacterService characterService;
   private Authentication auth;


   @BeforeEach
    public void setUp(){
       characterService = mock(CharacterService.class);
       auth = mock(Authentication.class);
       characterController = new CharacterController(characterService);
   }

   @Test
   public void getAll_200(){

       //given
       List<CharacterDTO> characterList = new ArrayList<>();
       CharacterDTO char1 = CharacterDTO.builder().name("character1").image("image1").build();
       CharacterDTO char2 = CharacterDTO.builder().name("character2").image("image2").build();
       characterList.add(char1);
       characterList.add(char2);

       UserDTO authUser = UserDTO.builder().role(AUTH_USER).build();

       //when
      when(auth.getPrincipal()).thenReturn(authUser);
      when(characterService.getAll()).thenReturn(characterList);


      //then
      ResponseEntity<List<CharacterDTO>>response = characterController.getAll(auth);

      assertEquals(characterList,response.getBody());
      assertEquals(HttpStatus.OK,response.getStatusCode());
   }

   @Test
    public void getAll_204(){

        //given
        List<CharacterDTO> characterList = new ArrayList<>();


        UserDTO authUser = UserDTO.builder().role(AUTH_USER).build();

        //when
        when(auth.getPrincipal()).thenReturn(authUser);
        when(characterService.getAll()).thenReturn(characterList);


        //then
        ResponseEntity<List<CharacterDTO>>response = characterController.getAll(auth);

        assertEquals(characterList,response.getBody());
        assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
    }

    @Test
    public void getAll_403(){

        //given
        List<CharacterDTO> characterList = new ArrayList<>();


        UserDTO unauthUser = UserDTO.builder().role("Unauthorized").build();

        //when
        when(auth.getPrincipal()).thenReturn(unauthUser);
        when(characterService.getAll()).thenReturn(characterList);

        //then
        assertThrows(ResponseStatusException.class,()->{
            characterController.getAll(auth);
        });

    }
}
