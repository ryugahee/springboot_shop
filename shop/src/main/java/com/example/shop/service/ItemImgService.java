package com.example.shop.service;

import com.example.shop.entity.ItemImg;
import com.example.shop.repository.ItemImgRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
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

    /*
     * 상품 수정
     * */

    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception {
        if (!itemImgFile.isEmpty()) {
            // 기존 이미지 엔티티 조회
            ItemImg savedItemImg = itemImgRepository.findById(itemImgId)
                    .orElseThrow(EntityNotFoundException::new);

            //기존 이미지 파일 삭제
            if (!StringUtils.isEmpty(savedItemImg.getImgName())) {
                fileService.deleteFile(itemImgLocation+"/"+
                        savedItemImg.getImgName());
            }

            String oriImgName = itemImgFile.getOriginalFilename();
            // 업데이트한 상품 이미지 파일을 업로드
            String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            String imgUrl = "/images/item/" + imgName;
            // 변경된 상품 이미지 정보를 세팅 **savedItemImg 엔티티가 영속상태이므로 데이터 변경만으로 변경 감지 기능이 도착하여
            //트랜젝션이 끝날 때 update 쿼리가 실행됨
            savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);

        }

    }
}
