package extraction;

import enums.Languages;
import gate.AnnotationSet;

import java.util.Map;

public abstract class DomainExtractor {

    protected Languages language;
    protected String text;
    protected AnnotationSet annotations;

    DomainExtractor(Languages language, String text, AnnotationSet annotations) {
        this.language = language;
        this.text = text;
        this.annotations = annotations;
    }

    public abstract Map<String, Map<String, String>> extract();

    public Languages getLanguage() {
        return language;
    }

    public void setLanguage(Languages language) {
        this.language = language;
    }

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
