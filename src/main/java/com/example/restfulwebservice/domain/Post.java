package com.example.restfulwebservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Post {
    @Id @GeneratedValue
    private Integer id;

    private String description;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERTABLE_ID")
    private Usertable user;

    @Builder
    public Post(Integer id, String description, Usertable user) {
        this.id = id;
        this.description = description;
        this.user = user;
    }
}
