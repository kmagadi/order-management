package com.training_spring_cloud.auth_service.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private String username;

    private String password;

    private Role role;

}