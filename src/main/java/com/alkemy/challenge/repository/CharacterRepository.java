package com.alkemy.challenge.repository;

import com.alkemy.challenge.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<Character,Integer> {
}
