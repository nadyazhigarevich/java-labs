package com.zhigarevich.text;

import com.zhigarevich.text.model.TextComponent;
import com.zhigarevich.text.parser.Parser;
import com.zhigarevich.text.parser.chain.ParserChainBuilder;
import com.zhigarevich.text.service.TextOperationService;
import com.zhigarevich.text.service.impl.TextOperationServiceImpl;
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
            String text = readFile(INPUT_FILE);
            logger.info("Файл успешно прочитан");

            Parser parser = ParserChainBuilder.buildParserChain();
            TextComponent document = parser.parse(text);
            logger.info("Текст успешно распарсен");

            TextOperationService service = new TextOperationServiceImpl();

            Files.deleteIfExists(Paths.get(OUTPUT_FILE));

            TextComponent sortedText = service.sortParagraphsBySentenceCount(document);
            writeResultToFile("=== Результат сортировки абзацев ===", sortedText);

            TextComponent longWordSentences = service.findSentencesWithLongestWord(document);
            writeResultToFile("\n=== Предложения с самыми длинными словами ===", longWordSentences);

            TextComponent filteredText = service.removeShortSentences(document, 3);
            writeResultToFile("\n=== Текст после удаления коротких предложений (менее 3 слов) ===", filteredText);

            int sameWordsCount = service.countSameWords(document);
            String sameWordsResult = String.format("\n=== Количество повторяющихся слов ===\n%d\n", sameWordsCount);
            Files.write(Paths.get(OUTPUT_FILE),
                    sameWordsResult.getBytes(),
                    java.nio.file.StandardOpenOption.APPEND);

            String vowelsConsonants = service.countVowelsAndConsonants(document);
            String lettersResult = String.format("\n=== Количество букв ===\n%s\n", vowelsConsonants);
            Files.write(Paths.get(OUTPUT_FILE),
                    lettersResult.getBytes(),
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
        String result = header + "\n" + text.toString() + "\n\n";
        Path outputPath = Paths.get(OUTPUT_FILE);

        if (!Files.exists(outputPath)) {
            Files.createDirectories(outputPath.getParent());
            Files.createFile(outputPath);
        }

        Files.write(outputPath,
                result.getBytes(),
                java.nio.file.StandardOpenOption.CREATE,
                java.nio.file.StandardOpenOption.APPEND);
    }
}
