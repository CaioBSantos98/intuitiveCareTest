package org.care.intuitive;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import technology.tabula.*;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class PdfTableExtractor {
    public void extractTable(String pdfPath, String csvPath) {
        buildDirIfNotExist(csvPath);

        try (PDDocument document = Loader.loadPDF(new File(pdfPath));
             FileWriter fileWriter = new FileWriter(csvPath);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {

            SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
            PageIterator pi = new ObjectExtractor(document).extract();

            while (pi.hasNext()) {
                Page page = pi.next();
                List<Table> tables = sea.extract(page);

                for (Table table : tables) {
                    List<List<RectangularTextContainer>> rows = table.getRows();

                    for (List<RectangularTextContainer> cells : rows) {

                        for (RectangularTextContainer content : cells) {
                            String text;
                            if (content.getText().isBlank() || content.getText().isEmpty()) {
                                text = "Vazio";
                            } else {
                                text = content.getText().replace("\r", " ");
                            }
                            System.out.print(text + "|");
                        }
                        System.out.println();
                    }
                }
            }


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void buildDirIfNotExist(String csvPath) {
        File csvFile = new File(csvPath);
        File parentDir = csvFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
    }
}
