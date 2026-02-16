package ru.yandex.practicum;

public class WordIsAlreadyUsedException extends RuntimeException {
    public WordIsAlreadyUsedException(String message) {
        super(message);
    }
}
