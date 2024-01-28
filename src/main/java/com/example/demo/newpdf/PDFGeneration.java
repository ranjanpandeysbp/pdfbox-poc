package com.example.demo.newpdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PDFGeneration {

    public static void main(String[] args) {

        List<ImgCoordinates> coordinatesList = new ArrayList<>();
        ImgCoordinates imgCoordinates = null;
        PDDocument pdDocument = new PDDocument();
        PDPage firstPage = new PDPage();
        pdDocument.addPage(firstPage);

        int pageHeight = (int) firstPage.getTrimBox().getHeight();
        int pageWidth = (int) firstPage.getTrimBox().getWidth();

        try {
            PDPageContentStream pdPageContentStream = new PDPageContentStream(pdDocument, firstPage);
            pdPageContentStream.setStrokingColor(Color.DARK_GRAY);
            pdPageContentStream.setLineWidth(1);

            // get the image
            PDImageXObject imageXObject = PDImageXObject.createFromFile("src/main/resources/image/0.jpg", pdDocument);
            pdPageContentStream.drawImage(imageXObject, 0, pageHeight-150, pageWidth, 150);

            //initial position of table
            int initX = 50;
            int initY = pageHeight - 160;
            //cell dimensions
            int cellHeight = 70;
            int cellWidth = 250;

            int colCount = 2;
            int rowCount = 5;
            Color tableHeaderColor = new Color(240, 93, 11);
            Color tableBodyColor = new Color(0, 0, 0);
            for(int i=0; i<= rowCount; i++){
                for(int j=0; j< colCount; j++){
                    //create cell content, top down so reduce cellHeight
                    pdPageContentStream.addRect(initX, initY, cellWidth, -cellHeight);
                    pdPageContentStream.beginText();
                    pdPageContentStream.newLineAtOffset(initX+10, initY-cellHeight+10);
                    pdPageContentStream.setFont(PDType1Font.HELVETICA, 16);
                    if(i==0 && j==0) {//header column 1
                        pdPageContentStream.setNonStrokingColor(tableHeaderColor);
                        pdPageContentStream.showText("Book Name");
                    }else if(i==0 && j==1) {//header column 2
                        pdPageContentStream.setNonStrokingColor(tableHeaderColor);
                        pdPageContentStream.showText("Price");
                    }
                    else if(i!=0 && j%2!=0) {//column 2 data
                        imgCoordinates = new ImgCoordinates();
                        imgCoordinates.setxCoordinate(initX);
                        imgCoordinates.setyCoordinate(initY);
                        imgCoordinates.setImgName(String.valueOf(i));

                        coordinatesList.add(imgCoordinates);
                        pdPageContentStream.setNonStrokingColor(tableBodyColor);
                        pdPageContentStream.showText(String.valueOf(i+Math.random()));
                    }
                    else{
                        pdPageContentStream.setNonStrokingColor(tableBodyColor);
                        pdPageContentStream.showText("Dummy Book "+i+1);
                    }
                    pdPageContentStream.endText();
                    initX += cellWidth;//for 2nd column increase width
                }
                //for next row set X position to initial, and reduce the height
                initX = 50;
                initY -= cellHeight;
            }
            for (ImgCoordinates imgCoor: coordinatesList){
                imageXObject = PDImageXObject.createFromFile("src/main/resources/image/"+imgCoor.getImgName()+".jpg", pdDocument);
                pdPageContentStream.drawImage(imageXObject, imgCoor.getxCoordinate()+20, imgCoor.getyCoordinate()-40, 130, 30);
            }

            pdPageContentStream.stroke();
            pdPageContentStream.close();

            pdDocument.save("demo.pdf");
            pdDocument.close();

            System.out.println("PDF Generated");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}