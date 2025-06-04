package com.zhigarevich.lab4.util;

import com.zhigarevich.lab4.model.contract.TextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DocumentPrinterUtil {
    public static final Logger log = LogManager.getLogger(DocumentPrinterUtil.class);

    public static void printDocumentStructure(TextComponent component, int level) {
        StringBuilder indent = new StringBuilder();
        indent.append("  ".repeat(Math.max(0, level)));
        log.info("{} {}: {}", indent, component.getClass().getSimpleName(),
                component.getText().replace("\n", "\\n"));

        for (TextComponent child : component.getChildren()) {
            printDocumentStructure(child, level + 1);
        }
    }
}