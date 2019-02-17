package extraction;

import enums.Languages;
import gate.AnnotationSet;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static extraction.CommonsUtilsExtractor.extractCustomAnnotation;

public class AviationAccidentExtractor extends DomainExtractor {

    public AviationAccidentExtractor(Languages language, String text, AnnotationSet annotations) {
        super(language, text, annotations);
    }

    @Override
    public Map<String, Map<String, String>> extract() {
        Map<String, Map<String, String>> result = new HashMap<>();
        result.put("airline", extractAirline());
        result.put("dateOfAccident", extractDateOfAccident());
        result.put("flightNumber", extractFlightNumber());
        result.put("numberOfVictims", extractNumberOfVictims());
        result.put("place", extractPlace());
        result.put("typeOfAccident", extractTypeOfAccident());
        result.put("typeOfAircraft", extractTypeOfAircraft());
        result.put("year", extractYear());

        return result;
    }

    private Map<String, String> extractAirline() {
        List<String> majorTypesSpa = Collections.singletonList("organization");
        return extractCustomAnnotation(annotations, text, "Lookup", majorTypesSpa);
    }

    private Map<String, String> extractDateOfAccident() {
        List<String> majorTypesSpa = Collections.singletonList("date");
        return extractCustomAnnotation(annotations, text, "Lookup", majorTypesSpa);
    }

    private Map<String, String> extractFlightNumber() {
        List<String> majorTypesSpa = Collections.singletonList("Tiempo");
        return extractCustomAnnotation(annotations, text, "Lookup", majorTypesSpa);
    }

    private Map<String, String> extractNumberOfVictims() {
        List<String> majorTypesSpa = Collections.singletonList("Tiempo");
        return extractCustomAnnotation(annotations, text, "Lookup", majorTypesSpa);
    }

    private Map<String, String> extractPlace() {
        List<String> majorTypesSpa = Collections.singletonList("location");
        return extractCustomAnnotation(annotations, text, "Lookup", majorTypesSpa);
    }

    private Map<String, String> extractTypeOfAccident() {
        List<String> majorTypesSpa = Collections.singletonList("Tiempo");
        return extractCustomAnnotation(annotations, text, "Lookup", majorTypesSpa);
    }

    private Map<String, String> extractTypeOfAircraft() {
        List<String> majorTypesSpa = Collections.singletonList("Tiempo");
        return extractCustomAnnotation(annotations, text, "Lookup", majorTypesSpa);
    }

    private Map<String, String> extractYear() {
        List<String> majorTypesSpa = Collections.singletonList("year");
        return extractCustomAnnotation(annotations, text, "Lookup", majorTypesSpa);
    }
}