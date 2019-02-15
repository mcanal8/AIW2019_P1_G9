package utils.classification;

import gate.*;
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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


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


   public static void analyze(TextClassifier classifier, CallMyGATEApp myanalyser, CallMyGATEApp ieEnglish, CallMyGATEApp ieSpanish) throws Exception{
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
           myanalyser.setCorpusFirst(corpus);
           myanalyser.executeMyGappFirst();
           language = document.getFeatures().get("lang").toString();
           classifier.createTestInstancesFromText(txt);
           topic=classifier.classify(txt);
           System.out.println("YOUR TEXT IS ABOUT " + topic + " IN " + language);

           System.out.println("CALLING THE EXTRACTION SYSTEM.....");

           switch (language){
               case "spanish":
                   ieSpanish.setCorpusSpanish(corpus);
                   ieSpanish.executeMyGappSpanish();
                   extractSystemSpanish(document);
                   break;
               case "english":
                   ieEnglish.setCorpusEnglish(corpus);
                   ieEnglish.executeMyGappEnglish();
                   extractSystemEnglish(document);
                   break;
               default:
                   ieEnglish.setCorpusEnglish(corpus);
                   ieEnglish.executeMyGappEnglish();
                   break;
           }

           System.out.println("Hey");
           classifier.removeInstance();
           System.out.print("READY FOR YOUR TEXT> ");
           txt=scanner.nextLine();
       }

   }

   private static void extractSystemEnglish(Document document) {
       extractInfo(document);

//       AnnotationSet annotations_english = document.getAnnotations();
//
//       for (Annotation annotation : annotations_english) {
//           String type = annotation.getType();
//           FeatureMap features =annotation.getFeatures();
//           System.out.println(type + features.toString());
//       }
   }

    private static void extractSystemSpanish(Document document) {
        extractInfo(document);

//        AnnotationSet annotations_spanish = document.getAnnotations();
//
//        for (Annotation annotation : annotations_spanish) {
//            String type = annotation.getType();
//            FeatureMap features =annotation.getFeatures();
//            System.out.println(type + features.toString());
//        }
    }

    private static void extractInfo(Document doc) {
        // get all annotations in default annotation Set
        AnnotationSet defaultAnnotations = doc.getAnnotations();

        // select annotations of type NE
        AnnotationSet mentions = defaultAnnotations.get("Mention");
        AnnotationSet lookUps = defaultAnnotations.get("Lookup");

        // variable to hold each annotation to be processed
        Annotation mention;
        Annotation lookUp;

        // start and end of annotations in the text
        Long start, end;

        // features of annotation
        FeatureMap fm;

        // document content
        String dc = doc.getContent().toString();

        // iterate on each annotation

        Iterator<Annotation> ite = mentions.iterator();

        while (ite.hasNext()) {

            // next NE
            mention = ite.next();

            // get features
            fm = mention.getFeatures();

            // get start end offset
            start = mention.getStartNode().getOffset();
            end = mention.getEndNode().getOffset();

            // get feature type of NE
            System.out.println(fm.get("type") + "=" + dc.substring(start.intValue(), end.intValue()));
        }

        Iterator<Annotation> ite_lookup = lookUps.iterator();

        while (ite_lookup.hasNext()) {

            // next NE
            lookUp = ite_lookup.next();

            // get features
            fm = lookUp.getFeatures();

            // get start end offset
            start = lookUp.getStartNode().getOffset();
            end = lookUp.getEndNode().getOffset();

            // get feature type of NE
            System.out.println(fm.get("type") + "=" + dc.substring(start.intValue(), end.intValue()));
        }
    }
}
