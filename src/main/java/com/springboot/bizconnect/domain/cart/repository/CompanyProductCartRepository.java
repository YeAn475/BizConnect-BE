package com.springboot.bizconnect.domain.cart.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.bizconnect.entity.Company;
import com.springboot.bizconnect.entity.CompanyProductCart;

import java.util.Optional;

public interface CompanyProductCartRepository extends JpaRepository<CompanyProductCart, CompanyProductCart.CompanyProductNo> {

	// 공급사 기준 - 거래중인 구매사 목록 조회
	@Query("SELECT DISTINCT cp.buyerCompany FROM CompanyProductCart cp WHERE cp.no.supplierCompanyNo = :supplierCompanyNo AND cp.isUsed = true")
	Page<Company> findDistinctBuyersBySupplier(@Param("supplierCompanyNo") Long supplierCompanyNo, Pageable pageable);

	// 구매사 기준 - 거래중인 공급사 목록 조회
	@Query("SELECT DISTINCT cp.supplierCompany FROM CompanyProductCart cp WHERE cp.no.buyerCompanyNo = :buyerCompanyNo AND cp.isUsed = true")
	Page<Company> findDistinctSuppliersByBuyer(@Param("buyerCompanyNo") Long buyerCompanyNo, Pageable pageable);

	// 공급사가 특정 구매사에 배정한 상품 목록
	Page<CompanyProductCart> findByNo_SupplierCompanyNoAndNo_BuyerCompanyNo(
			Long supplierCompanyNo, Long buyerCompanyNo, Pageable pageable);

	// 구매사가 특정 공급사에서 주문 가능한 상품 목록 (is_used = true)
	Page<CompanyProductCart> findByNo_SupplierCompanyNoAndNo_BuyerCompanyNoAndIsUsedTrue(
			Long supplierCompanyNo, Long buyerCompanyNo, Pageable pageable);

	// 특정 장바구니 항목 조회
	Optional<CompanyProductCart> findByNo_SupplierCompanyNoAndNo_BuyerCompanyNoAndNo_ProductNo(
			Long supplierCompanyNo, Long buyerCompanyNo, Long productNo);

	// 해당 구매사에 해당 상품이 배정되어 있는지 확인
	boolean existsByNo_SupplierCompanyNoAndNo_BuyerCompanyNoAndNo_ProductNoAndIsUsedTrue(
			Long supplierCompanyNo, Long buyerCompanyNo, Long productNo);
}