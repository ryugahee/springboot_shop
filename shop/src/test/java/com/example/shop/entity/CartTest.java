package com.example.shop.entity;

import com.example.shop.dto.MemberFormDto;
import com.example.shop.repository.CartRepository;
import com.example.shop.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class CartTest {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EntityManager em;

    /*
    * Cart 엔티티를 조회해서 Member 엔티티 가져오기
    * */

    // 회원 엔티티 생성
    public Member createMember() {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@email.com");
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("서울시 도봉구");
        memberFormDto.setPassword("1234");

        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    @DisplayName("장바구니 회원 엔티티 매핑 조회 테스트")
    public void findCartAndMemberTest() {
        Member member = createMember();
        memberRepository.save(member);

        Cart cart = new Cart();
        cart.setMember(member);
        cartRepository.save(cart);

        em.flush();  // JPA 영속성 컨텍스트에 저장 후 트랜잭션 끝날 때 flush()호출해서 DB에 반영
        em.clear();  // JPA 영속성 컨텍스트로부터 엔티티 조회후, 영속성 컨텍스트에 없을 경우 DB에서 조회함. 그래서 일부러 영속성 컨텍스트비워줌

        // 저장된 장바구니 엔티티 조회
        Cart savedCart = cartRepository.findById(cart.getId())
                .orElseThrow(EntityNotFoundException::new);
        // member 엔티티 id와 savesCart에 매핑된 엔티티의 id 비교
        assertEquals(savedCart.getMember().getId(), member.getId());
    }
}