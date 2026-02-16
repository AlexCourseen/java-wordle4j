package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordleDictionary {

    private final Log log;
    private final List<String> words;
    private String hiddenWord;
    private Random random = new Random();
    private final int correctLength = 5;

    public WordleDictionary(List<String> wordsToEdit, Log log) {
        this.log = log;
        this.words = getEditedDictionary(wordsToEdit);
    }

    private List<String> getEditedDictionary(List<String> words) {
        List<String> editedDictionary = new ArrayList<>();
        try {
            if (!words.isEmpty()) {
                for (String word : words) {
                    if (isWordLengthCorrect(word)) {
                        String editedWord = editWord(word);
                        editedDictionary.add(editedWord);
                    }
                }
            }
        } catch (DictionaryIsEmptyException e) {
            log.toLog(e);
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
        return words.get(random.nextInt(words.size()));
    }

    public void setHiddenWord() {
        this.hiddenWord = getRandomWord();
    }

    public boolean isHiddenWord(String word) {
        return word.equals(hiddenWord);
    }

    public boolean isWordInDictionary(String word) {
        return words.contains(word);
    }

    public boolean isWordLengthCorrect(String word) {
        return word.length() == correctLength;
    }

    /* Не стал "упрощать" метод, т.к. на нём завязано получение подсказок, которые помогают подобрать решение.
    Слишком много времени, боли, пота, крови было в него вложено ( сейчас игра работает как просят в тз
    */
    public String calculateMask(String answer, String target) {
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

    public String getHiddenWord() {
        return hiddenWord;
    }
}
