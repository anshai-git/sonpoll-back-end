package com.sonpoll.oradea.sonpoll.poll.repository;

import com.sonpoll.oradea.sonpoll.poll.model.Option;
import com.sonpoll.oradea.sonpoll.poll.model.Poll;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PollRepository extends MongoRepository<Poll, String> {
    Optional<Poll> findByOwner(String ownerId);
}
