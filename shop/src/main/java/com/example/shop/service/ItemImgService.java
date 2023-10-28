package com.example.shop.service;

import com.example.shop.entity.ItemImg;
import com.example.shop.repository.ItemImgRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {

    // properties에 등록한 "itemImgLocation"프로퍼티 값 불러와서 변수에 넣어줌
    @Value("${itemImgLocation}")
    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;

    private final FileService fileService;

    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception {
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        //파일 업로드
        if (!StringUtils.isEmpty(oriImgName)) {
            // 사용자가 상품 이미지 등록시 저장할 경로, 파일 이름, 파일 바이트 배열을 업로드 파라미터로 uploadFile()메소드 호출 후 imgName 변수에 저장
            imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            imgUrl = "/imges/item/" + imgName;  //저장한 상품 불러올 경로 설정
        }

        // 상품 이미지 정보 저장
        // 실제 저장된 파일 이름, 업로드했던 파일 원래 이름, 업로드 결과 로컬에 저장된 파일 불러오는 경로
        itemImg.updateItemImg(oriImgName, imgName, imgUrl);
        itemImgRepository.save(itemImg);
    }
}
