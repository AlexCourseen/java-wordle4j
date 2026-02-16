package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordleDictionaryTest {
    private static WordleDictionary dictionary;

    @BeforeAll
    public static void beforeAll() {
        try (Log log = new Log("log.txt")) {
            try {
                WordleDictionaryLoader loader = new WordleDictionaryLoader();
                dictionary = loader.load("words_ru.txt", log);
            } catch (Exception e) {
                log.toLog(e);
            }
        } catch (IOException e) {
            System.out.println("Не удалось создать лог-файл: " + e.getMessage());
        }
    }

    @Test
    void shouldReturnFullMatchForSameWords() {
        String mask = dictionary.calculateMask("арбуз", "арбуз");
        assertEquals("+++++", mask, "Должно быть 5 плюсов при полном совпадении");
    }

    @Test
    void shouldReturnCorrectSymbolsForPartialMatch() {
        String mask = dictionary.calculateMask("аккор", "арбуз");
        assertEquals("+---^", mask);

    }

    @Test
    void shouldNormalizeWordCorrectly() {
        String result = WordleDictionary.editWord("ПлЁткА");
        assertEquals("плетка", result, "Должно приводить к нижнему регистру и заменять ё на е");
    }
}
