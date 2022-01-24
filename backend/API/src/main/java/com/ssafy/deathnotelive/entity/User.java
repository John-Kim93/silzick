package com.ssafy.deathnotelive.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User extends BaseEntity {

    @Column(unique = true)
    private long userId;

    @Column(nullable = false)
    private String email;

    @JsonIgnore //setter, getter로 못 읽게 막음.
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

}
