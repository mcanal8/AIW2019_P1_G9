package extraction;

import gate.Annotation;
import gate.AnnotationSet;

import java.util.List;
import java.util.Map;

public class CommonsUtilsExtractor {

    public CommonsUtilsExtractor() {
    }

    static Map<String, String> extractCustomAnnotation(AnnotationSet annotations, String text, String annotationName, List<String> majorTypes, Map<String, String> currentResult) {

        AnnotationSet lookUpAnnotations = annotations.get(annotationName);
        Long start;
        Long end;

        for (Annotation lookUpAnnotation : lookUpAnnotations) {
            for (String majorType : majorTypes) {
                if(lookUpAnnotation.getFeatures().containsValue(majorType)) {

                    start = lookUpAnnotation.getStartNode().getOffset();
                    end = lookUpAnnotation.getEndNode().getOffset();
                    String formedString = text.substring(start.intValue(), end.intValue());
                    currentResult.put(majorType, formedString);
                }
            }
        }

        return currentResult;
    }
}
