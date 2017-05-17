package edu.illinois.cs.cogcomp.verbsense;

import edu.illinois.cs.cogcomp.annotation.Annotator;
import edu.illinois.cs.cogcomp.annotation.AnnotatorException;
import edu.illinois.cs.cogcomp.core.datastructures.ViewNames;
import edu.illinois.cs.cogcomp.core.datastructures.textannotation.TextAnnotation;
import edu.illinois.cs.cogcomp.core.utilities.configuration.ResourceManager;
import edu.illinois.cs.cogcomp.verbsense.experiment.TextPreProcessor;
import edu.illinois.cs.cogcomp.verbsense.utilities.VerbSenseConfigurator;

/**
 * @author Daniel Khashabi
 */
public class VerbSenseAnnotator extends Annotator {

    VerbSenseLabeler labeler;
    TextPreProcessor preProcessor;

    public VerbSenseAnnotator() {
        this(false);
    }

    public VerbSenseAnnotator(boolean lazilyInitialize) {
        this(lazilyInitialize, new VerbSenseConfigurator().getDefaultConfig());
    }

    public VerbSenseAnnotator(boolean lazilyInitialize, ResourceManager rm) {
        super(VerbSenseConstants.viewName, new String[] {ViewNames.POS, ViewNames.LEMMA,
                ViewNames.SHALLOW_PARSE, ViewNames.NER_CONLL}, lazilyInitialize,
                new VerbSenseConfigurator().getConfig(rm));
    }

    @Override
    public void initialize(ResourceManager resourceManager) {
        try {
            labeler = new VerbSenseLabeler();
        } catch (Exception e) {
            e.printStackTrace();
        }
        preProcessor = TextPreProcessor.getInstance();
    }

    @Override
    protected void addView(TextAnnotation textAnnotation) throws AnnotatorException {
        try {
            textAnnotation.addView(viewName, labeler.getPrediction(textAnnotation));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
