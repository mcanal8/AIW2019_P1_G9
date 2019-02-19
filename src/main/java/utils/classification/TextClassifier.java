package utils.classification;

import enums.Domains;
import enums.Languages;
import extraction.*;
import gate.Corpus;
import gate.Document;
import gate.Factory;
import utils.CallMyGATEApp;
import utils.fileUtils.FileUtils;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TextClassifier {

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(FileUtils.class);

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

       } catch (Exception ex) {
           Logger.getLogger(TextClassifier.class.getName()).log(Level.SEVERE, null, ex);
       }
   }

   public static void analyze(TextClassifier classifier, CallMyGATEApp myanalyser, CallMyGATEApp ieEnglish, CallMyGATEApp ieSpanish) throws Exception{
       String txt;
       Domains topic;
       Languages language;

       Scanner scanner = new Scanner(System.in);
       log.info("READY FOR YOUR TEXT> ");
       txt=scanner.nextLine();

       while(!txt.equalsIgnoreCase("quit")) {
           Corpus corpus= Factory.newCorpus("");
           Document document = Factory.newDocument(txt);
           corpus.add(document);
           myanalyser.setCorpus(corpus);
           myanalyser.executeMyGapp();
           language = Languages.fromValue(document.getFeatures().get("lang").toString());
           classifier.createTestInstancesFromText(txt);
           topic = Domains.fromValue(classifier.classify(txt));
           assert language != null;
           log.info("YOUR TEXT IS ABOUT " + topic + " IN " + language.value());

           if((language.equals(Languages.SPANISH) && topic == Domains.TERRORIST_ATTACK) || (language.equals(Languages.ENGLISH) && topic == Domains.AVIATION_ACCIDENT)) {
               log.info("CALLING THE EXTRACTION SYSTEM.....");

               DomainExtractor extractor;

               switch (language){
                   case SPANISH:
                       ieSpanish.setCorpusSerial(corpus);
                       ieSpanish.executeSerialMyGapp();
                       break;
                   case ENGLISH:
                       ieEnglish.setCorpus(corpus);
                       ieEnglish.executeMyGapp();
                       break;
                   default:
                       return;
               }

               switch (topic) {
                   case TERRORIST_ATTACK:
                       extractor = new TerroristAttackExtractor(language, txt, document.getAnnotations());
                       break;
                   case AVIATION_ACCIDENT:
                       extractor = new AviationAccidentExtractor(language, txt, document.getAnnotations());
                       break;
                   default:
                       return;
               }

               extractor.extract();

               classifier.removeInstance();
               Factory.deleteResource(document);
               log.info("....");
               log.info("READY FOR YOUR TEXT> ");
               txt=scanner.nextLine();
           } else {
               classifier.removeInstance();
               Factory.deleteResource(document);
               log.error("Sorry, I can't process the text.");
               log.info("READY FOR YOUR TEXT> ");
               txt=scanner.nextLine();
           }
       }

       log.info("GOOD BYE!");
       log.info("****");
   }
}
