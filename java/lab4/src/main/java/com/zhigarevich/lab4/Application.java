package com.zhigarevich.lab4;

import com.zhigarevich.lab4.exception.ApplicationException;
import com.zhigarevich.lab4.chain.ChainFacade;
import com.zhigarevich.lab4.model.contract.TextComponent;
import com.zhigarevich.lab4.reader.impl.TextFileReaderImpl;
import com.zhigarevich.lab4.reader.impl.TextPropertyReaderImpl;
import com.zhigarevich.lab4.service.TextService;
import com.zhigarevich.lab4.service.impl.TextServiceImpl;
import com.zhigarevich.lab4.writer.impl.TextFileWriterImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.zhigarevich.lab4.util.DocumentPrinterUtil.printDocumentStructure;

public class Application {

    private static final Logger log = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        try {
            var facade = new ChainFacade(new TextPropertyReaderImpl(), new TextFileReaderImpl(), new TextFileWriterImpl());
            TextComponent document = facade.run();
            TextService textService = new TextServiceImpl();
            textService.sortParagraphsBySentenceCount(document);
            log.info("\n=== After Sorting Paragraphs ===");
            printDocumentStructure(document, 0);

            // Find sentences with longest word
            var longestWordSentences = textService.findSentencesWithLongestWord(document);
            log.info("\nSentences with longest word:");
            longestWordSentences.forEach(s -> log.info("- {}", s.getText()));

            // Count duplicate words
            var duplicates = textService.countDuplicateWords(document);
            log.info("\nDuplicate words:");
            duplicates.forEach((word, count) -> log.info("{}: {}", word, count));

            // Count vowels and consonants
            var vowelCounts = textService.countVowelsAndConsonants(document);
            log.info("\nVowel/Consonant counts:");
            vowelCounts.forEach((sentence, counts) ->
                    log.info("{}: Vowels={}, Consonants={}",
                            sentence.getText(), counts[0], counts[1]));

            // Find max word length
            int maxLength = textService.findMaxWordLength(document);
            log.info("\nMaximum word length: {}", maxLength);

            // Find sentences with specific word length
            var lengthSentences = textService.collectSentencesWithWordLength(document, 5);
            log.info("\nSentences with 5-letter words:");
            lengthSentences.forEach(s -> log.info("- {}", s.getText()));

            // Remove short sentences
            textService.removeShortSentences(document, 3);
            log.info("\n=== After Removing Short Sentences ===");
            printDocumentStructure(document, 0);
        } catch (ApplicationException e) {
        }
    }
}
