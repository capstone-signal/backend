package com.hidiscuss.backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.lang.reflect.Member;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "User")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "refresh_token")
    private String refresh_token;

    @Column(name = "point")
    private Long point;


    @Builder
    public User(String username, String name, String email, String accessToken, String refresh_token, Long point) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.accessToken = accessToken;
        this.refresh_token = refresh_token;
        this.point = point;
    }

    public User update(User user){
        this.name = user.getName();
        this.username =user.getUsername();
        return this;
    }
}
