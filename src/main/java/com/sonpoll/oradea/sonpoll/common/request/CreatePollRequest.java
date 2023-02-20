package com.sonpoll.oradea.sonpoll.common.request;

import com.sonpoll.oradea.sonpoll.poll.model.Question;

import java.util.List;

public record CreatePollRequest(String title, List<Question> questions, boolean publicResults) {

}
