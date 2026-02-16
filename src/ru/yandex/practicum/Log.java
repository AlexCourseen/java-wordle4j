package ru.yandex.practicum;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class Log implements AutoCloseable {

    private final PrintWriter writer;

    public Log(String fileLog) throws IOException {
        this.writer = new PrintWriter(new FileWriter(fileLog, StandardCharsets.UTF_8));
    }

    public void toLog(Exception e) {
        e.printStackTrace(writer);
    }

    @Override
    public void close() {
        if (writer != null) {
            writer.close();
        }
    }
}


