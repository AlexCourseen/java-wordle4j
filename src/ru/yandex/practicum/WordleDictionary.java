package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordleDictionary {

    private final List<String> words;
    private static String hiddenWord;


    WordleDictionary(List<String> wordsToEdit) {
        this.words = getEditedDictionary(wordsToEdit);
    }

    private List<String> getEditedDictionary(List<String> words) {
        List<String> editedDictionary = new ArrayList<>();
        if (!words.isEmpty()) {
            for (String word : words) {
                if (isWordLengthCorrect(word)) {
                    String editedWord = editWord(word);
                    editedDictionary.add(editedWord);
                }
            }
        }
        return editedDictionary;
    }

    public List<String> getWords() {
        return words;
    }

    public static String editWord(String word) {
        return word.toLowerCase().replace('ё', 'е');
    }

    public String getRandomWord() {
        Random random = new Random(); // Не передавайте сюда размер списка
        return words.get(random.nextInt(words.size()));
    }

    public void setHiddenWord() {
        this.hiddenWord = getRandomWord();
    }

    public static boolean isHiddenWord(String word) {
        return word.equals(hiddenWord);
    }

    public boolean isWordInDictionary(String word) {
        return words.contains(word);
    }

    public boolean isWordLengthCorrect(String word) {
        int correctLength = 5;
        return word.length() == correctLength;
    }

    public static String calculateMask(String answer, String target) {
        char[] result = new char[5];
        char[] targetChar = target.toCharArray();
        char[] answerChar = answer.toCharArray();

        boolean[] targetMatch = new boolean[5];
        boolean[] answerMatch = new boolean[5];

        for (int i = 0; i < 5; i++) {
            if (answerChar[i] == targetChar[i]) {
                result[i] = '+';
                targetMatch[i] = true;
                answerMatch[i] = true;
            }
        }

        for (int i = 0; i < 5; i++) {
            if (!answerMatch[i]) {
                result[i] = '-';
                for (int j = 0; j < 5; j++) {
                    if (!targetMatch[j] && answerChar[i] == targetChar[j]) {
                        result[i] = '^';
                        targetMatch[j] = true;
                        break;
                    }
                }
            }
        }
        return new String(result);
    }

    public static String getHiddenWord() {
        return hiddenWord;
    }
}
