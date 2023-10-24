package com.example.shop.repository;

import com.example.shop.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 중복 회원 검사를 위한 이메일 확인
    Member findByEmail(String email);
}
