package org.care.intuitive;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileDownloader {
    public void downloadFile(String fileURL, String fileName) {
        try {
            URL url = new URL(fileURL);
            Path targetPath = Path.of("src", "main", "resources", "pdfs", fileName);

            Files.createDirectories(targetPath.getParent());

            Files.copy(url.openStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Download concluído: " + targetPath);
        } catch (IOException e) {
            System.out.println("Erro ao baixar " + fileName + ": " + e.getMessage());
        }
    }
}
