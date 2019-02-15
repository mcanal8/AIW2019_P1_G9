package extraction;

import gate.AnnotationSet;

import java.util.Map;

public class AviationAccidentExtractor extends DomainExtractor {

    public AviationAccidentExtractor(String text, AnnotationSet annotations) {
        super(text, annotations);
    }

    @Override
    public Map<String, String> extract() {
        return null;
    }
}
