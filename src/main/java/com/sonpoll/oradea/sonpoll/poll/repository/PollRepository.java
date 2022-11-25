package com.sonpoll.oradea.sonpoll.poll.repository;

import com.sonpoll.oradea.sonpoll.poll.model.Poll;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PollRepository extends MongoRepository<Poll, String> {
}
