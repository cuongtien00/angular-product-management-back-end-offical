package com.codegym.productmanagamentangular.service.impl;

import com.codegym.productmanagamentangular.model.Message;
import com.codegym.productmanagamentangular.repository.IMessageRepository;
import com.codegym.productmanagamentangular.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService implements IMessageService {

    @Autowired
    private IMessageRepository messageRepository;

    @Override
    public Iterable<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public Optional<Message> findById(Long id) {
        return messageRepository.findById(id);
    }

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public void remove(Long id) {
        messageRepository.deleteById(id);

    }

    @Override
    public List<Message> findAllBySender_Id(Long id) {
        return messageRepository.findAllBySender_Id(id);
    }

    @Override
    public List<Message> findAllByRoomId(Long id) {
        return messageRepository.findAllByRoomId(id);
    }
}
