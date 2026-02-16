package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class WordleDictionaryLoader {

    public WordleDictionary load(String file, Log log) throws IOException {
        File f = new File(file);

        if (!f.exists()) {
            log.toLog(new IOException("Файл не найден по пути: " + f.getAbsolutePath()));
            throw new FileNotFoundException("Файл словаря отсутствует.");
        }

        List<String> words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file, UTF_8))) {
            String line;
            while (br.ready()) {
                line = br.readLine().trim();
                words.add(line);
            }
        } catch (IOException e) {
            log.toLog(e);
        }
        WordleDictionary dictionary = new WordleDictionary(words, log);
        return dictionary;
    }
}
