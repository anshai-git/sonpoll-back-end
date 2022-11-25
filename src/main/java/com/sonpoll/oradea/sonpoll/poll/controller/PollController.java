package com.sonpoll.oradea.sonpoll.poll.controller;

import com.sonpoll.oradea.sonpoll.common.CommonRequestDTO;
import com.sonpoll.oradea.sonpoll.common.CommonResponseDTO;
import com.sonpoll.oradea.sonpoll.poll.model.CreatePollRequest;
import com.sonpoll.oradea.sonpoll.poll.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("poll")
public class PollController {

    @Autowired
    PollService pollService;

    public CommonResponseDTO createPoll(@RequestBody CommonRequestDTO<CreatePollRequest> request) {
        return pollService.createPoll(request.getPayload());
    }

}
