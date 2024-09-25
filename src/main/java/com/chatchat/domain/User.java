package com.chatchat.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private String name;

    private String password;

    private String email;

    private String phone;

    public User(Long id) {
        this.id = id;
    }

}
