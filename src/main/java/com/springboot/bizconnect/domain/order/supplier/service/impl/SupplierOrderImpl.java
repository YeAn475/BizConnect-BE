package com.springboot.bizconnect.domain.order.supplier.service.impl;

import com.springboot.bizconnect.domain.alarm.service.AlarmService;
import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.cart.repository.OrderItemRepository;
import com.springboot.bizconnect.domain.order.supplier.dto.detail.SupplierOrderDetailRequestDto;
import com.springboot.bizconnect.domain.order.supplier.dto.detail.SupplierOrderDetailResponseDto;
import com.springboot.bizconnect.domain.order.supplier.dto.list.SupplierOrderListRequestDto;
import com.springboot.bizconnect.domain.order.supplier.dto.list.SupplierOrderListResponseDto;
import com.springboot.bizconnect.domain.order.supplier.dto.status.SupplierOrderStatusUpdateRequestDto;
import com.springboot.bizconnect.domain.order.supplier.dto.status.SupplierOrderStatusUpdateResponseDto;
import com.springboot.bizconnect.domain.order.supplier.repository.SupplierOrderRepository;
import com.springboot.bizconnect.domain.order.supplier.service.SupplierOrderService;
import com.springboot.bizconnect.domain.user.repository.UserRepository;
import com.springboot.bizconnect.entity.Company;
import com.springboot.bizconnect.entity.Order;
import com.springboot.bizconnect.entity.OrderItem;
import com.springboot.bizconnect.entity.User;
import com.springboot.bizconnect.enums.AlarmType;
import com.springboot.bizconnect.enums.orderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierOrderImpl implements SupplierOrderService {
    private final SupplierOrderRepository orderRepository;
    private final UserRepository userRepository;
    private final AlarmService alarmService;
    private final OrderItemRepository orderItemRepository;

    @Override
    public List<SupplierOrderListResponseDto> getOrderList(CustomUserDetails userDetails, SupplierOrderListRequestDto requestDto) {
        User user = userRepository.findById(userDetails.getUser().getUserNo())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        Company supplierCompany = user.getCompany();
        if (supplierCompany == null) {
            throw new RuntimeException("소속된 회사가 없습니다.");
        }

        PageRequest pageRequest = PageRequest.of(requestDto.getPage(), requestDto.getSize());

        return orderRepository.findBySupplierCompany_CompanyNo(supplierCompany.getCompanyNo(), pageRequest)
                .map(order -> SupplierOrderListResponseDto.builder()
                        .orderNo(order.getOrderNo())
                        .buyerCompanyName(order.getBuyerCompany().getName())
                        .status(order.getStatus().name())
                        .createdAt(order.getCreatedAt())
                        .build())
                .getContent();
    }

    @Override
    public SupplierOrderStatusUpdateResponseDto updateOrderStatus(CustomUserDetails userDetails, SupplierOrderStatusUpdateRequestDto requestDto) {
        User user = userRepository.findById(userDetails.getUser().getUserNo())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        Order order = orderRepository.findById(requestDto.getOrderNo())
                .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다."));

        // 본인 회사 주문인지 확인 (공급사 입장)
        if (!order.getSupplierCompany().getCompanyNo().equals(user.getCompany().getCompanyNo())) {
            throw new RuntimeException("처리 권한이 없습니다.");
        }

        // PENDING 상태일 때만 변경 가능
        if (order.getStatus() != orderStatus.PENDING) {
            throw new RuntimeException("이미 처리된 주문입니다.");
        }

        // 상태 변경
        orderStatus newStatus;
        String message;
        try {
            newStatus = orderStatus.valueOf(requestDto.getStatus().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("유효하지 않은 상태값입니다. (APPROVED 또는 REJECTED)");
        }

        if (newStatus == orderStatus.APPROVED) {
            message = "주문이 승인되었습니다.";
        } else if (newStatus == orderStatus.REJECTED) {
            message = "주문이 거절되었습니다.";
        } else {
            throw new RuntimeException("APPROVED 또는 REJECTED만 가능합니다.");
        }

        order.setStatus(newStatus);
        orderRepository.save(order);

        // 구매사에 알람 전송
        String alarmTitle = newStatus == orderStatus.APPROVED ? "발주 승인 알림" : "발주 거절 알림";
        String alarmContent = user.getCompany().getName() + "에서 주문번호 " + order.getOrderNo() + "번 발주를 "
                + (newStatus == orderStatus.APPROVED ? "승인" : "거절") + "하였습니다.";

        alarmService.sendToCompanyMembers(
                user,
                order.getBuyerCompany().getCompanyNo(),
                alarmTitle,
                alarmContent,
                AlarmType.GENERAL
        );

        return SupplierOrderStatusUpdateResponseDto.builder()
                .orderNo(order.getOrderNo())
                .status(newStatus.name())
                .message(message)
                .build();
    }

    @Override
    public SupplierOrderDetailResponseDto getOrderDetail(CustomUserDetails userDetails, SupplierOrderDetailRequestDto requestDto) {
        User user = userRepository.findById(userDetails.getUser().getUserNo())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        Order order = orderRepository.findById(requestDto.getOrderNo())
                .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다."));

        // 본인 회사 주문인지 확인 (공급사 입장)
        if (!order.getSupplierCompany().getCompanyNo().equals(user.getCompany().getCompanyNo())) {
            throw new RuntimeException("조회 권한이 없습니다.");
        }

        List<OrderItem> orderItems = orderItemRepository.findByOrder_OrderNo(requestDto.getOrderNo());

        List<SupplierOrderDetailResponseDto.OrderItemDetailDto> itemDtos = orderItems.stream()
                .map(item -> SupplierOrderDetailResponseDto.OrderItemDetailDto.builder()
                        .productNo(item.getProduct().getProductNo())
                        .productName(item.getProduct().getName())
                        .quantity(item.getQuantity())
                        .build())
                .toList();

        return SupplierOrderDetailResponseDto.builder()
                .orderNo(order.getOrderNo())
                .buyerCompanyName(order.getBuyerCompany().getName())
                .status(order.getStatus().name())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .orderItems(itemDtos)
                .build();
    }
}
