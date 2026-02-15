package ru.yandex.practicum;

public class WordNotFoundException extends Exception {
    WordNotFoundException(String message) {
        super(message);
    }
}
