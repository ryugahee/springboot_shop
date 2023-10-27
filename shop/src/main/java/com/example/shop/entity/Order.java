package com.example.shop.entity;

import com.example.shop.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="orders")
public class Order {

    /*
     * 다대일 단방향 매핑 (주문-회원)
     * */

    @Id
    @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  //한 명의 회원이 여러번 주문 가능
    @JoinColumn(name="member_id")
    private Member member;

    private LocalDateTime orderDate;  //주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;  //주문 상태

    // 일대다 매핑 => 결론적 양방향 매핑, 영속성 전이(cascade), 고아  객체 제거
    // 연관 관계의 주인은 외래키(order_id)가 존재하는 OrderItem임. 주인 필드의 Order에 의해 관리된다는 뜻
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)  //부모 엔티티의 영속성 변화 자식에게 모두 전이
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime regTime;

    private LocalDateTime updateTime;

}
