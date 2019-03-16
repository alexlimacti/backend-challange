package com.invillia.acme.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.invillia.acme.domain.Order;
import com.invillia.acme.domain.Store;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	@Query("FROM Order o WHERE o.store = :store"
			+ " AND o.confirmationDate = :confirmationDate"
			+ " AND o.status = :status")
	public List<Order> findByStoreAndConfirmationDateAndStatus(@Param("store")Store store, @Param("confirmationDate")Date confirmationDate, @Param("status") Integer status);
	
	@Query("FROM Order o WHERE o.confirmationDate BETWEEN :beginDate AND :endDate")
	public List<Order> findByDate(@Param("beginDate")Date beginDate, @Param("endDate")Date endDate);
	
	@Transactional(readOnly=true)
	public Page<Order> findByStore(Store store, Pageable pageRequest);

}
