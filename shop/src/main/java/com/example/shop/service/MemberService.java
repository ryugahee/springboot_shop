package com.example.shop.service;

import com.example.shop.entity.Member;
import com.example.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional  // 에러 발생시 콜백시켜줌
@RequiredArgsConstructor  // 생성자 생성
public class MemberService {

    // 생성자가 1개이면 어노테이션 없어도 됨
    private final MemberRepository memberRepository;

    /*
     * 회원가입
     * */
    public Member saveMember(Member member) {
        // 중복 회원 검증
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    // 중복 회원의 경우 예외 발생시킴
    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }
}
