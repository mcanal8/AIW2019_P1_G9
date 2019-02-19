package extraction;

import enums.Languages;
import gate.AnnotationSet;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static extraction.CommonsUtilsExtractor.extractCustomAnnotation;

public class AviationAccidentExtractor extends DomainExtractor {

    private Map<String, String> result = new HashMap<>();

    public AviationAccidentExtractor(Languages language, String text, AnnotationSet annotations) {
        super(language, text, annotations);
    }

    @Override
    public Map<String, String> extract() {
        result = extractAirline();
        result = extractDateOfAccident();
        result = extractFlightNumber();
        result = extractNumberOfVictims();
        result = extractPlace();
        result = extractTypeOfAccident();
        result = extractTypeOfAircraft();
        result = extractYear();
        return result;
    }

    private Map<String, String> extractAirline() {
        List<String> majorTypesSpa = Collections.singletonList("Airline");
        return extractCustomAnnotation(annotations, text, "Mention", majorTypesSpa, result);
    }

    private Map<String, String> extractDateOfAccident() {
        List<String> majorTypesSpa = Collections.singletonList("DateOfAccident");
        return extractCustomAnnotation(annotations, text, "Mention", majorTypesSpa, result);
    }

    private Map<String, String> extractFlightNumber() {
        List<String> majorTypesSpa = Collections.singletonList("FlightNumber");
        return extractCustomAnnotation(annotations, text, "Mention", majorTypesSpa, result);
    }

    private Map<String, String> extractNumberOfVictims() {
        List<String> majorTypesSpa = Collections.singletonList("NumberOfVictims");
        return extractCustomAnnotation(annotations, text, "Mention", majorTypesSpa, result);
    }

    private Map<String, String> extractPlace() {
        List<String> majorTypesSpa = Collections.singletonList("Place");
        return extractCustomAnnotation(annotations, text, "Mention", majorTypesSpa, result);
    }

    private Map<String, String> extractTypeOfAccident() {
        List<String> majorTypesSpa = Collections.singletonList("TypeOfAccident");
        return extractCustomAnnotation(annotations, text, "Mention", majorTypesSpa, result);
    }

    private Map<String, String> extractTypeOfAircraft() {
        List<String> majorTypesSpa = Collections.singletonList("TypeOfAircraft");
        return extractCustomAnnotation(annotations, text, "Mention", majorTypesSpa, result);
    }

    private Map<String, String> extractYear() {
        List<String> majorTypesSpa = Collections.singletonList("Year");
        return extractCustomAnnotation(annotations, text, "Mention", majorTypesSpa, result);
    }
}