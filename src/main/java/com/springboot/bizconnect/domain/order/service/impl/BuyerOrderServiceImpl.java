package com.springboot.bizconnect.domain.order.service.impl;

import com.springboot.bizconnect.domain.order.dto.cancel.BuyerOrderCancelRequestDto;
import com.springboot.bizconnect.domain.order.dto.cancel.BuyerOrderCancelResponseDto;
import com.springboot.bizconnect.domain.order.dto.detail.BuyerOrderDetailRequestDto;
import com.springboot.bizconnect.domain.order.dto.detail.BuyerOrderDetailResponseDto;
import com.springboot.bizconnect.domain.order.dto.status.BuyerOrderStatusRequestDto;
import com.springboot.bizconnect.domain.order.dto.status.BuyerOrderStatusResponseDto;
import com.springboot.bizconnect.domain.order.dto.update.BuyerOrderUpdateRequestDto;
import com.springboot.bizconnect.domain.order.dto.update.BuyerOrderUpdateResponseDto;
import com.springboot.bizconnect.entity.User;
import com.springboot.bizconnect.entity.Order;
import com.springboot.bizconnect.entity.Company;
import com.springboot.bizconnect.entity.Product;
import com.springboot.bizconnect.entity.OrderItem;
import com.springboot.bizconnect.entity.CompanyProductCart;

import com.springboot.bizconnect.enums.AlarmType;
import com.springboot.bizconnect.enums.orderStatus;

import com.springboot.bizconnect.domain.alarm.service.AlarmService;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.user.repository.UserRepository;
import com.springboot.bizconnect.domain.order.service.BuyerOrderService;
import com.springboot.bizconnect.domain.order.repository.OrderRepository;
import com.springboot.bizconnect.domain.cart.repository.OrderItemRepository;
import com.springboot.bizconnect.domain.company.repository.CompanyRepository;
import com.springboot.bizconnect.domain.order.dto.create.CreateOrderRequestDto;
import com.springboot.bizconnect.domain.order.dto.create.CreateOrderResponseDto;
import com.springboot.bizconnect.domain.order.dto.list.BuyerOrderListRequestDto;
import com.springboot.bizconnect.domain.order.dto.list.BuyerOrderListResponseDto;
import com.springboot.bizconnect.domain.cart.repository.CompanyProductCartRepository;

import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class BuyerOrderServiceImpl implements BuyerOrderService {
    private final AlarmService alarmService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CompanyRepository companyRepository;
    private final OrderItemRepository orderItemRepository;
    private final CompanyProductCartRepository companyProductCartRepository;

    @Override
    @Transactional
    public CreateOrderResponseDto createOrder(CustomUserDetails userDetails, CreateOrderRequestDto requestDto) {
        Long buyerCompanyNo = userDetails.getUser().getCompany().getCompanyNo();
        Long supplierCompanyNo = requestDto.getSupplierCompanyNo();

        // 공급사 존재 확인
        Company supplierCompany = companyRepository.findById(supplierCompanyNo)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 공급사입니다."));

        Company buyerCompany = companyRepository.findById(buyerCompanyNo)
                .orElseThrow(() -> new RuntimeException("구매사 정보를 찾을 수 없습니다."));

        // 주문 생성
        Order order = Order.builder()
                .supplierCompany(supplierCompany)
                .buyerCompany(buyerCompany)
                .user(userDetails.getUser())
                .status(orderStatus.PENDING)
                .build();

        orderRepository.save(order);

        // 주문 상품 검증 및 저장
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

    @Override
    public List<BuyerOrderListResponseDto> OrderList(CustomUserDetails userDetails, BuyerOrderListRequestDto requestDto) {
        User user = userRepository.findById(userDetails.getUser().getUserNo())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        Company buyerCompany = user.getCompany();
        if (buyerCompany == null) {
            throw new RuntimeException("소속된 회사가 없습니다.");
        }

        PageRequest pageRequest = PageRequest.of(requestDto.getPage(), requestDto.getSize());

        return orderRepository.findByBuyerCompany_CompanyNo(buyerCompany.getCompanyNo(), pageRequest)
                .map(order -> BuyerOrderListResponseDto.builder()
                        .orderNo(order.getOrderNo())
                        .supplierCompanyName(order.getSupplierCompany().getName())
                        .orderStatus(order.getStatus().name())
                        .createdAt(order.getCreatedAt())
                        .build())
                .getContent();

    }

    @Override
    public BuyerOrderDetailResponseDto orderDetail(CustomUserDetails userDetails, BuyerOrderDetailRequestDto requestDto) {
        User user = userRepository.findById(userDetails.getUser().getUserNo())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        Order order = orderRepository.findById(requestDto.getOrderNo())
                .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다."));

        // 본인 회사 주문인지 확인
        if (!order.getBuyerCompany().getCompanyNo().equals(user.getCompany().getCompanyNo())) {
            throw new RuntimeException("조회 권한이 없습니다.");
        }

        List<OrderItem> orderItems = orderItemRepository.findByOrder_OrderNo(requestDto.getOrderNo());

        List<BuyerOrderDetailResponseDto.OrderItemDetailDto> items = orderItems.stream()
                .map(item -> BuyerOrderDetailResponseDto.OrderItemDetailDto.builder()
                        .productNo(item.getProduct().getProductNo())
                        .productName(item.getProduct().getName())
                        .quantity(item.getQuantity())
                        .build())
                .toList();

        return BuyerOrderDetailResponseDto.builder()
                .orderNo(order.getOrderNo())
                .supplierCompanyName(order.getSupplierCompany().getName())
                .status(order.getStatus().name())
                .createdAt(order.getCreatedAt())
                .orderItems(items)
                .build();
    }

    @Override
    public BuyerOrderStatusResponseDto orderStatusDetail(CustomUserDetails userDetails, BuyerOrderStatusRequestDto requestDto) {
        Order order = orderRepository.findById(requestDto.getOrderNo())
                .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다."));
        User user = userRepository.findById(userDetails.getUser().getUserNo())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        if (!order.getBuyerCompany().getCompanyNo().equals(user.getCompany().getCompanyNo())) {
            throw new RuntimeException("조회 권한이 없습니다.");
        }

        return BuyerOrderStatusResponseDto.builder()
                .status(order.getStatus().name())
                .build();
    }

    @Override
    public BuyerOrderCancelResponseDto orderCancel(CustomUserDetails userDetails, BuyerOrderCancelRequestDto requestDto) {
        return null;
    }

    @Override
    public BuyerOrderUpdateResponseDto orderUpdate(CustomUserDetails userDetails, BuyerOrderUpdateRequestDto requestDto) {
        /*
           - 주문이 실제 있는지, 주문한 회사소속이 맞는지. 상품번호가 맞는지 확인
           - 리스트 형태로 orderNo로 비교해서 존재하면 변경
         */
        return null;
    }
}
