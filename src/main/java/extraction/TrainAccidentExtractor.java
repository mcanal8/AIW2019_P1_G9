package extraction;

import gate.AnnotationSet;

import java.util.Map;

public class TrainAccidentExtractor extends DomainExtractor {

    public TrainAccidentExtractor(String text, AnnotationSet annotations) {
        super(text, annotations);
    }

    @Override
    public Map<String, String> extract() {
        return null;
    }
}
