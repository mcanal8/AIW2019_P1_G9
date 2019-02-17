package extraction;

import gate.Annotation;
import gate.AnnotationSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonsUtilsExtractor {

    public CommonsUtilsExtractor() {
    }

    static Map<String, String> extractCustomAnnotation(AnnotationSet annotations, String text, String annotationName, List<String> majorTypes) {
        Map<String, String> result = new HashMap<>();

        AnnotationSet lookUpAnnotations = annotations.get(annotationName);
        Long start;
        Long end;

        for (Annotation lookUpAnnotation : lookUpAnnotations) {
            for (String majorType : majorTypes) {
                if(lookUpAnnotation.getFeatures().containsValue(majorType)) {

                    start = lookUpAnnotation.getStartNode().getOffset();
                    end = lookUpAnnotation.getEndNode().getOffset();
                    String formedString = text.substring(start.intValue(), end.intValue());

                    int i = 1;
                    String minorTypeAnnotation = "minorType";

                    if(lookUpAnnotation.getFeatures().containsKey(minorTypeAnnotation)) {
                        if(!result.containsKey((String) lookUpAnnotation.getFeatures().get(minorTypeAnnotation))) {
                            result.put((String) lookUpAnnotation.getFeatures().get(minorTypeAnnotation), formedString);
                        }else {
                            if(!result.containsValue(formedString)){
                                result.put( lookUpAnnotation.getFeatures().get(minorTypeAnnotation) + " " + ++i, formedString);
                            }
                        }

                    }else {
                        result.put(majorType, formedString);
                    }

                }
            }
        }

        return result;
    }
}
