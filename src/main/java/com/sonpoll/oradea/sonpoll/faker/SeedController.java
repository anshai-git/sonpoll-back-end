package com.sonpoll.oradea.sonpoll.faker;

import com.sonpoll.oradea.sonpoll.poll.model.Option;
import com.sonpoll.oradea.sonpoll.poll.model.Poll;
import com.sonpoll.oradea.sonpoll.poll.model.Question;
import com.sonpoll.oradea.sonpoll.poll.repository.PollRepository;
import com.sonpoll.oradea.sonpoll.user.model.User;
import com.sonpoll.oradea.sonpoll.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/faker")
public class SeedController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PollRepository pollRepository;

    @GetMapping("/user/{count}")
    public List<User> seedUsers(@PathVariable final Integer count) {
        return IntStream.rangeClosed(1, count)
                .mapToObj(index -> SeedService.user())
                .map(userRepository::save)
                .toList();
    }

    @GetMapping("/poll/{count}")
    public List<Poll> seedPolls(@PathVariable final Integer count) {
        final List<User> users = userRepository.findAll();
        final User user = getRandomUser(users);

        return IntStream.rangeClosed(1, count)
                .mapToObj(index -> SeedService.poll(user.getId(), getRandomAssigneesForUser(user, users)))
                .map(pollRepository::save)
                .toList();
    }

    @GetMapping("/vote/{count}")
    public List<String> seedVotes(@PathVariable final Integer count) {
        final List<User> users = userRepository.findAll();
        final List<Poll> polls = pollRepository.findAll();

        return IntStream.rangeClosed(1, count)
                .mapToObj(index -> vote(users, polls))
                .filter(Objects::nonNull)
                .toList();
    }

    private String vote(List<User> users, List<Poll> polls) {
        final User user = getRandomUser(users);
        final Optional<Poll> poll = getRandomPollForUser(user, polls);

        AtomicReference<Optional<Question>> question = new AtomicReference<>(Optional.empty());
        poll.ifPresent(p -> question.set(getRandomQuestionForUser(user, p.getQuestions())));

        AtomicReference<Optional<Option>> option = new AtomicReference<>(Optional.empty());
        question.get().ifPresent(q -> option.set(getRandomOptionForUser(user, q.getOptions())));

        if (option.get().isPresent()) {
            final Option optionChoosed = option.get().get();
            optionChoosed.getUsersVotes().add(user.getId());
            pollRepository.save(poll.get());

            var value = switch (option.get().get().getType()) {
                case TEXT -> option.get().get().getTextValue();
                case DATE -> option.get().get().getDateValue();
                case NUMBER -> option.get().get().getNumberValue();

                default -> throw new IllegalArgumentException();
            };

            return String.format("USER [%s] WITH ID: [%s] VOTED IN POLL WITH ID: [%s] IN QUESTION [%s] CHOSEN OPTION: [%s]", user.getUsername(), user.getId(), poll.get().getId(), question.get().get().getTitle(), value);
        } else {
            return null;
        }
    }

    private User getRandomUser(final List<User> users) {
        return users.get((int) ((Math.random() * users.size()) - 1));
    }

    private Optional<Poll> getRandomPollForUser(final User user, final List<Poll> polls) {
        final Predicate<Poll> EXCLUD_OWNED = p -> !user.getId().equals(p.getOwner());
        final Predicate<Poll> EXCLUDE_NON_ASSIGNED = p -> p.getAssignees().stream().anyMatch(a -> user.getId().equals(a));
        final Predicate<Poll> CHOOSE_RANDOMLY = u -> (Math.random() * 10) > 5;

        return polls.stream()
                .filter(EXCLUD_OWNED)
                .filter(EXCLUDE_NON_ASSIGNED)
                .filter(CHOOSE_RANDOMLY)
                .findFirst();
    }

    private Optional<Question> getRandomQuestionForUser(final User user, final List<Question> questions) {
        final Predicate<Question> EXCLUDE_ALREADY_VOTED = q -> q.isMultiselectAllowed() || q.getOptions().stream()
                .flatMap(o -> o.getUsersVotes().stream())
                .noneMatch(vote -> vote.equals(user.getId()));
        final Predicate<Question> CHOOSE_RANDOMLY = q -> (Math.random() * 10) > 5;

        return questions.stream()
                .filter(EXCLUDE_ALREADY_VOTED)
                .filter(CHOOSE_RANDOMLY)
                .findFirst();
    }

    private Optional<Option> getRandomOptionForUser(final User user, final List<Option> options) {
        final Predicate<Option> EXCLUDE_ALREADY_VOTED = o -> o.getUsersVotes().stream().noneMatch(v -> v.equals(user.getId()));

        return options.stream()
                .filter(EXCLUDE_ALREADY_VOTED)
                .findFirst();
    }

    private List<String> getRandomAssigneesForUser(final User user, final List<User> users) {
        final Predicate<User> EXCLUDE_SELF = u -> !user.getId().equals(u.getId());
        final Predicate<User> CHOOSE_RANDOMLY = u -> (Math.random() * 10) > 5;

        return users.stream()
                .filter(EXCLUDE_SELF)
                .filter(CHOOSE_RANDOMLY)
                .map(User::getId)
                .toList();
    }
}