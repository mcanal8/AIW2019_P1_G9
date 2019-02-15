package utils;

import gate.Corpus;
import gate.Document;
import gate.Factory;
import gate.Gate;
import gate.creole.ConditionalSerialAnalyserController;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import gate.creole.SerialAnalyserController;
import gate.persist.PersistenceException;
import gate.util.GateException;
import gate.util.persistence.PersistenceManager;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Loading in JAVA a GATE gapp and executing it over a document
 * @author UPF
 */
public class CallMyGATEApp {
    
  //  public  CorpusController application;
    public ConditionalSerialAnalyserController applicationEnglish;
    public ConditionalSerialAnalyserController firstApplication;
    public SerialAnalyserController applicationSpanish;

    public CallMyGATEApp() 
              {
                  super();
              }

    public void loadMyGappFirst(String pathToGapp) throws IOException, ResourceInstantiationException, PersistenceException {

        // load the GAPP
        this.firstApplication = (ConditionalSerialAnalyserController) PersistenceManager.loadObjectFromFile(new File(pathToGapp));
    }

    public void loadMyGappEnglish(String pathToGapp) throws IOException, ResourceInstantiationException, PersistenceException {
        
            // load the GAPP 
             this.applicationEnglish = (ConditionalSerialAnalyserController) PersistenceManager.loadObjectFromFile(new File(pathToGapp));
    }

    public void loadMyGappSpanish(String pathToGapp) throws IOException, ResourceInstantiationException, PersistenceException {

        // load the GAPP
        this.applicationSpanish = (SerialAnalyserController) PersistenceManager.loadObjectFromFile(new File(pathToGapp));
    }

    public void setCorpusFirst(Corpus c) {
        this.firstApplication.setCorpus(c);

    }
    
    public void setCorpusEnglish(Corpus c) {
        this.applicationEnglish.setCorpus(c);
        
    }

    public void setCorpusSpanish(Corpus c) {
        this.applicationSpanish.setCorpus(c);

    }

    public void executeMyGappFirst() throws ExecutionException {

        this.firstApplication.execute();
    }
    
    public void executeMyGappEnglish() throws ExecutionException {
        
        this.applicationEnglish.execute();
    }

    public void executeMyGappSpanish() throws ExecutionException {

        this.applicationSpanish.execute();
    }
    
    
    public static void main(String[] args) {

        if(Gate.getGateHome() == null)
            Gate.setGateHome(new File("/home/victor/GATE_Developer_8.0"));
        if(Gate.getPluginsHome() == null)
            Gate.setPluginsHome(new File("/home/victor/IdeaProjects/AIW2019_P1_G9/src/plugins"));

        try {
            // initialize GATE
            Gate.init();

            // one instance of my class
            CallMyGATEApp myanalyser=new CallMyGATEApp();
           
           // load the application
            myanalyser.loadMyGappEnglish("/home/victor/IdeaProjects/AIW2019_P1_G9/src/main/java/gapps/MyNLPApp.gapp");
          
           // create a GATE corpus
            Corpus corpus=Factory.newCorpus("");
            // create a GATE dcoument for an English text
            Document en_document=Factory.newDocument("December 20 American Airlines Flight 965 , a Boeing 757 , crashes into a mountain while approaching Santiago de Cali, Colombia ; of the 164 people on board, only 4 people and a dog survive.");
            // create a GATE dcoument for a Spanisg text
            Document es_document=Factory.newDocument("2009  30 de junio: el vuelo 626 de Yemenia chocó en cercanías a Comoras, en el Océano Índico.");
            // put documents in corpus
            corpus.add(en_document);
            
            corpus.add(es_document);
            // pass corpus to app
            myanalyser.setCorpusFirst(corpus);
          
            
            // show annotations before call for English doc
            System.out.println(">>>> annotations before call ENGLISH <<<<<");
            System.out.println(en_document.getAnnotations());
            
             // show annotations before call for Spanish doc
            System.out.println(">>>> annotations before call SPANISH<<<<<");
            System.out.println(es_document.getAnnotations());
            
            // execute app
            myanalyser.executeMyGappFirst();
        
            // show annotations after call
            System.out.println(">>>> annotations after call ENGLISH<<<<<");
            System.out.println(en_document.getAnnotations());
            
            System.out.println(">>>> annotations after call SPANISH<<<<<");
            System.out.println(es_document.getAnnotations());
            // do stuff with your document...
            
            // release resources used for documents
            Factory.deleteResource(en_document);
            Factory.deleteResource(es_document);

        } catch(GateException ge) {
            ge.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(CallMyGATEApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
            
    
}