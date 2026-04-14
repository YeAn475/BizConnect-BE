package com.springboot.bizconnect.domain.cart.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.springboot.bizconnect.entity.Product;

import org.springframework.data.repository.query.Param;

import com.springboot.bizconnect.entity.Company;
import com.springboot.bizconnect.entity.CompanyProductCart;

import java.util.Optional;

public interface CompanyProductCartRepository extends JpaRepository<CompanyProductCart, CompanyProductCart.CompanyProductNo>{
	// 두개의 값이 동시에 존재하는 정보가 있는지 탐색
	@Query("SELECT p FROM Product p " +
		       "JOIN CompanyProductCart cp ON p.productNo = cp.no.productNo " +
		       "WHERE cp.no.companyNo = :companyNo AND cp.isUsed = true")
		Page<Product> findAllByCompanyNoAndIsUsed(@Param("companyNo") Long companyNo, Pageable pageable);
	// 해당 회사의 제품인지 판별하는 메서드
	boolean existsByNo_CompanyNoAndNo_ProductNoAndIsUsedTrue(Long companyNo, Long productNo);
	
	@Query("SELECT DISTINCT cp.company FROM CompanyProductCart cp WHERE cp.isUsed = true")
	Page<Company> findDistinctCompaniesWithUsedCart(Pageable pageable);

	Page<CompanyProductCart> findByNo_CompanyNo(Long companyNo, Pageable pageable);

	Optional<CompanyProductCart> findByNo_CompanyNoAndNo_ProductNo(Long companyNo, Long productNo);

}
