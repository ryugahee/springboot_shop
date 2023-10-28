package com.example.shop.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log
public class FileService {
    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception{
        UUID uuid = UUID.randomUUID();  // 개체 구별용. 파일명 중복 문제 해결
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savedFileName = uuid.toString() + extension;  //UUID 값과 원래 파일명 조합해서 파일 이름 저장
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);  //파일 출력 스트림. 생성자로 파일이 저장될 위치와 파일 이름을 넘김
        fos.write(fileData);  //fileData를 출력 스트림에 입력
        fos.close();
        return savedFileName;  //업로드된 파일 이름을 반환
    }

    public void deleteFile(String filePath) throws Exception{
        File deleteFile = new File(filePath);  //6
        if(deleteFile.exists()) {  //6
            deleteFile.delete();
            log.info("파일을 삭제하였습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }
}
