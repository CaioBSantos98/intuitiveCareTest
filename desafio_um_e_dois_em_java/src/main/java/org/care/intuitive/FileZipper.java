package org.care.intuitive;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileZipper {
    public void zipFiles(File sourceDir, File zipFile) {
        if (!sourceDir.exists() || !sourceDir.isDirectory()) {
            System.out.println("Diretório não encontrado!");
            return;
        }

        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zipOut = new ZipOutputStream(fos)) {

            File[] files = sourceDir.listFiles();
            if (files == null || files.length == 0) {
                System.out.println("Nenhum arquivo encontrado para compactação.");
                return;
            }

            for (File file : files) {
                // Condicional adicionada para evitar possivel loop infinito caso tenha algum arquivo zip na pasta
                if (file.isFile() && !file.getAbsolutePath().equals(zipFile.getAbsolutePath())) {
                    addFileToZip(file, zipOut);
                }
            }
            System.out.println("ZIP criado em: " + zipFile.getParent());

        } catch (IOException e) {
            System.out.println("Erro ao criar ZIP: " + e.getMessage());
        }
    }

    public void zipFile(File file, String zipName) {
        if (!file.exists() || !file.isFile()) {
            System.out.println("Arquivo não encontrado ou não é um arquivo válido.");
            return;
        }

        String zipFileName = (zipName != null && !zipName.isBlank()) ? zipName : file.getName() + ".zip";
        File zipFile = new File(file.getParent(), zipFileName);

        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zipOut = new ZipOutputStream(fos)) {

            addFileToZip(file, zipOut);

            System.out.println("ZIP criado em: " + file.getParent());

        } catch (IOException e) {
            System.out.println("Erro ao criar ZIP: " + e.getMessage());
        }
    }

    private void addFileToZip(File file, ZipOutputStream zipOut) throws IOException {
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
}
