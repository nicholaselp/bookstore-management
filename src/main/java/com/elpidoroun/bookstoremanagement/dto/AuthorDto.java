package com.elpidoroun.bookstoremanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class AuthorDto implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    public AuthorDto() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
