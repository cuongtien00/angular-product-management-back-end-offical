package com.codegym.productmanagamentangular.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Long status;

    @ManyToOne(targetEntity = User.class)
    private User sender;

    private Long roomId;

    private LocalDateTime time;

    public Message() {
    }

    public Message(String content, Long status, User sender, Long roomId, LocalDateTime time) {
        this.content = content;
        this.status = status;
        this.sender = sender;
        this.roomId = roomId;
        this.time = time;
    }

    public Message(Long id, String content, Long status, User sender, Long roomId, LocalDateTime time) {
        this.id = id;
        this.content = content;
        this.status = status;
        this.sender = sender;
        this.roomId = roomId;
        this.time = time;
    }
}
