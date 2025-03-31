package org.care.intuitive;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import technology.tabula.*;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;


public class PdfTableExtractor {
    public File extractTableFromPdfAndSaveCsv(String pdfPath, String csvPath) {
        File csvFile = createDirIfNotExist(csvPath);

        try (PDDocument document = Loader.loadPDF(new File(pdfPath));
             FileWriter fileWriter = new FileWriter(csvFile);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {

            SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
            PageIterator pi = new ObjectExtractor(document).extract();

            while (pi.hasNext()) {
                Page page = pi.next();
                List<Table> tables = sea.extract(page);

                for (Table table : tables) {
                    List<List<RectangularTextContainer>> rows = table.getRows();

                    for (List<RectangularTextContainer> cells : rows) {
                        StringBuilder row = new StringBuilder();

                        for (RectangularTextContainer content : cells) {
                            String text = content.getText().isBlank() ? "" : content.getText().replace("\r", "");
                            row.append("\"").append(text).append("\"").append(",");
                        }

                        if (!row.isEmpty()) {
                            row.setLength(row.length() - 1);
                        }

                        if (!row.toString().equals(" ")) {
                            writer.write(row.toString());
                            writer.newLine();
                        }
                    }
                }
            }

            replaceAbbreviations(csvFile);
            return csvFile;

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    private File createDirIfNotExist(String csvPath) {
        File csvFile = new File(csvPath);
        File parentDir = csvFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        return csvFile;
    }

    private void replaceAbbreviations(File file) {
        if (file == null || !file.exists()) {
            System.out.println("Arquivo não encontrado!");
            return;
        }

        if (!file.getName().endsWith(".csv")) {
            System.out.println("O arquivo não é um arquivo CSV válido!");
            return;
        }

        try {
            List<String> lines = Files.readAllLines(file.toPath());

            List<String> modifiedLines = lines.stream()
                    .map(line -> line
                            .replace(",OD,", ",SEG ODONTOLOGICA,")
                            .replace(",AMB,", ",SEG AMBULATORIAL,"))
                    .toList();

            Files.write(file.toPath(), modifiedLines);

        } catch (IOException e) {
            System.out.println("Erro ao modificar CSV: " + e.getMessage());
        }
    }
}
