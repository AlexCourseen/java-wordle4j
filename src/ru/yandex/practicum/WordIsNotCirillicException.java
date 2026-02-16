package ru.yandex.practicum;

public class WordIsNotCirillicException extends RuntimeException {
    public WordIsNotCirillicException(String message) {
        super(message);
    }
}
