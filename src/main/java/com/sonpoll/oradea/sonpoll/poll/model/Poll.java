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
}
