package com.codegym.productmanagamentangular.controller;

import com.codegym.productmanagamentangular.model.Message;
import com.codegym.productmanagamentangular.model.Product;
import com.codegym.productmanagamentangular.service.IMessageService;
import com.codegym.productmanagamentangular.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin("*")
@RestController
public class WebsocketController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IMessageService messageService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/products")
    @SendTo("/topic/product")
    public Product createNewProduct(Product product) {
        return productService.save(product);
    }


    @MessageMapping("/messages/create/{roomId}")
    public void createMessage(@DestinationVariable String roomId, Message message) {
        messageService.save(message);
//        messagingTemplate.convertAndSend("/topic/message/" + roomId, messageService.findAll());
        messagingTemplate.convertAndSend("/topic/message/" + roomId, messageService.save(message));
    }

    @MessageMapping("/messages/{roomId}")
    public void createMessage(@DestinationVariable String roomId) {
        List<Message> messages = messageService.findAllByRoomId(Long.valueOf(roomId));
        messagingTemplate.convertAndSend("/topic/message/" + roomId, messages);
//        messagingTemplate.convertAndSendTo("/topic/message/" + roomId, messageService.save(message));
    }



//    @MessageMapping("/messages/room/{roomId}")
//    public void findAllByRoomId(@DestinationVariable String roomId) {
//        List<Message> messages = messageService.findAllByRoomId(Long.valueOf(roomId));
//        messagingTemplate.convertAndSend("/topic/message/" + roomId, messages);
//    }
}
