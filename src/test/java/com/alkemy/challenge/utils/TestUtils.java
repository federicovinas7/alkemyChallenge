package com.alkemy.challenge.utils;

import com.alkemy.challenge.model.Character;
import com.alkemy.challenge.model.DTO.CharacterDTO;
import com.alkemy.challenge.model.DTO.UserDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.alkemy.challenge.utils.Constants.AUTH_USER;

public class TestUtils {

    public static UserDTO anAuthUser(){
        return UserDTO.builder().username("user").role(AUTH_USER).build();
    }
    public static UserDTO anUnauthUser(){
        return UserDTO.builder().username("user").role("UNAUTHORIZED").build();
    }
    public static CharacterDTO aCharacterDTO(){

        return CharacterDTO.builder().name("character1").image("image").build();
    }
    public static List<CharacterDTO>aCharacterDTOlist(){

        List<CharacterDTO>characterDTOList = new ArrayList<>();
        characterDTOList.add(aCharacterDTO());
        characterDTOList.add(CharacterDTO.builder().name("character2").image("image2").build());
        return characterDTOList;
    }
    public static Character aCharacter(){

        return Character.builder()
                .id(1)
                .name("character1")
                .age(10)
                .weight(18.5F)
                .movieList(new HashSet<>())
                .image("image")
                .story("a story")
                .build();
    }

}
