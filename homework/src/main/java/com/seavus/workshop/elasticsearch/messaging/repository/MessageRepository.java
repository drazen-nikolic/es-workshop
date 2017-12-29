package com.seavus.workshop.elasticsearch.messaging.repository;

import com.seavus.workshop.elasticsearch.messaging.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
