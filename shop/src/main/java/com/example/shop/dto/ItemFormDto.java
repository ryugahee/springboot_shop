package com.example.shop.dto;

import com.example.shop.constant.ItemSellStatus;
import com.example.shop.entity.Item;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;


/*
* 상품 데이터 정보 전달
* */

@Getter
@Setter
public class ItemFormDto {

    private Long id;

    private String itemNm;

    private Integer price;

    private String itemDetail;

    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    // 상품 저장 후 상품 이미지 정보 저장
    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();

    // 상품 이미지 아이디를 저장 (상품 수정시 이미지 저장)
    private List<Long> itemImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Item createItem() {
        return modelMapper.map(this, Item.class);
    }
    public static ItemFormDto of(Item item) {
        return modelMapper.map(item, ItemFormDto.class);
    }
}
