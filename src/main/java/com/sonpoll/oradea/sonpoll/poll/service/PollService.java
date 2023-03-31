package com.sonpoll.oradea.sonpoll.poll.service;

import com.sonpoll.oradea.sonpoll.common.CommonResponseDTO;
import com.sonpoll.oradea.sonpoll.common.exceptions.AuthorizationException;
import com.sonpoll.oradea.sonpoll.common.request.CreatePollRequest;
import com.sonpoll.oradea.sonpoll.poll.model.Poll;

import java.util.List;

public interface PollService {
    CommonResponseDTO<List<Poll>> getAllPolls();

    CommonResponseDTO<Poll> createPoll(CreatePollRequest request);

    CommonResponseDTO<Poll> getPollByOwner(String ownerId);

    CommonResponseDTO<Poll> getPollById(String pollId);

    CommonResponseDTO<Poll> updatePoll(Poll updatedPoll) throws AuthorizationException;

    CommonResponseDTO<Poll> updatePollVotes(Poll updatedPoll);
}
