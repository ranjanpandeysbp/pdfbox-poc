package com.example.demo.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.awt.*;
import java.io.IOException;

public class AddPDFTable {

    PDDocument document;
    PDPageContentStream contentStream;
    private int[] colWidths;
    private int cellHeight;
    private int yPosition;
    private int xPosition;
    private int colPosition;
    private int xInitialPosition;
    private float fontSize;
    private PDFont font;
    private Color fontColor;

    public AddPDFTable(PDDocument document, PDPageContentStream contentStream) {
        this.document = document;
        this.contentStream = contentStream;
    }

    void setTable(int[] colWidths, int cellHeight, int xPosition, int yPosition) {
        this.colWidths = colWidths;
        this.cellHeight = cellHeight;
        this.yPosition = yPosition;
        this.xPosition = xPosition;
        this.xInitialPosition = xPosition;
    }

    void setTableFont(PDFont pdFont, float fontSize, Color color){
        this.font = pdFont;
        this.fontSize = fontSize;
        this.fontColor = color;
    }

    void addCell(String text, Color fillColor) throws IOException {
        contentStream.setStrokingColor(1f);//1f = border 100% transparent
        if(fillColor != null){
            contentStream.setNonStrokingColor(fillColor);
        }
        contentStream.addRect(xPosition, yPosition, colWidths[colPosition], cellHeight);

        if(fillColor == null){
            contentStream.stroke();
        }else {
            contentStream.fillAndStroke();
        }

        contentStream.beginText();
        contentStream.setNonStrokingColor(fontColor);

        contentStream.setFont(font, fontSize);
        contentStream.showText(text);
        contentStream.endText();

        xPosition = xPosition+colWidths[colPosition];
        colPosition++;

        //once the col position reaches end set it back to start of next row
        if(colPosition == colWidths.length){
            colPosition = 0;
            xPosition = xInitialPosition;
            yPosition -= cellHeight;
        }
    }
}
