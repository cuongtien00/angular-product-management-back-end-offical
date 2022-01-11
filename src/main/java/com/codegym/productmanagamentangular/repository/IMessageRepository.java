package com.codegym.productmanagamentangular.repository;

import com.codegym.productmanagamentangular.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllBySender_Id(Long id);
    List<Message> findAllByRoomId(Long id);
}
