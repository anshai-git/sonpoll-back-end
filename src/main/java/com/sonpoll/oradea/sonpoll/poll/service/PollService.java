package com.sonpoll.oradea.sonpoll.poll.service;

import com.sonpoll.oradea.sonpoll.common.CommonResponseDTO;
import com.sonpoll.oradea.sonpoll.common.exceptions.AuthorizationException;
import com.sonpoll.oradea.sonpoll.poll.model.CreatePollRequest;
import com.sonpoll.oradea.sonpoll.poll.model.Poll;

import java.util.List;

public interface PollService {
    CommonResponseDTO<List<Poll>> getAllPolls();

    CommonResponseDTO<String> createPoll(final CreatePollRequest request);

    CommonResponseDTO<Poll> getPollByOwner(final String ownerId);
    CommonResponseDTO<Poll> getPollById(final String pollId);

    CommonResponseDTO<Poll> updatePoll(Poll updatedPoll) throws AuthorizationException;
    CommonResponseDTO<Poll> updatePollVotes(Poll updatedPoll);
}
