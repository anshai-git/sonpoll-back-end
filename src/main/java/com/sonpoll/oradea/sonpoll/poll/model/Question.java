package com.sonpoll.oradea.sonpoll.poll.model;

import lombok.Data;

import java.util.List;

@Data
public class Question {
    private String title;
    private String description;
    private boolean isMultiselectAllowed;
    // TODO: 21.01.2023  by default we will have options defined by poll owner
    //  and custom oprions will be included in this list as well
    private List<Option> options;

}
