package com.example.shop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="orders")
public class Order {

    @Id
    private Long id;

    private Member member;

    private LocalDateTime orderDate;
}
