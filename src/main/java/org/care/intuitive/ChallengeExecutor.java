package org.care.intuitive;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ChallengeExecutor {

    private final FileDownloader fileDownloader = new FileDownloader();
    private final FileZipper fileZipper = new FileZipper();
    private final PdfTableExtractor extractor = new PdfTableExtractor();

    public void runChallengeOne() {
        List<String> urlsToDownload = getUrlsToDownload();
        for (String url : urlsToDownload) {
            fileDownloader.downloadFile(url, url.substring(url.lastIndexOf("/") + 1));
        }

        fileZipper.zipFiles(
                Path.of("src", "main", "resources", "pdfs").toFile(),
                Path.of("src", "main", "resources", "pdfs", "pdfs.zip").toFile()
        );
    }

    public void runChallengeTwo() {
        String pdfPath = "src/main/resources/pdfs/Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf";
        String csvPath = "src/main/resources/csvs/Anexo_I_Rol_2021RN_465.2021_RN627L.2024.csv";
        File csvFile = extractor.extractTableFromPdfAndSaveCsv(pdfPath, csvPath);
        fileZipper.zipFile(csvFile, "Teste_" + csvFile.getName().replace(".csv", "") + ".zip");
    }

    private List<String> getUrlsToDownload() {
        List<String> urls = new ArrayList<>();
        try {
            String url = "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos";

            Document doc = Jsoup.connect(url).get();

            Elements links = doc.select("a");

            for (Element link : links) {
                String href = link.attr("href");
                String text = link.text();

                if ((text.toLowerCase().contains("anexo i") || text.toLowerCase().contains("anexo ii"))
                        && href.endsWith(".pdf")) {
                    urls.add(href);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return urls;
    }
}
