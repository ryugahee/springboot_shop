package com.example.shop.dto;

import com.example.shop.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ItemImgDto {
    private Long id;

    private  String imgName;

    private  String oriImgName;

    private String imgUrl;

    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();  //멤버변수로 ModelMapper 객체 추가

    // ItemImg 객체를 파라미터로 받아서 ItemImg의 자료형과 멤버변수의 이름이 같을 때
    // =>itemImg 엔티티 객체를 ItemDto 클래스로 매핑=>ItemImgDto로 값을 복사해서 반환
    // static 선언 -> ItemDto 객체 생성 없이 호출 가능하도록 함
    public static ItemImgDto of(ItemImg itemImg) {
        return modelMapper.map(itemImg, ItemImgDto.class);
    }
}
