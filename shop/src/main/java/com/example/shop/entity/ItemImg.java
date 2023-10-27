package com.example.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="item_img")
@Getter @Setter
public class ItemImg extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="item_img_id")
    private Long id;

    private String imgName;  //이미지 파일명

    private String oriImgName;  //원본 이미지 파일명

    private String imgUrl;  //이미지 조회 경로

    private String repImgYn;  //대표 이미지 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")  //상품-이미지 다대일 단방향 매핑
    private Item item;

    // 이미지 정보 업데이트
    public void updateItemImg(String oriImgName, String imgName, String imgUrl) {
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}
