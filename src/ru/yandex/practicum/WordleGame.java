package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static ru.yandex.practicum.WordleDictionary.calculateMask;
import static ru.yandex.practicum.WordleDictionary.editWord;
import static ru.yandex.practicum.WordleDictionary.isHiddenWord;

public class WordleGame {

    private String answer;
    private int steps;
    private WordleDictionary dictionary;
    private Log log;
    private List<String> rawWords;

    private LinkedHashMap<String, Integer> wordsUsed;

    WordleGame(WordleDictionary dictionary, Log log) {
        this.dictionary = dictionary;
        this.steps = 6;
        this.log = log;
        this.rawWords = new ArrayList<>(dictionary.getWords());
        this.wordsUsed = new LinkedHashMap<>();
        try {
            dictionary.setHiddenWord();
        } catch (Exception e) {
            log.toLog(e);
        }
    }


    public int getSteps() {
        return steps;
    }

    public void makeStep() {
        Scanner scanner = new Scanner(System.in);
        answer = null;
        while (answer == null) {
            System.out.println("Введите слово (или Enter для подсказки)");
            String input = scanner.nextLine().trim();
            try {
                if (!input.equals("")) {
                    answer = input;
                    if (isCorrectAnswer()) {
                        answer = editWord(answer);
                    } else {
                        answer = null;
                    }
                } else {
                    answer = getHint();
                    System.out.println(answer);
                }
            } catch (WordNotFoundException | IncorrectWordLengthException e) {
                System.out.println(e.getMessage());
                answer = null;
            } catch (Exception e) {
                log.toLog(e);
                answer = null;
            }
        }

        String mask = calculateMask(answer, dictionary.getHiddenWord());
        System.out.println(mask);

        wordsUsed.put(answer, 1);
        steps--;
    }

    public boolean isCorrectAnswer() throws WordNotFoundException, IncorrectWordLengthException {
        if (answer.matches("[a-zA-Z]+")) {
            System.out.println("Слово должно быть из русских букв");
            return false;
        }
        if (!dictionary.isWordLengthCorrect(answer)) {
            throw new IncorrectWordLengthException("Слово должно быть из 5 букв");
        }
        if (!dictionary.isWordInDictionary(answer)) {
            throw new WordNotFoundException("Такого слова нет в словаре");
        }
        if (wordsUsed.containsKey(editWord(answer))) {
            System.out.println("Вы уже использовали это слово");
            return false;
        }
        return true;
    }

    public String getHint() {
        String hint;
        if (steps == 6 || wordsUsed.isEmpty()) {
            hint = dictionary.getRandomWord();
            return hint;
        }
        String lastInput = "";
        for (String key : wordsUsed.keySet()) {
            lastInput = key;
        }
        String lastMask = calculateMask(lastInput, dictionary.getHiddenWord());

        List<String> maybeWords = new ArrayList<>();

        for (String word : rawWords) {
            if (wordsUsed.containsKey(word)) {
                continue;
            }
            if (calculateMask(lastInput, word).equals(lastMask)) {
                maybeWords.add(word);
            }
        }
        if (maybeWords.isEmpty()) {
            return dictionary.getRandomWord();
        }

        Random random = new Random();
        hint = maybeWords.get(random.nextInt(maybeWords.size()));
        return hint;
    }


    public boolean isWin() {
        return isHiddenWord(answer);
    }
}
