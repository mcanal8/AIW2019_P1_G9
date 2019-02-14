package utils.wekaUtils;

import enums.Domains;
import enums.WekaAnnotations;
import enums.WekaAttributesTypes;
import org.apache.log4j.Logger;
import utils.MultilingualTextClassificationAndInformationExtraction;
import utils.fileUtils.FileUtilsInterface;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.ISO_8859_1;

public class WekaUtils implements FileUtilsInterface{

    private final static Logger log = Logger.getLogger(MultilingualTextClassificationAndInformationExtraction.class);
    public static final String RESULT_ARFF_FILE_NAME = System.getProperty("user.dir") + "/data_classifier.arff";
    private static final Path OUTPUT_PATH = Paths.get(RESULT_ARFF_FILE_NAME);
    private static final String RELATION_NAME = "category";

    public static Path generateArffFromFile(File file){

        if(OUTPUT_PATH.toFile().exists()){
            OUTPUT_PATH.toFile().delete();
        }
        createOutputFileInDirectory();
        buildRelationHeader(RELATION_NAME);
        buildAttributes();
        buildDataFromFile(file);
        return OUTPUT_PATH;
    }

    private static void buildRelationHeader(String relationName){
        FileUtilsInterface.writeLineToFile(WekaAnnotations.RELATION.value() + " " +  relationName, OUTPUT_PATH.toFile());
        FileUtilsInterface.writeLineToFile("\n", OUTPUT_PATH.toFile());
    }

    private static void buildAttributes(){

        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("Text", WekaAttributesTypes.STRING.value());
        attributes.put(RELATION_NAME,
                "{" + Domains.AVIATION_ACCIDENT.value() + ","
                        + Domains.EARTHQUAKE.value() + ","
                        + Domains.TERRORIST_ATTACK + ","
                        + Domains.TRAIN_ACCIDENT.value() + "}");

        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            FileUtilsInterface.writeLineToFile(WekaAnnotations.ATTRIBUTE.value() + " " + entry.getKey() + " " + entry.getValue(), OUTPUT_PATH.toFile());
        }
        FileUtilsInterface.writeLineToFile("\n", OUTPUT_PATH.toFile());
    }

    private static Path createOutputFileInDirectory(){
        return FileUtilsInterface.createOutputFileInDirectory(OUTPUT_PATH);
    }

    private static void buildDataFromFile(File file){

        FileUtilsInterface.writeLineToFile(WekaAnnotations.DATA.value(), OUTPUT_PATH.toFile());
        FileUtilsInterface.writeLineToFile("\n", OUTPUT_PATH.toFile());

        BufferedReader in;

        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(file.getAbsolutePath()), ISO_8859_1));
            String strLine;

            while((strLine = in.readLine()) != null) {
                FileUtilsInterface.writeLineToFile(strLine, OUTPUT_PATH.toFile());
            }
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage() != null ? e.getMessage() : "ERROR while reading file. UnsupportedEncoding!");
        } catch (FileNotFoundException e) {
            log.error(e.getMessage() != null ? e.getMessage() : "ERROR while reading file. File not found!");
        }catch (IOException e) {
            log.error(e.getMessage() != null ? e.getMessage() : "ERROR while reading file.");
        }
    }

    public static Instances getInstancesFromFile(String filepath) {

        try {
            ArffLoader loader = new ArffLoader();
            loader.setFile(new File(filepath));
            return loader.getDataSet();
        }catch (IOException e){
            log.error(e.getMessage() != null ? e.getMessage() : "ERROR while getting Instances from File");
        }
        return null;
    }
}
