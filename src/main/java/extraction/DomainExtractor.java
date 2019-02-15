package extraction;

import gate.AnnotationSet;

import java.util.Map;

public abstract class DomainExtractor {

    protected String text;
    protected AnnotationSet annotations;

    public DomainExtractor(String text, AnnotationSet annotations) {
        this.text = text;
        this.annotations = annotations;
    }

    public abstract Map<String, String> extract();

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public AnnotationSet getAnnotations() {
        return annotations;
    }

    public void setAnnotations(AnnotationSet annotations) {
        this.annotations = annotations;
    }
}
