package com.alkemy.challenge.service;

import com.alkemy.challenge.model.Character;
import com.alkemy.challenge.model.DTO.CharacterDTO;
import com.alkemy.challenge.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CharacterService {

    CharacterRepository characterRepository;

    @Autowired
    public CharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public List<CharacterDTO>getAll(){
        List<Character>characters = characterRepository.findAll();
        List<CharacterDTO>characterDTOList = new ArrayList<>();

        for(Character c : characters){
            characterDTOList.add(CharacterDTO.builder()
                                .image(c.getImage())
                                .name(c.getName())
                                .build());
        }
        
       return characterDTOList;
    }

    public Character addCharacter(Character character) {

        return characterRepository.save(character);
    }

    public Character getById(Integer id){
        return characterRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("Character with id %d not found",id)));
    }

    public Boolean updateCharacter(Character character) {
        Character c = getById(character.getId());
        characterRepository.save(character);
        return c != null;
    }

    public void deleteCharacter(Integer characterId) {

        characterRepository.deleteById(characterId);
    }
}
