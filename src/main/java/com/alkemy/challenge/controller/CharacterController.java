package com.alkemy.challenge.controller;

import com.alkemy.challenge.model.Character;
import com.alkemy.challenge.model.DTO.CharacterDTO;
import com.alkemy.challenge.service.CharacterService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Character>getById(@PathVariable Integer id){

        Character character = characterService.getById(id);
        return  ResponseEntity.ok(character);
    }

    @PostMapping()
    public ResponseEntity<Character>addCharacter(Authentication auth,@RequestBody Character character){
        verifyAuthentication(auth);
        Character c = characterService.addCharacter(character);
        return ResponseEntity.created(buildURI(c.getId())).build();
    }

    @PutMapping()//If character already exist then modifies it, otherwise a new character it's created
    public ResponseEntity<Character> updateCharacter(Authentication auth,@RequestBody Character character){
        verifyAuthentication(auth);
        if (characterService.updateCharacter(character))
            return ResponseEntity.ok(character);
        else
            return ResponseEntity.created(buildURI(character.getId())).build();
    }

    @DeleteMapping("/{characterId}")
    public void deleteCharacter(Authentication auth,@PathVariable Integer characterId){
        verifyAuthentication(auth);
        characterService.deleteCharacter(characterId);
    }
}
