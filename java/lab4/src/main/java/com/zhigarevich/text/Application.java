package com.zhigarevich.text;

import com.zhigarevich.text.model.*;
import com.zhigarevich.text.parser.*;
import com.zhigarevich.text.parser.chain.ParserChainBuilder;
import com.zhigarevich.text.service.*;
import com.zhigarevich.text.service.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Application {
    private static final Logger logger = LogManager.getLogger();
    private static final String INPUT_FILE = "data/input.txt";
    private static final String OUTPUT_FILE = "data/output.txt";

    public static void main(String[] args) {
        try {
            // 1. Чтение файла
            String text = readFile(INPUT_FILE);
            logger.info("Файл успешно прочитан");

            // 2. Парсинг текста
            Parser parser = ParserChainBuilder.buildParserChain();
            TextComponent document = parser.parse(text);
            logger.info("Текст успешно распарсен");

            // 3. Обработка текста
            TextOperationService service = new TextOperationServiceImpl();

            // Пример выполнения операций:
            // a) Сортировка абзацев
            TextComponent sortedText = service.sortParagraphsBySentenceCount(document);
            writeResultToFile("Результат сортировки абзацев:", sortedText);

            // b) Предложения с самыми длинными словами
            TextComponent longWordSentences = service.findSentencesWithLongestWord(document);
            writeResultToFile("\nПредложения с самыми длинными словами:", longWordSentences);

            // c) Удаление коротких предложений
            TextComponent filteredText = service.removeShortSentences(document, 3);
            writeResultToFile("\nТекст после удаления коротких предложений:", filteredText);

            // d) Подсчет одинаковых слов
            int sameWordsCount = service.countSameWords(document);
            Files.write(Paths.get(OUTPUT_FILE),
                    ("\nКоличество одинаковых слов: " + sameWordsCount).getBytes(),
                    java.nio.file.StandardOpenOption.APPEND);

            // e) Подсчет гласных/согласных
            String vowelsConsonants = service.countVowelsAndConsonants(document);
            Files.write(Paths.get(OUTPUT_FILE),
                    ("\n" + vowelsConsonants).getBytes(),
                    java.nio.file.StandardOpenOption.APPEND);

            logger.info("Обработка текста завершена. Результаты в файле " + OUTPUT_FILE);

        } catch (Exception e) {
            logger.error("Ошибка при обработке текста: " + e.getMessage(), e);
        }
    }

    private static String readFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return new String(Files.readAllBytes(path));
    }

    private static void writeResultToFile(String header, TextComponent text) throws IOException {
        String result = header + "\n" + text.toString() + "\n";
        Files.write(Paths.get(OUTPUT_FILE),
                result.getBytes(),
                java.nio.file.StandardOpenOption.CREATE,
                java.nio.file.StandardOpenOption.APPEND);
    }
}