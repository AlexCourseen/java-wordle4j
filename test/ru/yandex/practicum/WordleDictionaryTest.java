package ru.yandex.practicum;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordleDictionaryTest {

    @Test
    void shouldReturnFullMatchForSameWords() {
        String mask = WordleDictionary.calculateMask("арбуз", "арбуз");
        assertEquals("+++++", mask, "Должно быть 5 плюсов при полном совпадении");
    }

    @Test
    void shouldReturnCorrectSymbolsForPartialMatch() {
        String mask = WordleDictionary.calculateMask("аккор", "арбуз");
        assertEquals("+---^", mask);

    }

    @Test
    void shouldNormalizeWordCorrectly() {
        String result = WordleDictionary.editWord("ПлЁткА");
        assertEquals("плетка", result, "Должно приводить к нижнему регистру и заменять ё на е");
    }
}
