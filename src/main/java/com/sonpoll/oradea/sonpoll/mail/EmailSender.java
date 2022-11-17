package com.sonpoll.oradea.sonpoll.mail;

public interface EmailSender {
    void sendEmail(String toEmail, String link);
}
