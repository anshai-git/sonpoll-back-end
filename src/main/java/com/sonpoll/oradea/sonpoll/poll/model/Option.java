package com.sonpoll.oradea.sonpoll.poll.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class Option {
    private OPTION_TYPE type;
    private String textValue;
    private Date dateValue;
    private Double numberValue;
    private List<String> usersVotes = new ArrayList<>();

    public Option(final String value) {
        this.textValue = value;
        type = OPTION_TYPE.TEXT;
    }

    public Option(final Double value) {
        this.numberValue = value;
        type = OPTION_TYPE.NUMBER;
    }

    public Option(final Date value) {
        this.dateValue = value;
        type = OPTION_TYPE.DATE;
    }
}
