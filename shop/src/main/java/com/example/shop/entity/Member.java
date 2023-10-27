package com.example.shop.entity;

import com.example.shop.constant.Role;
import com.example.shop.dto.MemberFormDto;
import com.querydsl.core.types.PredicateTemplate;
import groovyjarjarpicocli.CommandLine;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@Entity
@ToString
@Table(name="member")
public class Member extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)   //enum으 기본적으로 순서가 저장되나 바뀔 수 있으므로 string 권장
    private Role role;

    //Member 엔티티 생성
    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());

        // Bean을 파라미터로 넘겨 비밀번호를 암호화
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.ADMIN);

        return member;
    }
}
