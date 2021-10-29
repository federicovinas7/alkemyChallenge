package com.alkemy.challenge.controller;

import com.alkemy.challenge.model.Character;
import com.alkemy.challenge.model.DTO.CharacterDTO;
import com.alkemy.challenge.model.DTO.UserDTO;
import com.alkemy.challenge.model.User;
import com.alkemy.challenge.service.CharacterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.alkemy.challenge.utils.Constants.AUTH_USER;
import static com.alkemy.challenge.utils.TestUtils.*;
import static java.util.Optional.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CharacterControllerTest  {

   private CharacterController characterController;
   private CharacterService characterService;
   private Authentication auth;
   private ResponseStatusException expectedException;


   @BeforeEach
    public void setUp(){
       characterService = mock(CharacterService.class);
       auth = mock(Authentication.class);
       characterController = new CharacterController(characterService);
       MockHttpServletRequest request = new MockHttpServletRequest();
       RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
       expectedException = null;
   }

   @Test
   public void getAllTest_200(){

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
    public void getAllTest_204(){

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
    public void getAllTest_403(){

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

    @Test
    public void getByIdTest_200(){

       //given
        UserDTO authUser = UserDTO.builder().role(AUTH_USER).build();

        //when
        when(auth.getPrincipal()).thenReturn(anAuthUser());
        when(characterService.getById(1)).thenReturn(aCharacter());

        ResponseEntity<Character>response = characterController.getById(auth,1);

        //then
        assertEquals(aCharacter(),response.getBody());
        assertEquals(HttpStatus.OK,response.getStatusCode());

    }

    @Test
    public void getByIdTest_404(){

        //given
        UserDTO authUser = UserDTO.builder().role(AUTH_USER).build();
        ResponseStatusException expected =mock(ResponseStatusException.class);
        //when
        when(auth.getPrincipal()).thenReturn(anAuthUser());
        when(characterService.getById(1)).thenThrow(ResponseStatusException.class);
        //then
        assertThrows(ResponseStatusException.class,()->characterController.getById(auth,1));

    }

    @Test
    public void addCharacter_201(){

       when(auth.getPrincipal()).thenReturn(anAuthUser());
       when(characterService.addCharacter(aCharacter())).thenReturn(aCharacter());

       ResponseEntity<Character>response = characterController.addCharacter(auth,aCharacter());

       assertEquals(HttpStatus.CREATED,response.getStatusCode());
    }

    @Test
    public void addCharacter_403(){

        when(auth.getPrincipal()).thenReturn(anUnauthUser());
        when(characterService.addCharacter(aCharacter())).thenReturn(aCharacter());

        try{
            characterController.addCharacter(auth,aCharacter());
        }catch (ResponseStatusException ex){
            expectedException = ex;
        }

        assertEquals(HttpStatus.FORBIDDEN,expectedException.getStatus());
    }

    @Test
    public void updateCharacter_200(){

       when(auth.getPrincipal()).thenReturn(anAuthUser());
       when(characterService.updateCharacter(aCharacter())).thenReturn(aCharacter());

       ResponseEntity<Character>response = characterController.updateCharacter(auth,aCharacter());

       assertEquals(HttpStatus.OK,response.getStatusCode());
       assertEquals(aCharacter(),response.getBody());
    }

    @Test
    public void updateCharacter_403(){

        when(auth.getPrincipal()).thenReturn(anUnauthUser());
        when(characterService.updateCharacter(aCharacter())).thenReturn(aCharacter());

        try{
            characterController.updateCharacter(auth,aCharacter());
        }catch (ResponseStatusException ex){
            expectedException = ex;
        }

        assertEquals(HttpStatus.FORBIDDEN,expectedException.getStatus());
    }

    public void searchCharacter_200(){


    }

    public void searchCharacter_403(){

    }
}
