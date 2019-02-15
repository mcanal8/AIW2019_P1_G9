package extraction;

import gate.AnnotationSet;

import java.util.Map;

public class EarthquakeExtractor extends DomainExtractor {

    public EarthquakeExtractor(String text, AnnotationSet annotations) {
        super(text, annotations);
    }

    @Override
    public Map<String, String> extract() {
        return null;
    }
}
