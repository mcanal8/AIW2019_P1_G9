package utils.fileUtils;

import enums.Domains;
import enums.RawDomains;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.ISO_8859_1;

public class FileUtils implements FileUtilsInterface{

    private final static Logger log = Logger.getLogger(FileUtils.class);
    private static final String TEXT_FILES_PATH = "Concisus-v3/text_files";
    private static final String RESULT_FILE_NAME = System.getProperty("user.dir") + "/resultFile.txt";
    private static final Path OUTPUT_PATH = Paths.get(RESULT_FILE_NAME);

    private FileUtils(){}

    public static Path extractDataToFile(){

        if(OUTPUT_PATH.toFile().exists()){
            OUTPUT_PATH.toFile().delete();
        }
        createOutputFileInDirectory();

        String url = getResourceFolderPath();

        if(SystemUtils.IS_OS_WINDOWS){
            url = url.replaceFirst("/","");
        }

        try(Stream<Path> paths = Files.walk(Paths.get(url))) {
            paths.forEach(filePath -> {
                if (filePath.toFile().isFile()) {
                    try {
                        readContentFromFile(filePath.toFile());
                    } catch (Exception e) {
                        log.error(e.getMessage() != null ? e.getMessage() : "ERROR reading content.");
                    }
                }
            });
            return OUTPUT_PATH;
        } catch (IOException e) {
            log.error(e.getMessage() != null ? e.getMessage() : "ERROR extracting text from sample texts.");
        }
        return null;
    }

    private static String getResourceFolderPath () {
        return FileUtilsInterface.getResourceFolderPath(TEXT_FILES_PATH);
    }

    private static void readContentFromFile(File file) throws IOException{

        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file.getAbsolutePath()),  ISO_8859_1));
        String strLine;

        while((strLine = in.readLine()) != null){
            writeLineToFile(strLine, OUTPUT_PATH.toFile(), file.getName());
        }
    }

    private static Path createOutputFileInDirectory(){
       return FileUtilsInterface.createOutputFileInDirectory(OUTPUT_PATH);
    }

    private static void writeLineToFile(String line, File file, String fileName){
        FileUtilsInterface.writeLineWithFileNameToFile(line, file, fileName);
    }

    static Domains getDomainFromString(String raw){
        if(raw.contains(RawDomains.AVIATION_ACCIDENT.value())){
            return Domains.AVIATION_ACCIDENT;
        }else if(raw.contains(RawDomains.EARTHQUAKE.value())){
            return Domains.EARTHQUAKE;
        }else if(raw.contains(RawDomains.TERRORIST_ATTACK.value())){
            return Domains.TERRORIST_ATTACK;
        }else if(raw.contains(RawDomains.TRAIN_ACCIDENT.value())){
            return Domains.TRAIN_ACCIDENT;
        }
        return null;
    }
}

