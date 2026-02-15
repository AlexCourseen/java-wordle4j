package ru.yandex.practicum;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */

import java.io.IOException;

import static ru.yandex.practicum.WordleDictionary.getHiddenWord;

public class Wordle {

    public static void main(String[] args) {
        try (Log log = new Log("log.txt")) {
            try {
                WordleDictionaryLoader loader = new WordleDictionaryLoader();
                WordleDictionary dictionary = loader.load("words_ru.txt", log);
                WordleGame game = new WordleGame(dictionary, log);
                play(game);
            } catch (Exception e) {
                log.toLog(e);
            }
        } catch (IOException e) {
            System.out.println("Не удалось создать лог-файл: " + e.getMessage());
        }
    }

    private static void play(WordleGame game) {
        System.out.println("Введите слово или [Enter] для подсказки.");

        while (game.getSteps() > 0) {
            System.out.print("Шагов осталось: " + game.getSteps() + " > ");
            game.makeStep();
            if (game.isWin()) {
                System.out.println("Вы угадали слово.");
                return;
            }
        }
        System.out.println("Попытки закончились. Вы проиграли.");
        System.out.println("Загаданное слово было: " + getHiddenWord());

    }
}

