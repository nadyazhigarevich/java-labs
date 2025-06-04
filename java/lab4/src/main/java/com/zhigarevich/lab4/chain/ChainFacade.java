package com.zhigarevich.lab4.chain;

import com.zhigarevich.lab4.exception.ApplicationException;
import com.zhigarevich.lab4.handler.LetterHandler;
import com.zhigarevich.lab4.handler.ParagraphHandler;
import com.zhigarevich.lab4.handler.SentenceHandler;
import com.zhigarevich.lab4.handler.TranslationHandler;
import com.zhigarevich.lab4.handler.WordHandler;
import com.zhigarevich.lab4.model.contract.TextComponent;
import com.zhigarevich.lab4.reader.TextFileReader;
import com.zhigarevich.lab4.reader.TextPropertyReader;
import com.zhigarevich.lab4.writer.TextFileWriter;

public class ChainFacade {

    public static final String APPLICATION_PROPERTY_FILE_NAME = "application";

    private final TextPropertyReader textPropertyReader;
    private final TextFileReader textFileReader;
    private final TextFileWriter textFileWriter;

    public ChainFacade(TextPropertyReader textPropertyReader, TextFileReader textFileReader, TextFileWriter textFileWriter) {
        this.textPropertyReader = textPropertyReader;
        this.textFileReader = textFileReader;
        this.textFileWriter = textFileWriter;
    }

    public TextComponent run() throws ApplicationException {
        String prop = this.textPropertyReader.loadProperty(APPLICATION_PROPERTY_FILE_NAME);
        String data = this.textFileReader.read(prop);

        TranslationHandler translationHandler = new TranslationHandler();
        ParagraphHandler paragraphHandler = new ParagraphHandler();
        SentenceHandler sentenceHandler = new SentenceHandler();
        WordHandler wordHandler = new WordHandler();
        LetterHandler letterHandler = new LetterHandler();

        translationHandler.setNextHandler(paragraphHandler);
        paragraphHandler.setNextHandler(sentenceHandler);
        sentenceHandler.setNextHandler(wordHandler);
        wordHandler.setNextHandler(letterHandler);

        TextComponent document = translationHandler.handle(data);

        textFileWriter.write(document.getText());
        return document;
    }
}
