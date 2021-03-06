package com.alkemy.challenge.utils;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public abstract class EntityURIBuilder {

    public static URI buildURI(final Integer id){


        return ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{entity}")
                    .buildAndExpand(id)
                    .toUri();
    }

    public static URI buildURI(final String entity, final String id){

        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/{entity}/{id}")
                .buildAndExpand(entity,id)
                .toUri();
    }
}
