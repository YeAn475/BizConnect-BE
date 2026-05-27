package com.springboot.bizconnect.domain.order.supplier.service;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.order.supplier.dto.detail.SupplierOrderDetailRequestDto;
import com.springboot.bizconnect.domain.order.supplier.dto.detail.SupplierOrderDetailResponseDto;
import com.springboot.bizconnect.domain.order.supplier.dto.list.SupplierOrderListRequestDto;
import com.springboot.bizconnect.domain.order.supplier.dto.list.SupplierOrderListResponseDto;
import com.springboot.bizconnect.domain.order.supplier.dto.status.SupplierOrderStatusUpdateRequestDto;
import com.springboot.bizconnect.domain.order.supplier.dto.status.SupplierOrderStatusUpdateResponseDto;

import java.util.List;

public interface SupplierOrderService {
    List<SupplierOrderListResponseDto> getOrderList(CustomUserDetails userDetails, SupplierOrderListRequestDto requestDto);
    SupplierOrderStatusUpdateResponseDto updateOrderStatus(CustomUserDetails userDetails, SupplierOrderStatusUpdateRequestDto requestDto);
    SupplierOrderDetailResponseDto getOrderDetail(CustomUserDetails userDetails, SupplierOrderDetailRequestDto requestDto);
}
