package com.back.domain.product.product.entity;

import com.back.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Product extends BaseEntity {
    private String name;
    private String engName;
    private String info;
    private int price;
    private String imageUrl;

    // 생성자
    public Product(String name, String imageUrl, String info, int price, String engName) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.info = info;
        this.price = price;
        this.engName = engName;
    }

    // 수정 -> 서비스
    public void modify(String name, String imageUrl, String info, int price, String eng_name) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.info = info;
        this.price = price;
        this.engName = eng_name;
    }
}

