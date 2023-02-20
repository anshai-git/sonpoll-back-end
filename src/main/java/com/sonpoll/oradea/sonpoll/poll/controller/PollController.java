package com.sonpoll.oradea.sonpoll.poll.controller;

import com.sonpoll.oradea.sonpoll.common.CommonRequestDTO;
import com.sonpoll.oradea.sonpoll.common.CommonResponseDTO;
import com.sonpoll.oradea.sonpoll.common.exceptions.AuthorizationException;
import com.sonpoll.oradea.sonpoll.common.request.CreatePollRequest;
import com.sonpoll.oradea.sonpoll.poll.model.Poll;
import com.sonpoll.oradea.sonpoll.poll.service.PollServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("poll")
public class PollController {

    @Autowired
    PollServiceImpl pollService;

    public CommonResponseDTO createPoll(@RequestBody CommonRequestDTO<CreatePollRequest> request,
            final Authentication authentication) {
        return pollService.createPoll(request.getPayload());
    }

    @GetMapping("findAll")
    public CommonResponseDTO<List<Poll>> getAllPolls() {
        return pollService.getAllPolls();
    }

    @GetMapping("findByOwner")
    public CommonResponseDTO<Poll> getPollByOwner(@RequestBody CommonRequestDTO<String> request) {
        return pollService.getPollByOwner(request.getPayload());
    }

    @GetMapping("findById")
    public CommonResponseDTO<Poll> getPollById(@RequestBody CommonRequestDTO<String> request) {
        return pollService.getPollById(request.getPayload());
    }

    @PatchMapping("updatePoll")
    public CommonResponseDTO<Poll> updatePoll(@RequestBody CommonRequestDTO<Poll> updateRequest) {
        try {
            return pollService.updatePoll(updateRequest.getPayload());
        } catch (AuthorizationException e) {
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("updateVotes")
    public CommonResponseDTO<Poll> updatePollVotes(@RequestBody CommonRequestDTO<Poll> updateRequest) {
        return pollService.updatePollVotes(updateRequest.getPayload());
    }

}
