package com.hidiscuss.backend.entity;

import io.jsonwebtoken.Claims;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public User(Claims claims) {
        this.id = Long.valueOf(claims.get("userId").toString());
        this.name = claims.get("sub").toString();
        this.role = claims.get("role").toString();
    }
}
