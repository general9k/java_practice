package ru.rodionov.servlets.server.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Integer id;
    private String name;
    private String image;
    private LocalDate birthDay;
    private String email;
    private String password;
    private Role role;
    private Gender gender;

}
