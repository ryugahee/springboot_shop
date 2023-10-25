package com.example.shop.service;

import com.example.shop.entity.Member;
import com.example.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional  // 에러 발생시 콜백시켜줌
@RequiredArgsConstructor  // 생성자 생성
public class MemberService implements UserDetailsService {  //MemberService가 UserDetailsService(시큐리티가 제공)를 구현

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

    /*
     * 로그인 / 로그아웃
     * */

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            throw new UsernameNotFoundException(email);
        }
        // User 객체를 생성하기 위해서 생성자로 파라미터 넘겨줌
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }


}
