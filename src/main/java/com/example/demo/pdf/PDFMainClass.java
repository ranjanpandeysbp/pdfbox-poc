package com.example.demo.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.*;
import java.io.IOException;

public class PDFMainClass {

    public static void main(String[] args) throws IOException {
        PDDocument pdDocument = new PDDocument();
        PDPage firstPage = new PDPage(PDRectangle.A4);
        pdDocument.addPage(firstPage);

        int pageHeight = (int) firstPage.getTrimBox().getHeight();
        int pageWidth = (int) firstPage.getTrimBox().getWidth();


        PDFont font = PDType1Font.HELVETICA;
        PDFont italicFont = PDType1Font.HELVETICA_OBLIQUE;

        PDPageContentStream contentStream = new PDPageContentStream(pdDocument, firstPage);

        // get the image
        PDImageXObject imageXObject = PDImageXObject.createFromFile("src/main/resources/image/defaultImg.jpg", pdDocument);
        //contentStream.drawImage(imageXObject, 0, pageHeight-150, pageWidth, 150);
        contentStream.drawImage(imageXObject, 25, pageHeight-200, 80, 25);


        //Add data to table
        AddPDFTable addPDFTable = new AddPDFTable(pdDocument, contentStream);
        int[] cellWidths = {100, 200};
        addPDFTable.setTable(cellWidths, 30, 25, pageHeight-200);
        addPDFTable.setTableFont(font, 16, Color.BLACK);

        Color tableHeaderColor = new Color(240, 93, 11);
        Color tableBodyColor = new Color(219, 218, 198);

        addPDFTable.addCell("Book Name", tableHeaderColor);
        addPDFTable.addCell("Book Image", tableHeaderColor);

        addPDFTable.addCell("Harry Porter", tableBodyColor);
        addPDFTable.addCell("Rich Dad Poor Dad", tableBodyColor);


        contentStream.close();
        pdDocument.save("newPdf.pdf");
        pdDocument.close();
        System.out.println("Done!");






    }
}
