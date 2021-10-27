package com.alkemy.challenge.service;

import com.alkemy.challenge.model.Character;
import com.alkemy.challenge.model.DTO.CharacterDTO;
import com.alkemy.challenge.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static com.alkemy.challenge.specs.CharacterSpecs.*;
import static com.alkemy.challenge.utils.Constants.CHARACTER_NOT_FOUND;

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
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,String.format(CHARACTER_NOT_FOUND,id)));
    }

    public Character updateCharacter(Character character) {

        return characterRepository.save(character);

    }

    public void deleteCharacter(Integer characterId) {

        if(characterRepository.existsById(characterId))
        characterRepository.deleteById(characterId);
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND,String.format(CHARACTER_NOT_FOUND,characterId));
    }

    public List<Character> searchCharacter(String name, Integer characterAge, Float charWeight, Integer movieId) {

        Specification<Character>spec1 = hasNameLike(name);
        Specification<Character>spec2 = ageEquals(characterAge);
        Specification<Character>spec3 = weightEquals(charWeight);
        Specification<Character>spec4 = inMovie(movieId);

        Specification<Character> specs = Specification.where(spec1).and(spec2).and(spec3).and(spec4);

        return characterRepository.findAll(specs);
    }
}
