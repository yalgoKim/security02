package com.security.loginWithoutAdapter.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    private String email;

    @Column
    private String name;

    @Builder // setter, constructor 대신 builder 사용
    private Member(String email, String name) {
        this.email = email;
        this.name = name;
    }

    private Member(String name) {
        this.name = name;
    }

    public static Member createMember(String name) {
        return new Member(name);
    }
}
