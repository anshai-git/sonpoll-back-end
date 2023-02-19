db.createUser({
  user: 'admin',
  pwd: 'admin',
  roles: [
    {
      role: 'readWrite',
      db: 'sonpoll'
    }
  ]
});

db = new Mongo().getDB("sonpoll");
db.createCollection('users', { capped: false });
db.createCollection('polls', { capped: false });

db.users.insert([
    {
        username: "user_1",
        email: "email_1@sonpoll.com",
        password: "password_1"
    },
    {
        username: "user_2",
        email: "email_2@sonpoll.com",
        password: "password_2"
    },
    {
        username: "user_3",
        email: "email_3@sonpoll.com",
        password: "password_3"
    },
    {
        username: "user_4",
        email: "email_4@sonpoll.com",
        password: "password_4"
    },
    {
        username: "user_5",
        email: "email_5@sonpoll.com",
        password: "password_5"
    }
]);

const users = db.users.find();


db.polls.insert([
    {
        title: "Poll 1",
        owner: users.next()._id,
        publicResults: true,
        questions: [
            {
                title: "Question 1",
                description: "Description for question 1",
                isMultiSelectAllowed: false,
                options: [
                    {
                        type: "TEXT",
                        textValue: "Option 1",
                        dateValue: null,
                        numberValue: null,
                        userVotes: [users[1]._id, users[2]._id]
                    }
                ]
            }
        ]
    }
]);
