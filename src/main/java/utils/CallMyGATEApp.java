package utils;

import gate.Corpus;
import gate.creole.ConditionalSerialAnalyserController;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import gate.creole.SerialAnalyserController;
import gate.persist.PersistenceException;
import gate.util.persistence.PersistenceManager;

import java.io.File;
import java.io.IOException;


/**
 * Loading in JAVA a GATE gapp and executing it over a document
 * @author UPF
 */
public class CallMyGATEApp {
    
    public ConditionalSerialAnalyserController application;
    public SerialAnalyserController serialApplication;

    public CallMyGATEApp() 
              {
                  super();
              }

    public void loadMyGapp(String pathToGapp) throws IOException, ResourceInstantiationException, PersistenceException {
        this.application = (ConditionalSerialAnalyserController) PersistenceManager.loadObjectFromFile(new File(pathToGapp));
    }
    
        public void loadMyGappSerialAnalyser(String pathToGapp) throws IOException, ResourceInstantiationException, PersistenceException {
        this.serialApplication = (SerialAnalyserController) PersistenceManager.loadObjectFromFile(new File(pathToGapp));
    }

    public void setCorpus(Corpus c) {
        this.application.setCorpus(c);
    }
    
    public void setCorpusSerial(Corpus c) {
        this.serialApplication.setCorpus(c);
    }

    public void executeMyGapp() throws ExecutionException {
        this.application.execute();
    }
    
    public void executeSerialMyGapp() throws ExecutionException {
        this.serialApplication.execute();
    }
}