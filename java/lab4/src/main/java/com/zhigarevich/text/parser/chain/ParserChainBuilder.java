package com.zhigarevich.text.parser.chain;

import com.zhigarevich.text.parser.Parser;
import com.zhigarevich.text.parser.impl.DocumentParser;
import com.zhigarevich.text.parser.impl.LexemeParser;
import com.zhigarevich.text.parser.impl.ParagraphParser;
import com.zhigarevich.text.parser.impl.SentenceParser;
import com.zhigarevich.text.parser.impl.WordParser;

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