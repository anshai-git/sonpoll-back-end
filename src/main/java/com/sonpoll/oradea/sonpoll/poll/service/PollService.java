package com.sonpoll.oradea.sonpoll.poll.service;

import com.sonpoll.oradea.sonpoll.common.CommonError;
import com.sonpoll.oradea.sonpoll.common.CommonResponseDTO;
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
public class PollService {
    private static final Logger logger = LoggerFactory.getLogger(PollService.class);

    private final PollRepository pollRepository;

    public List<Poll> getAllPolls() {
        return this.pollRepository.findAll();
    }

    public CommonResponseDTO createPoll(final CreatePollRequest request) {
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
        if (pollByOwner.isPresent()) {
            return CommonResponseDTO.createSuccesResponse(pollByOwner.get());
        }
        return CommonResponseDTO.createFailResponse(new CommonError("Poll can't be found for userId: " + ownerId));
    }
}
