/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import utils.fileUtils.FileUtils;

/**
 *
 * @author Horacio
 */
public class CreateTrainingDataSimple {

    private final static Logger log = Logger.getLogger(CreateTrainingDataSimple.class);

    public static void main(String[] args) {

        // Logging config
        BasicConfigurator.configure();

        log.info("Text Classification and Extraction App. " +
                "This App Recognizes texts belonging to the following two domain: terrorist attacks and aviation accidents");

        // Generating WEKA .arff file
        FileUtils.extract();

    }
}
