package com.hidiscuss.backend.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import io.jsonwebtoken.Claims;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Setter
    @Column(name = "point")
    private Long point;

    @Transient
    private String role;

    @Transient
    private String accessToken;

    @Builder
    public User(Long id, String name, String email, String accessToken, String refreshToken, Long point) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.point = point;
    }
}
