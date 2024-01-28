package com.example.demo.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.awt.*;
import java.io.IOException;

public class AddPDFText {

    PDDocument document;
    PDPageContentStream pdPageContentStream;

    public AddPDFText(PDDocument document, PDPageContentStream pdPageContentStream) {
        this.document = document;
        this.pdPageContentStream = pdPageContentStream;
    }

    void addSingleLineText(String text, int posX, int posY, PDFont pdFont, float fontSize, Color color) throws IOException {
        pdPageContentStream.beginText();
        pdPageContentStream.setFont(pdFont, fontSize);
        pdPageContentStream.setStrokingColor(color);
        pdPageContentStream.newLineAtOffset(posX, posY);
        pdPageContentStream.showText(text);
        pdPageContentStream.endText();
        pdPageContentStream.moveTo(0,0);
    }
}
