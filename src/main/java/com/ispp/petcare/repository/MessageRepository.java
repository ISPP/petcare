package com.ispp.petcare.repository;

import com.ispp.petcare.domain.Message;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Message entity.
 */
public interface MessageRepository extends JpaRepository<Message,Long> {

    @Query("select message from Message message where message.sender.login = ?#{principal.username}")
    List<Message> findBySenderIsCurrentUser();

    @Query("select message from Message message where message.recipient.login = ?#{principal.username}")
    List<Message> findByRecipientIsCurrentUser();

}
