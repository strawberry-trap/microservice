package com.microservice.order.web;

import com.microservice.order.service.OrderService;
import dto.common.ResponseDTO;
import entity.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public Mono<ResponseDTO<Order>> getOrder(@PathVariable String id) {
        return orderService.getOrderById(id).map(ResponseDTO::ok);
    }

    @GetMapping("/list")
    public Flux<Order> getOrderList() {
        return orderService.getOrderList();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Order> createOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Order> updateOrder(@RequestBody Order order) {
        return orderService.updateOrder(order);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> deleteOrder(@PathVariable String id) {
        return orderService.deleteOrder(id);
    }

}
