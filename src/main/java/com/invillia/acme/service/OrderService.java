package com.invillia.acme.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.invillia.acme.domain.Order;
import com.invillia.acme.domain.OrderItem;
import com.invillia.acme.domain.enumerator.PaymentStatus;
import com.invillia.acme.repository.OrderItemRepository;
import com.invillia.acme.repository.OrderRepository;
import com.invillia.acme.repository.PaymentRepository;
import com.invillia.acme.service.exceptions.ObjectNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private StoreService storeService;
	
	public Order find(Long id) {
		Optional<Order> obj = orderRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object don't found! Id: " + id + ", Type: " + Order.class.getName()));
	}
	
	public Order insert(Order entity, Integer credCardNumber) {
		entity.setId(null);
		entity.setConfirmationDate(new Date());
		entity.setStore(storeService.find(entity.getStore().getId()));
		entity.getPayment().setPaymentStatus(PaymentStatus.waiting);
		entity.getPayment().setOrder(entity);
		entity.getPayment().setCredcardNumber(credCardNumber);
		entity = orderRepository.save(entity);
		paymentRepository.save(entity.getPayment());
		for (OrderItem oi : entity.getItems()) {
			oi.setUnitPrice(oi.getUnitPrice());
			oi.setQuantity(oi.getQuantity());
		}
		orderItemRepository.saveAll(entity.getItems());
		return entity;
	}
	
	public Order updatePaymentStatus(Order entity) {
		entity.getPayment().setPaymentStatus(PaymentStatus.confirmed);
		paymentRepository.save(entity.getPayment());
		return entity;
	}
	
	public Page<Order> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return orderRepository.findAll(pageRequest);
	}

}
