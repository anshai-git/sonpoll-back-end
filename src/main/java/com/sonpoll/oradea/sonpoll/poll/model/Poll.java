package com.sonpoll.oradea.sonpoll.poll.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Document("polls")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Poll {
    @Id
    private String id;
    private String title;
    private String owner;
    private List<Question> questions;
    private boolean publicResults;

    // TODO: this should have:
    // 1. A list of asignees representing the users that participate in this vote (list of user ids)
    // 2. A boolean denoting if the owner participates in the vode or not
    // 3. An invitation token that is used to invite users to a given poll

    // TODO: Also:
    // Users should probably be able to send an invitation to an other user for a poll by email /& username
}
