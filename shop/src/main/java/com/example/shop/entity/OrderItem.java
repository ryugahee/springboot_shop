package com.example.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class OrderItem {

    /*
     * 다대일 단방향 매핑 (아이템-주문)
     * */


    @Id
    @GeneratedValue
    @Column(name="order_item_id")
    private Long id;

    @ManyToOne
    @Column(name="item_id")
    private  Item item;

    @ManyToOne
    @Column(name="order_id")
    private Order order;

    private  int OrderPrice;  //주문 가격

    private int Count;  // 수량

    private LocalDateTime regTime;

    private LocalDateTime updateTime;
}
