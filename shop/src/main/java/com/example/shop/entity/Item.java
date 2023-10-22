package com.example.shop.entity;

import com.example.shop.constant.ItemSellStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Table(name="item")  //어떤 테이블과 매핑될지 지정
@Entity
public class Item {
    @Column(name="item_id")  //item 테이블의 컬럼명 item_id와 id를 매핑
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;  // 상품 코드

    @Column(nullable = false, length = 50)
    private String itemNm;  //상품명

    private int price;  // 상품 가격

    private int stockNumber;  //재고 수량

    @Lob  //사진과 같은 큰 값을 넣기 위한 것
    @Column(nullable = false)
    private String itemDetail;  //상품 상세 설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;  //상품 판매 상태

    private LocalDateTime regTime;  //등록 시간

    private LocalDateTime updateTime;  //수정 시간

}
