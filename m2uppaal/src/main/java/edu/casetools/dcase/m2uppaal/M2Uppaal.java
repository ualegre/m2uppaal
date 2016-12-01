package edu.casetools.dcase.m2uppaal;

import java.io.IOException;
import java.util.ArrayList;

import edu.casetools.dcase.m2uppaal.data.MData;
import edu.casetools.dcase.m2uppaal.juppaal.elements.Automaton;
import edu.casetools.dcase.m2uppaal.juppaal.elements.Declaration;
import edu.casetools.dcase.m2uppaal.juppaal.elements.NTA;
import edu.casetools.dcase.m2uppaal.juppaal.elements.SystemDeclaration;
import edu.casetools.dcase.m2uppaal.mnta.DeclarationsWriter;
import edu.casetools.dcase.m2uppaal.mnta.SystemDeclarationWriter;
import edu.casetools.dcase.m2uppaal.mnta.TemplateGenerationManager;



public class M2Uppaal {


    public void translate(String filePath, MData systemData) throws IOException {

		try {
		    Declaration declaration = new Declaration();
		    declaration.add(new DeclarationsWriter(systemData).write());
		    NTA nta = new NTA();
		    nta.setDeclarations(declaration);
		    nta.setTemplates((ArrayList<Automaton>) new TemplateGenerationManager(systemData).generateTemplates());
		    nta.setSystemDeclaration(new SystemDeclaration(new SystemDeclarationWriter(systemData).write()));
		    nta.writeModelToFile(filePath);
	
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
}
