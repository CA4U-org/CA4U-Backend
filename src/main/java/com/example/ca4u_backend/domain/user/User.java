package com.example.ca4u_backend.domain.user;

import com.example.ca4u_backend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name = "USER")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    //회원 가입 시 전달받은 이름입니다.
    private String name;

    private String department;

    private String major;

    private boolean isRegistered;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String username, String email, Role role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    public void updateStudentInfo(String name, String department, String major) {
        this.name = name;
        this.department = department;
        this.major = major;
        this.isRegistered = true;
    }
}
