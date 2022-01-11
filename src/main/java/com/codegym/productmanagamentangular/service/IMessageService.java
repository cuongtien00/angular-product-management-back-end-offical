package com.codegym.productmanagamentangular.service;

import com.codegym.productmanagamentangular.model.Message;

import java.util.List;

public interface IMessageService extends IGeneralService<Message> {
    List<Message> findAllBySender_Id(Long id);
    List<Message> findAllByRoomId(Long id);

}
