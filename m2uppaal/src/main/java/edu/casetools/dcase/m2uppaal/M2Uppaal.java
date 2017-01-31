package edu.casetools.dcase.m2uppaal;

import java.io.IOException;

import edu.casetools.dcase.m2uppaal.data.MData;
import edu.casetools.dcase.m2uppaal.generation.UPPAALFileGenerator;
import edu.casetools.dcase.m2uppaal.generation.all.AllCombinationsGenerator;
import edu.casetools.dcase.m2uppaal.generation.selected.SelectedDiagramsGenerator;

public class M2Uppaal {

    public enum GENERATION_TYPE {
	ALL_COMBINATIONS, SELECTED_DIAGRAM
    };

    public void translate(GENERATION_TYPE type, MData data) throws IOException {
	UPPAALFileGenerator generator = initialiseGenerator(type, data);
	generator.generateTestCases(data);
    }

    private UPPAALFileGenerator initialiseGenerator(GENERATION_TYPE type, MData data) {
	switch (type) {
	case ALL_COMBINATIONS:
	    return new AllCombinationsGenerator(data);
	case SELECTED_DIAGRAM:
	    return new SelectedDiagramsGenerator(data);
	default:
	    return null;
	}
    }

}
