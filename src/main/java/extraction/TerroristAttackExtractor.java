package extraction;

import enums.Languages;
import gate.Annotation;
import gate.AnnotationSet;
import gate.FeatureMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TerroristAttackExtractor extends DomainExtractor {

    public TerroristAttackExtractor(String text, AnnotationSet annotations) {
        super(text, annotations);
    }

    @Override
    public Map<String, String> extract() {

        Map<String, String> result = new HashMap<>();

        AnnotationSet mentions = annotations.get("Mention");
        AnnotationSet lookUps = annotations.get("Lookup");

        // variable to hold each annotation to be processed
        Annotation mention;
        Annotation lookUp;

        // start and end of annotations in the text
        Long start, end;

        // features of annotation
        FeatureMap fm;

        // iterate on each annotation

        Iterator<Annotation> ite = mentions.iterator();

        while (ite.hasNext()) {

            // next NE
            mention = ite.next();

            // get features
            fm = mention.getFeatures();

            // get start end offset
            start = mention.getStartNode().getOffset();
            end = mention.getEndNode().getOffset();

            // get feature type of NE
            System.out.println(fm.get("type") + "=" + text.substring(start.intValue(), end.intValue()));
        }

        Iterator<Annotation> ite_lookup = lookUps.iterator();

        while (ite_lookup.hasNext()) {

            // next NE
            lookUp = ite_lookup.next();

            // get features
            fm = lookUp.getFeatures();

            // get start end offset
            start = lookUp.getStartNode().getOffset();
            end = lookUp.getEndNode().getOffset();

            // get feature type of NE
            System.out.println(fm.get("type") + "=" + text.substring(start.intValue(), end.intValue()));
        }


        return result;
    }
}
