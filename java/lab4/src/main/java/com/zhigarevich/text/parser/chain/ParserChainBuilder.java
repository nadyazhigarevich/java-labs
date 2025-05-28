package com.zhigarevich.text.parser.chain;

import com.zhigarevich.text.parser.Parser;
import com.zhigarevich.text.parser.impl.*;

public class ParserChainBuilder {
    public static Parser buildParserChain() {
        Parser documentParser = new DocumentParser();
        Parser paragraphParser = new ParagraphParser();
        Parser sentenceParser = new SentenceParser();
        Parser lexemeParser = new LexemeParser();
        Parser wordParser = new WordParser();

        documentParser.setNextParser(paragraphParser);
        paragraphParser.setNextParser(sentenceParser);
        sentenceParser.setNextParser(lexemeParser);
        lexemeParser.setNextParser(wordParser);

        return documentParser;
    }
}