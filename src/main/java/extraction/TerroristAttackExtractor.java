package extraction;

import enums.Languages;
import gate.AnnotationSet;

import java.util.*;

import static extraction.CommonsUtilsExtractor.extractCustomAnnotation;

public class TerroristAttackExtractor extends DomainExtractor {

    public TerroristAttackExtractor(Languages language, String text, AnnotationSet annotations) {
        super(language, text, annotations);
    }

    @Override
    public Map<String, Map<String, String>> extract() {
        Map<String, Map<String, String>> result = new HashMap<>();
        result.put("dateOfAttack", extractDateOfAttack());
        result.put("fatalities", extractFatalities());
        result.put("injured" , extractInjured());
        result.put("nameOfVictim", extractNameOfVictim());
        result.put("perpetrator", extractPerpetrator());
        result.put("place", extractPlace());
        result.put("typeOfAttack", extractTypeOfAttack());
        
        return result;
    }

    private Map<String, String> extractDateOfAttack() {
        List<String> majorTypesSpa = Collections.singletonList("DateOfAttack");
        return extractCustomAnnotation(annotations, text,"Mention", majorTypesSpa);
    }

    private Map<String, String> extractFatalities() {
        List<String> majorTypesSpa = Collections.singletonList("Fatalities");
        return extractCustomAnnotation(annotations, text,"Mention", majorTypesSpa);
    }

    private Map<String, String> extractInjured() {
        List<String> majorTypesSpa = Collections.singletonList("Injured");
        return extractCustomAnnotation(annotations, text,"Mention", majorTypesSpa);
    }

    private Map<String, String> extractNameOfVictim() {
        List<String> majorTypes = Collections.singletonList("NameOfVictim");
        return extractCustomAnnotation(annotations, text, "Mention", majorTypes);
    }

    private Map<String, String> extractPerpetrator() {
        List<String> majorTypes = Collections.singletonList("Perpetrator");
        return extractCustomAnnotation(annotations, text, "Mention", majorTypes);
    }

    private Map<String, String> extractPlace() {
        List<String> majorTypesSpa = Collections.singletonList("Place");
        return extractCustomAnnotation(annotations, text, "Mention", majorTypesSpa);
    }

    private Map<String, String> extractTypeOfAttack() {
        List<String> majorTypesSpa = Collections.singletonList("TypeOfAttack");
        return extractCustomAnnotation(annotations, text, "Mention", majorTypesSpa);
    }
}
