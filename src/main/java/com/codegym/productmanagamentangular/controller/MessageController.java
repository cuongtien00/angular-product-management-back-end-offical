package com.codegym.productmanagamentangular.controller;

import com.codegym.productmanagamentangular.model.Message;
import com.codegym.productmanagamentangular.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin("*")
@RequestMapping("/messages")
public class MessageController {


    @Autowired
    private IMessageService messageService;




    @GetMapping
    public ResponseEntity<List<Message>> findAll() {
        return new ResponseEntity<>((List<Message>) messageService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{roomId}")
    public ResponseEntity<List<Message>> findAllByRoomId(@PathVariable Long roomId) {
        return new ResponseEntity<>(messageService.findAllByRoomId(roomId), HttpStatus.OK);
    }
    @GetMapping("/sender/{id}")
    public ResponseEntity<List<Message>> findAllBySenderId(@PathVariable Long id) {
        return new ResponseEntity<>(messageService.findAllBySender_Id(id), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        return  new ResponseEntity<>(messageService.save(message),HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable Long id, @RequestBody Message message){

        if (!messageService.findById(id).isPresent()) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(messageService.save(message),HttpStatus.OK);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id){
        messageService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
