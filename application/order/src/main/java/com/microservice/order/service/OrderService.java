package com.microservice.order.service;

import com.microservice.order.repository.OrderRepository;
import entity.order.Order;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * get order by id
     * @param id
     * */
    public Mono<Order> getOrderById(String id) {
        return orderRepository.findById(id);
    }

    /**
     * get all orders
     * */
    public Flux<Order> getOrderList() {
        return orderRepository.findAll();
    }

    /**
     * save order
     * @param order DTO
     * */
    @Transactional
    public Mono<Order> saveOrder(Order order) {
        return orderRepository.save(order);
    }

    /**
     * update order
     * @param order DTO
     * */
    @Transactional
    public Mono<Order> updateOrder(Order order) {
        return orderRepository.findById(order.getId())
                .flatMap( o -> {
                    if(!StringUtil.isNullOrEmpty(order.getOrderBy())) o.setOrderBy(order.getOrderBy());
                    if(order.getPrice() != null) o.setPrice(order.getPrice());
                    return orderRepository.save(o);
                });
    }

    /**
     * delete order
     * @param id order id
     * */
    @Transactional
    public Mono<Void> deleteOrder(String id) {
        return orderRepository.deleteById(id);
    }
}
