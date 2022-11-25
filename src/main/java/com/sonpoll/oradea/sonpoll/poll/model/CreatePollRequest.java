package com.sonpoll.oradea.sonpoll.poll.model;

import java.util.List;

public record CreatePollRequest(String title, String owner, List<Question> questions, boolean publicResults) {

}
