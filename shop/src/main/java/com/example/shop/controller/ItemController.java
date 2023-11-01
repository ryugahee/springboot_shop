package com.example.shop.controller;

import com.example.shop.dto.ItemFormDto;
import com.example.shop.service.ItemService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    @GetMapping(value = "/admin/item/new")
    public String itemForm(Model model) {
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "/item/itemForm";
    }

    @PostMapping(value = "/admin/item/new")
    public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                          Model model, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {
        // 상품 등록시 필수 값이 없다면 다시 상품 등록 페이지로 전환
        if (bindingResult.hasErrors()) {
            return "item/itemForm";
        }
        // 상품 등록시 첫번째 이미지가 없다면 다시 상품 등록 페이지로 전환
        if (itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값입니다.");
            return "item/itemForm";
        }

        // 상품 저장 로직 호출
        // 매개 변수로 상품 정보와 상품 이미지 정보를 담고 있는 itemImgFileList를 넘겨줌
        try {
            itemService.saveItem(itemFormDto, itemImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생했습니다.");
             return "/item/itemForm";
        }
        // 정상 등록시 메인 페이지로 이동
        return "redirect:/";
    }

    /*
     * 상품 수정
     * */

    // 조회한 상품 데이터를 모델에 담아서 뷰로 전달
    @GetMapping(value = "/admin/item/{itemId}")
    public String itemDtl(@PathVariable("itemId") Long itemId, Model model) {
        try {
            // 조회한 상품 데이터 모델에 모아 보내기
            ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
            model.addAttribute("itemFormDto", itemFormDto);
        } catch (EntityNotFoundException e) {  //상품엔티티가 존재하지 않는 경우
            model.addAttribute("errorMessage", "존재하지 않은 상품입니다.");
            model.addAttribute("itemFormDto", new ItemFormDto());
            return "item/itemForm";
        }
        return "item/itemForm";
    }
}
