package com.sonpoll.oradea.sonpoll.poll.model;

import lombok.Data;

import java.util.List;

@Data
public class Question {
    private String title;
    private String description;
    private boolean isMultiselectAllowed;
    private List<Option> options;

}
