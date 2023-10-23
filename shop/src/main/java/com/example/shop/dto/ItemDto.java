package com.example.shop.dto;

import com.example.shop.constant.ItemSellStatus;

import java.time.LocalDateTime;

public class ItemDto {
    private Long id;

    private String itemNm;

    private int price;

    private String itemDetail;

    private String sellStatCd;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;
}
