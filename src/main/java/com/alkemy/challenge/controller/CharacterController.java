package com.alkemy.challenge.controller;

import com.alkemy.challenge.model.Character;
import com.alkemy.challenge.model.DTO.CharacterDTO;
import com.alkemy.challenge.service.CharacterService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.alkemy.challenge.filter.JWTAuthorizationFilter.verifyAuthentication;
import static com.alkemy.challenge.utils.EntityURIBuilder.buildURI;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    CharacterService characterService;

    @Autowired
    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping
    public ResponseEntity<List<CharacterDTO>> getAll(Authentication auth){
        verifyAuthentication(auth);
        List<CharacterDTO>characters = characterService.getAll();
        return ResponseEntity.status(characters.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK)
                .body(characters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Character>getById(Authentication auth,@PathVariable Integer id){
        verifyAuthentication(auth);
        Character character = characterService.getById(id);
        return  ResponseEntity.ok(character);
    }

    @PostMapping()
    public ResponseEntity<Character>addCharacter(Authentication auth,@RequestBody Character character){
        verifyAuthentication(auth);
        Character c = characterService.addCharacter(character);
        return ResponseEntity.created(buildURI(c.getId())).build();
    }

    @PutMapping()
    public ResponseEntity<Character> updateCharacter(Authentication auth,@RequestBody Character character){
        verifyAuthentication(auth);
        Character updatedCharacter = characterService.updateCharacter(character);
       return new ResponseEntity<>(updatedCharacter,HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Character>>searchCharacter(
            @RequestParam (required = false,name="name") String name ,@RequestParam (required = false,name= "age")Integer characterAge,
            @RequestParam (required = false,name="weight") Float charWeight,
            @RequestParam(required = false,name ="movieId") Integer movieId){

        List<Character>characters = characterService.searchCharacter(name,characterAge,charWeight,movieId);
        return ResponseEntity.status(characters.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK).body(characters);
    }

    @DeleteMapping("/{characterId}")
    public ResponseEntity<?> deleteCharacter(Authentication auth,@PathVariable Integer characterId){
        verifyAuthentication(auth);
        characterService.deleteCharacter(characterId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
