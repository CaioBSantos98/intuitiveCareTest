package org.care.intuitive;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class PdfZipper {
    public void zipFiles(File sourceDir, File zipFile) {
        if (!sourceDir.exists() || !sourceDir.isDirectory()) {
            System.out.println("Diretório de PDFs não encontrado!");
            return;
        }

        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zipOut = new ZipOutputStream(fos)) {

            File[] files = sourceDir.listFiles((dir, name) -> name.endsWith(".pdf"));
            if (files == null || files.length == 0) {
                System.out.println("Nenhum PDF encontrado para compactação.");
                return;
            }

            for (File file : files) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zipOut.putNextEntry(zipEntry);

                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        zipOut.write(buffer, 0, bytesRead);
                    }

                    zipOut.closeEntry();
                    System.out.println("Adicionado ao ZIP: " + file.getName());
                }
            }

            System.out.println("ZIP criado em: " + zipFile.getAbsolutePath());

        } catch (IOException e) {
            System.out.println("Erro ao criar ZIP: " + e.getMessage());
        }
    }
}
