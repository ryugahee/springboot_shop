package com.example.shop.service;

import com.example.shop.dto.MemberFormDto;
import com.example.shop.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional  //테스트 실행 후 롤백 처리를 통해 같은 메소드 반복 테스트 가능
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberServiceTest {
    
    @Autowired
    MemberService memberService;
    
    @Autowired
    PasswordEncoder passwordEncoder;

    // 회원 정보를 입력한  멤버 엔티티 생성
    public Member createMember() {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@email.com");
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("서울시 마포구 합정동");
        memberFormDto.setPassword("1234");

        return Member.createMember(memberFormDto, passwordEncoder);
    }

    // Junit의 Assertion 클래스의 assertEquals 메소드를 이용해 요청값과 저장값을 비교.
    // 첫번째 파라미터=기대값, 두번째 파라미터=실제 데이터값
    @Test
    @DisplayName("회원가입 테스트")
    public void saveMemberTest() {
        //when
        Member member = createMember();
        //given
        Member saveMember = memberService.saveMember(member);
        //then
        assertEquals(member.getEmail(), saveMember.getEmail());
        assertEquals(member.getName(), saveMember.getName());
        assertEquals(member.getAddress(), saveMember.getAddress());
        assertEquals(member.getPassword(), saveMember.getPassword());
        assertEquals(member.getRole(), saveMember.getRole());
    }

    @Test
    @DisplayName("중복 회원 가입 테스트")
    public void saveDuplicateMemberTest() {
        //given
        Member member1 = createMember();
        Member member2 = createMember();
        memberService.saveMember(member1);

        //when
        //발생할 예외타입
        Throwable e = assertThrows(IllegalStateException.class, () -> {
            memberService.saveMember(member2);
        });
        //발생 예외 메시지가 예상과 같은지 검증
        assertEquals("이미 가입된 회원입니다.", e.getMessage());
    }
}