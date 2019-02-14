package utils.classification;

import gate.Corpus;
import gate.Document;
import gate.Factory;
import utils.CallMyGATEApp;
import utils.fileUtils.FileUtils;
import utils.fileUtils.FileUtilsInterface;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utils.wekaUtils.WekaUtils.RESULT_ARFF_FILE_NAME;

public class TextClassifier {

    private final static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(FileUtils.class);
    private static final String TEXT_FILES_PATH = "testing_classifier.txt";

    private StringToWordVector filter;
    private Classifier naiveClassifier;
    private Instances trainInstances;
    private Instances testInstances;
    ArrayList attributes;
    ArrayList classValues;

   public TextClassifier() {

   }

   public void initClassifier() {
        filter = new StringToWordVector();
        naiveClassifier = new NaiveBayes();
   }

   public String classify(String txt) throws Exception {

       Instance instance;
       instance=new DenseInstance(2);

       instance.setValue(testInstances.attribute(0),txt);
       instance.setValue(testInstances.attribute(1),trainInstances.attribute(trainInstances.classIndex()).value(0));

       testInstances.add(instance);

       Instances m_Test2 = Filter.useFilter(testInstances, filter);

       log.info(testInstances.instance(0).stringValue(0));

       double index = naiveClassifier.classifyInstance(m_Test2.instance(0));

       log.info("INDEX "+index);

       String className=m_Test2.classAttribute().value((int)index);

       log.info(className);
       return className;
   }

   public void removeInstance() {
       testInstances.delete(0);
   }


    public void createTestInstancesFromText(String text) {
        ArrayList fvClassVal = new ArrayList();
        Enumeration enu = trainInstances.attribute(trainInstances.classIndex()).enumerateValues();
        while(enu.hasMoreElements()) {
            text=(String)enu.nextElement();
            fvClassVal.add(text);
        }
        Attribute classAttribute = new Attribute("topic", fvClassVal);
        ArrayList fvWekaAttributes=new ArrayList();
        Attribute textAttribute = new Attribute("text",(Vector) null);
        fvWekaAttributes.add(textAttribute);
        fvWekaAttributes.add(classAttribute);
        testInstances = new Instances("Rel", fvWekaAttributes, 1);
    }

   public void createTestInstances() {
        ArrayList fvClassVal = new ArrayList();
        String value;
        Enumeration enu = trainInstances.attribute(trainInstances.classIndex()).enumerateValues();
        while(enu.hasMoreElements()) {
            value=(String)enu.nextElement();
            fvClassVal.add(value);
        }
        Attribute classAttribute = new Attribute("topic", fvClassVal);
        ArrayList fvWekaAttributes=new ArrayList();
        Attribute textAttribute = new Attribute("text",(Vector) null);
        fvWekaAttributes.add(textAttribute);
        fvWekaAttributes.add(classAttribute);
        testInstances = new Instances("Rel", fvWekaAttributes, 1);
   }

   public void loadTrainingInstances(String training_file) {

       try {
           trainInstances = new Instances(new BufferedReader(new FileReader(training_file)));
           int lastIndex = trainInstances.numAttributes() - 1;
           trainInstances.setClassIndex(lastIndex);
           filter.setInputFormat(trainInstances);

           trainInstances = Filter.useFilter(trainInstances, filter);
           naiveClassifier.buildClassifier(trainInstances);

           ArrayList fvWekaAttributes = new ArrayList();

           log.info("Training the Text Classifier.....done!");

       } catch (FileNotFoundException ex) {
           Logger.getLogger(TextClassifier.class.getName()).log(Level.SEVERE, null, ex);
       } catch (IOException ex) {
           Logger.getLogger(TextClassifier.class.getName()).log(Level.SEVERE, null, ex);
       } catch (Exception ex) {
           Logger.getLogger(TextClassifier.class.getName()).log(Level.SEVERE, null, ex);
       }
   }


   public static void main(String[] args) {
       try {

           TextClassifier classifier = new TextClassifier();
           classifier.initClassifier();
           classifier.loadTrainingInstances(RESULT_ARFF_FILE_NAME);
           classifier.createTestInstances();
           String txt;
           String topic;

           /**
            *  Iterative testing the classifier
            */

           /*
           Scanner scanner = new Scanner(System.in);
           System.out.print("YOUR TEXT>>> ");
           txt=scanner.nextLine();
           while(!txt.equalsIgnoreCase("quit")) {
               topic=classifier.classify(txt);
               System.out.println("It is about "+topic);
               classifier.removeInstance();
               System.out.print("YOUR TEXT>>> ");
               txt=scanner.nextLine();
            }
          */


           /**
            *  Batch testing of the classifier
            */
           BufferedReader reader=new BufferedReader(new FileReader(FileUtilsInterface.getResourceFolderPath(TEXT_FILES_PATH)));
           while((txt=reader.readLine())!=null) {
               System.out.println("TEXT >>> "+txt);
               topic=classifier.classify(txt);
               System.out.println("It is about "+topic);
               classifier.removeInstance();
           }
       } catch (Exception ex) {
           Logger.getLogger(TextClassifier.class.getName()).log(Level.SEVERE, null, ex);
       }

   }


   public static void analyze(TextClassifier classifier, CallMyGATEApp myanalyser) throws Exception{
       String txt;
       String topic;
       String language;

       Scanner scanner = new Scanner(System.in);
       System.out.print("READY FOR YOUR TEXT> ");
       txt=scanner.nextLine();

       while(!txt.equalsIgnoreCase("quit")) {
           Corpus corpus= Factory.newCorpus("");
           Document document = Factory.newDocument(txt);
           corpus.add(document);
           myanalyser.setCorpus(corpus);
           myanalyser.executeMyGapp();
           language = document.getFeatures().get("lang").toString();
           classifier.createTestInstancesFromText(txt);
           topic=classifier.classify(txt);
           System.out.println("YOUR TEXT IS ABOUT " + topic + " IN " + language);

           System.out.println("CALLING THE EXTRACTION SYSTEM.....");

           classifier.removeInstance();
           System.out.print("READY FOR YOUR TEXT> ");
           txt=scanner.nextLine();
       }

   }

}
