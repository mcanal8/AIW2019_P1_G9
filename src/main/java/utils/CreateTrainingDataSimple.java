package utils;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import utils.fileUtils.FileUtils;
import utils.wekaUtils.WekaUtils;

import java.nio.file.Path;

public class CreateTrainingDataSimple {

    private final static Logger log = Logger.getLogger(CreateTrainingDataSimple.class);

    public static void main(String[] args) {

        // Logging config
        BasicConfigurator.configure();

        log.info("Text Classification and Extraction App. " +
                "This App Recognizes texts belonging to the following two domain: terrorist attacks and aviation accidents");

        // Extracting text from text_files and merging to resultFile.txt
        Path resultFilePath = FileUtils.extractDataToFile();

        // Generating WEKA .arff file
        assert resultFilePath != null;
        WekaUtils.generateArffFromFile(resultFilePath.toFile().getAbsoluteFile());
    }
}
