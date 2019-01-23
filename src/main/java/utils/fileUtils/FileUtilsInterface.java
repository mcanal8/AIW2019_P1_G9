package utils.fileUtils;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.charset.StandardCharsets.ISO_8859_1;

@SuppressWarnings("Duplicates")
public interface FileUtilsInterface {

    Logger log = Logger.getLogger(FileUtils.class);

    static Path createOutputFileInDirectory(Path outputPath){
        try {
            return Files.createFile(outputPath);
        } catch (IOException e) {
            log.error(e.getMessage() != null ? e.getMessage() : "ERROR creating output file.");
        }
        return null;
    }

    static String getResourceFolderPath (String folderPath) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(folderPath);
        assert url != null;
        return url.getFile();
    }

    static void writeLineWithFileNameToFile(String line, File file, String fileName){
        try {
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(file.getAbsolutePath(), true), ISO_8859_1));
            out.write("'" + line + "'" + " ," + FileUtils.getDomainFromString(fileName));
            out.newLine();
            out.flush();
            out.close();
        } catch (IOException e) {
            log.error(e.getMessage() != null ? e.getMessage() : "ERROR writing line to output file.");
        }
    }

    static void writeLineToFile(String line, File file){
        try {
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(file.getAbsolutePath(), true), ISO_8859_1));
            out.write(line);
            out.newLine();
            out.flush();
            out.close();
        } catch (IOException e) {
            log.error(e.getMessage() != null ? e.getMessage() : "ERROR writing line to output file.");
        }
    }

}
