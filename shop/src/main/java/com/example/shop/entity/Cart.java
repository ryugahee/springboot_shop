package com.example.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name="cart")
public class Cart {

    /*
    * 일대일 단방향 매핑 (장바구니-회원)
    * */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="cart_id")
    private Long id;

    @OneToOne
    @JoinColumn(name="member_id")  //매핑할 외래키 지정. name=매핑할 외래키 이름
    private Member member;


}
