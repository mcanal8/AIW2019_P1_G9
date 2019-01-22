package utils.fileUtils;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileUtils {

    private final static Logger log = Logger.getLogger(FileUtils.class);
    private static final String TEXT_FILES_PATH = "Concisus-v3/text_files";
    private static final String RESULT_FILE_NAME = System.getProperty("user.dir") + "/resultFile.txt";
    private static final Path OUTPUT_PATH = Paths.get(RESULT_FILE_NAME);

    private FileUtils(){}

    public static void extract(){

        if(OUTPUT_PATH.toFile().exists()){
            OUTPUT_PATH.toFile().delete();
        }
        createOutputFileInDirectory();

        try(Stream<Path> paths = Files.walk(Paths.get(getResourceFolderPath()))) {
            paths.forEach(filePath -> {
                if (filePath.toFile().isFile()) {
                    try {
                        readContentFromFile(filePath.toFile());
                    } catch (Exception e) {
                        log.error(e.getMessage() != null ? e.getMessage() : "ERROR reading content.");
                    }
                }
            });
        } catch (IOException e) {
            log.error(e.getMessage() != null ? e.getMessage() : "ERROR extracting text from sample texts.");
        }
    }

    private static String getResourceFolderPath () {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(TEXT_FILES_PATH);
        assert url != null;
        return url.getFile();
    }

    private static void readContentFromFile(File file) throws IOException{

        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file.getAbsolutePath()), "ISO-8859-1"));
        String strLine;

        while((strLine = in.readLine()) != null){
            writeLine(strLine);
        }
    }

    private static Path createOutputFileInDirectory(){
        try {
            return Files.createFile(OUTPUT_PATH);
        } catch (IOException e) {
            log.error(e.getMessage() != null ? e.getMessage() : "ERROR creating output file.");
        }
        return null;
    }

    private static void writeLine(String line){

        try {
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(OUTPUT_PATH.toFile().getAbsolutePath(), true), StandardCharsets.ISO_8859_1));
            out.write(line);
            out.newLine();
            out.flush();
        } catch (IOException e) {
            log.error(e.getMessage() != null ? e.getMessage() : "ERROR writing line to output file.");
        }
    }
}

