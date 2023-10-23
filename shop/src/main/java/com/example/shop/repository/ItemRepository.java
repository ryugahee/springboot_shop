package com.example.shop.repository;

import com.example.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

// JpaRepository는 2개의 제네릭 타입 사용(엔티티 타입 클래스, 기본키 타입)
public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item>{
    /*
    * 쿼리 메소드
    * */
    List<Item> findByItemNm(String itemNm);  //find + By + 변수이름

    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);  //상품명, 상품 상세 설명 조회

    List<Item> findByPriceLessThan(Integer price);  //파라미터로 넘어온 price 변수보다 값이 작은 상품 조회

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);  //가격 내림차순으로 조회

    /*
     * @Query 어노테이션
     * */
    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

    // 기존 DB에서 사용하던 쿼리를 그대로 사용해야 하는 경우 nativeQuery 속성 사용
    @Query(value = "select * from Item i where i.item_detail like %:itemDetail% order by i.price desc", nativeQuery = true)
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);

}
