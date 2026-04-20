package com.springboot.bizconnect.domain.order.service.impl;

import com.springboot.bizconnect.domain.alarm.service.AlarmService;
import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.cart.repository.CompanyProductCartRepository;
import com.springboot.bizconnect.domain.cart.repository.OrderItemRepository;
import com.springboot.bizconnect.domain.company.repository.CompanyRepository;
import com.springboot.bizconnect.domain.order.dto.create.CreateOrderRequestDto;
import com.springboot.bizconnect.domain.order.dto.create.CreateOrderResponseDto;
import com.springboot.bizconnect.domain.order.repository.OrderRepository;
import com.springboot.bizconnect.domain.order.service.BuyerOrderService;
import com.springboot.bizconnect.entity.Company;
import com.springboot.bizconnect.entity.CompanyProductCart;
import com.springboot.bizconnect.entity.Order;
import com.springboot.bizconnect.entity.OrderItem;
import com.springboot.bizconnect.entity.Product;
import com.springboot.bizconnect.enums.AlarmType;
import com.springboot.bizconnect.enums.orderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BuyerOrderServiceImpl implements BuyerOrderService {
    private final CompanyRepository companyRepository;
    private final OrderRepository orderRepository;
    private final CompanyProductCartRepository companyProductCartRepository;
    private final OrderItemRepository orderItemRepository;
    private final AlarmService alarmService;

    @Override
    @Transactional
    public CreateOrderResponseDto createOrder(CustomUserDetails userDetails, CreateOrderRequestDto requestDto) {
        Long buyerCompanyNo = userDetails.getUser().getCompany().getCompanyNo();
        Long supplierCompanyNo = requestDto.getSupplierCompanyNo();

        // 1. 공급사 존재 확인
        Company supplierCompany = companyRepository.findById(supplierCompanyNo)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 공급사입니다."));

        Company buyerCompany = companyRepository.findById(buyerCompanyNo)
                .orElseThrow(() -> new RuntimeException("구매사 정보를 찾을 수 없습니다."));

        // 2. 주문 생성
        Order order = Order.builder()
                .supplierCompany(supplierCompany)
                .buyerCompany(buyerCompany)
                .user(userDetails.getUser())
                .status(orderStatus.PENDING)
                .build();

        orderRepository.save(order);

        // 3. 주문 상품 검증 및 저장
        List<String> orderedProductNames = new ArrayList<>();

        for (CreateOrderRequestDto.OrderItemDto item : requestDto.getOrderItems()) {
            // 수량 검증
            if (item.getQuantity() == null || item.getQuantity() < 1) {
                throw new RuntimeException("수량은 1개 이상이어야 합니다.");
            }

            // 장바구니에 배정된 상품인지 + is_used = true 확인
            CompanyProductCart cart = companyProductCartRepository
                    .findByNo_SupplierCompanyNoAndNo_BuyerCompanyNoAndNo_ProductNo(
                            supplierCompanyNo, buyerCompanyNo, item.getProductNo())
                    .orElseThrow(() -> new RuntimeException("배정되지 않은 상품입니다: " + item.getProductNo()));

            if (!cart.getIsUsed()) {
                throw new RuntimeException("현재 주문할 수 없는 상품입니다: " + item.getProductNo());
            }

            Product product = cart.getProduct();

            // 주문 상품 저장
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(item.getQuantity())
                    .build();

            orderItemRepository.save(orderItem);
            orderedProductNames.add(product.getName());
        }

        String alarmTitle = "신규 발주 주문";
        String alarmContent = buyerCompany.getName() + "에서 발주 주문을 하였습니다. (" + orderedProductNames.size() + "건)";

        alarmService.sendToCompanyMembers(
                userDetails.getUser(),
                supplierCompanyNo,
                alarmTitle,
                alarmContent,
                AlarmType.GENERAL
        );

        return CreateOrderResponseDto.builder()
                .orderNo(order.getOrderNo())
                .message(orderedProductNames.size() + "개 상품 주문이 완료되었습니다.")
                .build();
    }
}
