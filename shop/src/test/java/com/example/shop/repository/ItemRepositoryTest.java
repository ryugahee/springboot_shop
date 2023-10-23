package com.example.shop.repository;

import com.example.shop.constant.ItemSellStatus;
import com.example.shop.entity.Item;
import com.example.shop.entity.QItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.util.ArrayBuilders;
import org.thymeleaf.util.SetUtils;
import org.thymeleaf.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.shop.entity.QItem.item;
import static org.junit.jupiter.api.Assertions.*;


@TestPropertySource(locations = "classpath:application-test.properties")  //application.properties과 같은 설정이 있다면 더 높은 우선순위를 부여. 테스트 실행시 MySQL 말고 H2를 사용
@SpringBootTest  //실제 애플리케이션을 구동할 때처럼 모든 Bean을 loC컨테이너에 등록함
class ItemRepositoryTest {

    @Autowired  //Bean에 주입
    ItemRepository itemRepository;


    /*
    * save() 메소드 테스트
    * */
    @Test
    @DisplayName("상품 저장 테스트")  //테스트코드 실행시 지정한 테스트명이 노출됨
    public void createItemTest() {
        Item item = new Item();

        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());

        Item savedItem = itemRepository.save(item);
        System.out.println(savedItem.toString());
    }

    /*
     * find() 메소드 테스트
     * */

    //테스트 코드 실행 시 DB에 상품이 없으므로 10개의 상품을 저장해줌
    public void createItemList() {
        for (int i = 1; i <= 10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());

            Item savedItem = itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNmTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNm("테스트 상품9");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("상품명, 상품 상세 설명 Or 테스트")
    void findByItemNmOrItemDetail() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 LessThan 테스트")
    void findByPriceLessThan() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThan(10005);
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 내림차순 조회 테스트")
    void findByPriceLessThanOrderByPriceDesc() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    /*
     * @Query 어노테이션 테스트
     * */
    @Test
    @DisplayName("@Query를 이용한 상품 조회 테스트")
    void findByItemDetail() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세 설명");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("nativeQuery를 이용한 상품 조회 테스트")
    void findByItemDetailByNative() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetailByNative("테스트 상품 상세 설명");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    /*
     * @Querydsl
     * */

    // EntityManager 빈을 주입
    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("Querydsl 조회 테스트1")
    public void querydslTest() {
        this.createItemList();

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);  //쿼리 동적 생성
        QItem qItem = item;  //플러그인에 의해 자동 생성된 QItem 객체
        JPAQuery<Item> query = queryFactory.selectFrom(qItem)
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.itemDetail.like("%" + "테스트 상품 상세 설명" + "%"))
                .orderBy(qItem.price.desc());

        List<Item> itemList = query.fetch();  //Lit<T> fetch() 조회 결과 반환 메소드

        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    /*
     * @QuerydslPredicateExecutor
     * */
    public void createItemList2() {
        for (int i = 1; i <= 5; i++) {  //1~5번 SELL 지정, 6~10번 SOLD_OUT 지정
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());

            itemRepository.save(item);
        }

        for (int i = 6; i <= 10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
            item.setStockNumber(0);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());

            itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("Querydsl 조회 테스트2")
    public void querydslTest2() {
        this.createItemList();

        BooleanBuilder booleanBuilder = new BooleanBuilder();  //쿼리에 들어갈 조건을 만들어주는 빌더
        QItem Item = item;  //플러그인에 의해 자동 생성된 QItem 객체
        String itemDetail = "테스트 상품 상세 설명";
        int price = 10003;
        String itemSellStatus = "SELL";

        booleanBuilder.and(item.itemDetail.like("%" + itemDetail + "%"));
        booleanBuilder.and(item.price.gt(price));

        if (StringUtils.equals(item.itemSellStatus, ItemSellStatus.SELL)) {
            booleanBuilder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));
        }

        Pageable pageable = PageRequest.of(0, 5);
        Page<Item> itemPagingResult = itemRepository.findAll(booleanBuilder, pageable);
        System.out.println("total elements : " + itemPagingResult.getTotalElements());

        List<Item> resultItemList = itemPagingResult.getContent();
        for (Item resultItem : resultItemList) {
            System.out.println(resultItemList.toString());
        }
    }
}