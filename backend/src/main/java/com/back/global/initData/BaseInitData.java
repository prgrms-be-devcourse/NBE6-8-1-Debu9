package com.back.global.initData;

import com.back.domain.member.member.entity.Member;
import com.back.domain.member.member.service.MemberService;
import com.back.domain.order.order.entity.Order;
import com.back.domain.order.order.service.OrderService;
import com.back.domain.product.product.entity.Product;
import com.back.domain.product.product.service.ProductService;
import com.back.global.util.NumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {
    @Autowired
    @Lazy
    private BaseInitData self;
    private final ProductService productService;
    private final OrderService orderService;
    private final MemberService memberService;

    @Bean
    ApplicationRunner baseInitDataApplicationRunner() {
        return args -> {
            self.BaseWork();
        };
    }

    @Transactional
    public List<Product> work1() { // 1. 생성된 Product 리스트를 반환하도록 변경
        if (productService.count() > 0) return productService.findAll();

        Product product1 = productService.write("스타버구 블렌드1", " /images/starbu9_blend1.png", " 스타버구 블렌드1은 과일 향과 꽃향기가 조화를 이루는 고급스러운 커피입니다. 깔끔한 산미와 은은한 단맛이 입안 가득 퍼지며, 가벼운 바디감으로 아침에 마시기 좋은 커피로 사랑받고 있습니다.", 10000, "Starbu9 Blend1");
        Product product2 = productService.write("스타버구 블렌드2", " /images/starbu9_blend2.png", " 스타버구 블렌드2는  균형 잡힌 맛과 부드러운 질감이 특징입니다. 초콜릿과 견과류의 고소함이 은은하게 느껴지고, 중간 정도의 산미와 풍부한 바디감이 조화를 이뤄 어느 때나 부담 없이 즐기기 좋습니다.", 20000, "Starbu9 Blend2");
        Product product3 = productService.write("스타버구 블렌드3",  "/images/starbu9_blend3.png", " 스타버구 블렌드3은 묵직한 바디감과 고소한 맛이 돋보이는 커피입니다. 캐러멜과 견과류의 풍미가 깊게 느껴지며, 쓴맛이 적고 부드러운 마무리로 남녀노소 모두에게 인기가 높습니다.",30000, "Starbu9 Blend3");
        Product product4 = productService.write("스타버구 블렌드4",  "/images/starbu9_blend4.png", "스타버구 블렌드4는 스모키하면서도 과일 향이 조화롭게 어우러진 커피입니다. 복합적인 맛과 중간에서 묵직한 바디감, 그리고 깔끔한 산미가 특징이며, 진한 에스프레소나 드립 커피 모두 잘 어울립니다."
                , 40000, "Starbu9 Blend4");

        return List.of(product1, product2, product3);
    }

    @Transactional
    public void work2(List<Product> products) { // 2. Product 리스트를 파라미터로 받도록 변경
        if (orderService.count() > 0) return;

        Member user1 = memberService.write("aaa@naver.com");
        Member user2 = memberService.write("bbb@gmail.com");
        Member user3 = memberService.write("ccc@programers.com");

        user1 = memberService.findById(1).orElseThrow(() -> new IllegalArgumentException("User with ID 1 does not exist."));
        user2 = memberService.findById(2).orElseThrow(() -> new IllegalArgumentException("User with ID 2 does not exist."));
        user3 = memberService.findById(3).orElseThrow(() -> new IllegalArgumentException("User with ID 3 does not exist."));

        Order order1 = orderService.write(user1, NumberGenerator.generateRandomNumber(6), "서울시 강남구 역삼동");
        Order order2 = orderService.write(user2, NumberGenerator.generateRandomNumber(6), "대전광역시 동구 자양동");
        Order order3 = orderService.write(user3, NumberGenerator.generateRandomNumber(6), "부산광역시 해운대구 우동");

        order1.addItem(products.get(0), 2, LocalDateTime.now().plusDays(3), "상품 준비 중");
        order1.addItem(products.get(1), 1, LocalDateTime.now().plusDays(2), "배송중");
        order2.addItem(products.get(1), 3, LocalDateTime.now(), "배송완료");
        order3.addItem(products.get(2), 1, LocalDateTime.now().plusDays(4), "입금 확인 중");
    }

    @Transactional
    public void BaseWork() {
        List<Product> products = self.work1(); // work1의 결과를 받음
        self.work2(products);                 // work2에 전달
    }
}