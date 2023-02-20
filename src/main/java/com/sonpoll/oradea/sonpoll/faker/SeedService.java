package com.sonpoll.oradea.sonpoll.faker;

import com.github.javafaker.Faker;
import com.sonpoll.oradea.sonpoll.poll.model.OPTION_TYPE;
import com.sonpoll.oradea.sonpoll.poll.model.Option;
import com.sonpoll.oradea.sonpoll.poll.model.Poll;
import com.sonpoll.oradea.sonpoll.poll.model.Question;
import com.sonpoll.oradea.sonpoll.user.model.AccesToken;
import com.sonpoll.oradea.sonpoll.user.model.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SeedService {

    private static final Faker faker = new Faker();

    public static AccesToken accesToken() {
        final LocalDateTime expirationDate = faker.date().future(30, TimeUnit.MINUTES, Date.from(Instant.now())).toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        return AccesToken.builder()
                .token(faker.crypto().sha512())
                .expirationDate(expirationDate)
                .isUsed(faker.bool().bool())
                .build();
    }

    public static User user() {
        return User.builder()
                .username(faker.name().username())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .accesToken(accesToken())
                .build();
    }

    public static Poll poll(final String ownerId, final List<String> assignees) {
        final List<Question> questions = IntStream.rangeClosed(1, faker.number().numberBetween(1, 10))
                .mapToObj(index -> question())
                .toList();

        return Poll.builder()
                .title(faker.book().title())
                .owner(ownerId)
                .assignees(assignees)
                .publicResults(faker.bool().bool())
                .questions(questions)
                .build();
    }

    public static Question question() {
        final List<Option> options = IntStream.rangeClosed(1, faker.number().numberBetween(1, 10))
                .mapToObj(index -> option())
                .toList();

        return Question.builder()
                .isMultiselectAllowed(faker.bool().bool())
                .description(faker.lorem().sentence(20, 100))
                .title(faker.lorem().sentence(5, 10))
                .options(options)
                .build();
    }

    public static Option option() {
        final int typeIndex = faker.number().numberBetween(1, 3);

        final OPTION_TYPE type = switch (typeIndex) {
            case 1 -> OPTION_TYPE.TEXT;
            case 2 -> OPTION_TYPE.DATE;
            case 3 -> OPTION_TYPE.NUMBER;
            default -> throw new IllegalArgumentException();
        };

        var value = switch (type) {
            case TEXT -> faker.lorem().sentence(1, 10);
            case DATE -> faker.date().future(10, TimeUnit.DAYS);
            case NUMBER -> faker.number().numberBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);
        };

        return switch (type) {
            case TEXT -> new Option((String) value);
            case DATE -> new Option((Date) value);
            case NUMBER -> new Option((Double) value);
        };
    }
}
