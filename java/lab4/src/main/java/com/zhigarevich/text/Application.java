package com.zhigarevich.text;

import com.zhigarevich.text.interpreter.ExpressionInterpreter;
import com.zhigarevich.text.interpreter.impl.ArithmeticInterpreterImpl;
import com.zhigarevich.text.model.*;
import com.zhigarevich.text.parser.*;
import com.zhigarevich.text.parser.chain.ParserChainBuilder;
import com.zhigarevich.text.service.*;
import com.zhigarevich.text.service.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.nio.file.*;

public class Application {
    private static final Logger logger = LogManager.getLogger();
    private static final String INPUT_FILE = "input.txt";
    private static final String OUTPUT_DIR = "output";

    public static void main(String[] args) {
        try {
            // 1. Инициализация
            ExpressionInterpreter interpreter = new ArithmeticInterpreterImpl();
            Parser parser = ParserChainBuilder.buildParserChain(interpreter);
            TextProcessor processor = new TextProcessor(parser);
            TextOperationService service = new TextOperationServiceImpl();

            // 2. Чтение и обработка
            String text = readFile(INPUT_FILE);
            TextComponent document = processor.processText(text);

            // 3. Создание директории для результатов
            Files.createDirectories(Paths.get(OUTPUT_DIR));

            // 4. Выполнение операций
            processOperation(service, document, "sort");
            processOperation(service, document, "longest");
            processOperation(service, document, "remove");
            processOperation(service, document, "count");
            processOperation(service, document, "vowels");

            logger.info("Обработка завершена успешно");
        } catch (Exception e) {
            logger.error("Ошибка в приложении: " + e.getMessage(), e);
        }
    }

    private static void processOperation(TextOperationService service,
                                         TextComponent document,
                                         String operation) throws IOException {
        String filename = OUTPUT_DIR + "/" + operation + ".txt";
        String content;

        switch (operation) {
            case "sort":
                content = service.sortParagraphsBySentenceCount(document).getText();
                break;
            case "longest":
                content = service.findSentencesWithLongestWord(document).getText();
                break;
            case "remove":
                content = service.removeShortSentences(document, 3).getText();
                break;
            case "count":
                content = "Повторяющиеся слова: " + service.countSameWords(document);
                break;
            case "vowels":
                content = service.countVowelsAndConsonants(document);
                break;
            default:
                throw new IllegalArgumentException("Неизвестная операция: " + operation);
        }

        Files.writeString(Paths.get(filename), content);
        logger.info("Результат операции '{}' сохранен в {}", operation, filename);
    }

    private static String readFile(String path) throws IOException {
        return Files.readString(Paths.get(path));
    }
}