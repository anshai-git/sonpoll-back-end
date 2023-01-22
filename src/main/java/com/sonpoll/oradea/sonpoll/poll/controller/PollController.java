package com.sonpoll.oradea.sonpoll.poll.controller;

import com.sonpoll.oradea.sonpoll.common.CommonRequestDTO;
import com.sonpoll.oradea.sonpoll.common.CommonResponseDTO;
import com.sonpoll.oradea.sonpoll.poll.model.CreatePollRequest;
import com.sonpoll.oradea.sonpoll.poll.model.Poll;
import com.sonpoll.oradea.sonpoll.poll.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("poll")
public class PollController {

    @Autowired
    PollService pollService;

    public CommonResponseDTO createPoll(@RequestBody CommonRequestDTO<CreatePollRequest> request) {
        return pollService.createPoll(request.getPayload());
    }

    @GetMapping("findAll")
    public CommonResponseDTO<List<Poll>> getAllPolls() {
        return CommonResponseDTO.createSuccesResponse(pollService.getAllPolls());
    }

    @GetMapping("findByOwner")
    public CommonResponseDTO<Poll> getPollByOwner(@RequestParam String ownerId) {
        return pollService.getPollByOwner(ownerId);
    }
    @PatchMapping("sendVote")
    public CommonResponseDTO<String> sendVote(@RequestBody CommonRequestDTO<Poll> voteRequest) {
        // TODO: 22.01.2023 implement 
        return null;
    }

}
