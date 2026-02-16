package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static ru.yandex.practicum.WordleDictionary.editWord;

public class WordleGame {

    private String answer;
    private int steps;
    private WordleDictionary dictionary;
    private Log log;
    private List<String> rawWords;
    private Random random = new Random();


    private LinkedList<String> wordsUsed;

    public WordleGame(WordleDictionary dictionary, Log log) {
        this.dictionary = dictionary;
        this.steps = 6;
        this.log = log;
        this.rawWords = new ArrayList<>(dictionary.getWords());
        this.wordsUsed = new LinkedList<>();
        dictionary.setHiddenWord();
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
                    checkAnswer();
                } else {
                    answer = getHint();
                    System.out.println(answer);
                }
            } catch (WordNotFoundException | IncorrectWordLengthException | WordIsNotCirillicException
                     | WordIsAlreadyUsedException e) {
                System.out.println(e.getMessage());
                log.toLog(e);
                answer = null;
            } catch (Exception e) {
                log.toLog(e);
                answer = null;
            }
        }

        String mask = dictionary.calculateMask(answer, dictionary.getHiddenWord());
        System.out.println(mask);

        wordsUsed.add(answer);
        steps--;
    }

    public void checkAnswer() throws WordNotFoundException, IncorrectWordLengthException, WordIsNotCirillicException,
            WordIsAlreadyUsedException {
        if (answer.matches("[a-zA-Z]+")) {
            throw new WordIsNotCirillicException("Используйте только русские буквы");
        }
        if (!dictionary.isWordLengthCorrect(answer)) {
            throw new IncorrectWordLengthException("Слово должно быть из 5 букв");
        }
        if (!dictionary.isWordInDictionary(answer)) {
            throw new WordNotFoundException("Слова нет в словаре");
        }
        if (wordsUsed.contains(editWord(answer))) {
            throw new WordIsAlreadyUsedException("Вы уже использовали это слово");
        }
    }


    public String getHint() {
        String hint;
        if (steps == 6 || wordsUsed.isEmpty()) {
            hint = dictionary.getRandomWord();
            return hint;
        }
        String lastInput = wordsUsed.getLast();
        String lastMask = dictionary.calculateMask(lastInput, dictionary.getHiddenWord());

        List<String> maybeWords = new ArrayList<>();

        for (String word : rawWords) {
            if (wordsUsed.contains(word)) {
                continue;
            }
            if (dictionary.calculateMask(lastInput, word).equals(lastMask)) {
                maybeWords.add(word);
            }
        }
        if (maybeWords.isEmpty()) {
            return dictionary.getRandomWord();
        }

        hint = maybeWords.get(random.nextInt(maybeWords.size()));
        return hint;
    }


    public boolean isWin() {
        return dictionary.isHiddenWord(answer);
    }
}
