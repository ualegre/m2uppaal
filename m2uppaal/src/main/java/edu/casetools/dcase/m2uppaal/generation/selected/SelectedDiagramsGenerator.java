package edu.casetools.dcase.m2uppaal.generation.selected;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.casetools.dcase.m2uppaal.data.MData;
import edu.casetools.dcase.m2uppaal.data.elements.State;
import edu.casetools.dcase.m2uppaal.generation.UPPAALFileGenerator;
import edu.casetools.dcase.m2uppaal.juppaal.elements.Automaton;
import edu.casetools.dcase.m2uppaal.juppaal.elements.Declaration;
import edu.casetools.dcase.m2uppaal.juppaal.elements.NTA;
import edu.casetools.dcase.m2uppaal.juppaal.elements.SystemDeclaration;
import edu.casetools.dcase.m2uppaal.mnta.DeclarationsWriter;
import edu.casetools.dcase.m2uppaal.mnta.SystemDeclarationWriter;
import edu.casetools.dcase.m2uppaal.mnta.TemplateGenerationManager;

public class SelectedDiagramsGenerator implements UPPAALFileGenerator {

    MData systemData;
    List<State> independentStates;

    public SelectedDiagramsGenerator(MData systemData) {
	this.systemData = systemData;
    }

    private void translate() {
	try {
	    Declaration declaration = new Declaration();
	    declaration.add(new DeclarationsWriter(this.systemData).write());
	    NTA nta = new NTA();
	    nta.setDeclarations(declaration);
	    nta.setTemplates((ArrayList<Automaton>) new TemplateGenerationManager(this.systemData).generateTemplates());
	    nta.setSystemDeclaration(new SystemDeclaration(new SystemDeclarationWriter(this.systemData).write()));
	    nta.writeModelToFile(systemData.getFilePath());

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void generateTestCases(MData systemData) throws IOException {
	this.systemData = systemData;
	translate();

    }

}
