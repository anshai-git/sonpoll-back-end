package com.sonpoll.oradea.sonpoll.poll.service;

import com.sonpoll.oradea.sonpoll.common.CommonError;
import com.sonpoll.oradea.sonpoll.common.CommonResponseDTO;
import com.sonpoll.oradea.sonpoll.common.exceptions.AuthorizationException;
import com.sonpoll.oradea.sonpoll.poll.model.CreatePollRequest;
import com.sonpoll.oradea.sonpoll.poll.model.Poll;
import com.sonpoll.oradea.sonpoll.poll.repository.PollRepository;
import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PollServiceImpl implements PollService {
    private static final Logger logger = LoggerFactory.getLogger(PollServiceImpl.class);

    private final PollRepository pollRepository;

    public CommonResponseDTO<List<Poll>> getAllPolls() {
        List<Poll> polls = this.pollRepository.findAll();
        return polls.isEmpty()
                ? CommonResponseDTO.createSuccesResponse("No polls found")
                : CommonResponseDTO.createSuccesResponse(polls);
    }

    public CommonResponseDTO<String> createPoll(final CreatePollRequest request) {
        Poll newPoll = Poll.builder()
                .title(request.title())
                .owner(request.owner())
                .questions(request.questions())
                .publicResults(request.publicResults())
                .build();
        pollRepository.save(newPoll);
        return CommonResponseDTO.createSuccesResponse("Poll created");
    }

    public CommonResponseDTO<Poll> getPollByOwner(final String ownerId) {
        Optional<Poll> pollByOwner = pollRepository.findByOwner(ownerId);
        return pollByOwner.isPresent()
                ? CommonResponseDTO.createSuccesResponse(pollByOwner.get())
                : CommonResponseDTO.createFailResponse(new CommonError("Poll can't be found for userId: " + ownerId));
    }

    public CommonResponseDTO<Poll> getPollById(final String pollId) {
        Optional<Poll> poll = pollRepository.findById(pollId);
        return poll.isPresent()
                ? CommonResponseDTO.createSuccesResponse(poll.get())
                : CommonResponseDTO.createFailResponse(new CommonError("Poll can't be found for id: " + pollId));
    }

    @Override
    public CommonResponseDTO<Poll> updatePoll(Poll updatedPoll) throws AuthorizationException {
        Optional<Poll> dbPoll = pollRepository.findById(updatedPoll.getId());
        if (dbPoll.isPresent()) {
            isSameOwner(dbPoll.get(), updatedPoll.getOwner());
            Poll pollToUpdate = dbPoll.get();
            pollToUpdate.setTitle(updatedPoll.getTitle());
            pollToUpdate.setPublicResults(updatedPoll.isPublicResults());
            pollToUpdate.setQuestions(updatedPoll.getQuestions());
            return CommonResponseDTO.createSuccesResponse(pollRepository.save(pollToUpdate));
        } else {
            return CommonResponseDTO.createSuccesResponse(
                    new CommonError("Poll with id: " + updatedPoll.getId() + " can't be found in database"));
        }
    }

    @Override
    public CommonResponseDTO<Poll> updatePollVotes(Poll updatedPoll) {
        Optional<Poll> dbPoll = pollRepository.findById(updatedPoll.getId());
        if (dbPoll.isPresent()) {
            Poll pollToUpdate = dbPoll.get();
            pollToUpdate.setQuestions(updatedPoll.getQuestions());
            return CommonResponseDTO.createSuccesResponse(pollRepository.save(pollToUpdate));
        } else {
            return CommonResponseDTO.createSuccesResponse(
                    new CommonError("Poll with id: " + updatedPoll.getId() + " can't be found in database"));
        }
    }

    private void isSameOwner(Poll dbPoll, String requestUser) throws AuthorizationException {
        if (!dbPoll.getOwner().equals(requestUser)) {
            throw new AuthorizationException("You don't have acces to update this poll");
        }
    }
}
