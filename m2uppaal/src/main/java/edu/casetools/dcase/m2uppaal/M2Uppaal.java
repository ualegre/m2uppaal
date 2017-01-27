package edu.casetools.dcase.m2uppaal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.casetools.dcase.m2uppaal.combinator.Combinator;
import edu.casetools.dcase.m2uppaal.data.MData;
import edu.casetools.dcase.m2uppaal.data.elements.Event;
import edu.casetools.dcase.m2uppaal.data.elements.State;
import edu.casetools.dcase.m2uppaal.juppaal.elements.Automaton;
import edu.casetools.dcase.m2uppaal.juppaal.elements.Declaration;
import edu.casetools.dcase.m2uppaal.juppaal.elements.NTA;
import edu.casetools.dcase.m2uppaal.juppaal.elements.SystemDeclaration;
import edu.casetools.dcase.m2uppaal.mnta.DeclarationsWriter;
import edu.casetools.dcase.m2uppaal.mnta.SystemDeclarationWriter;
import edu.casetools.dcase.m2uppaal.mnta.TemplateGenerationManager;

public class M2Uppaal extends Combinator {

    String filePath;
    MData systemData;
    List<State> independentStates;

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

    public void generateAllTestCases(String filePath, MData systemData) throws IOException {
	independentStates = systemData.getIndependentStates();
	this.initialiseAlgorithm(independentStates.size(), systemData.getMaxIteration());
	this.generateCombinations();
    }

    @Override
    protected void handleResult(List<Boolean> values) {
	List<Event> events = new ArrayList<>();
	int columnCounter = 0;
	boolean value;

	printTestCaseHeader();
	for (int j = 0; j < values.size(); j++) {
	    value = values.get(j);
	    countIteration(j);
	    System.out.print(value);
	    events = addEvent(events, columnCounter, value);
	}
	printTestCaseFooter();

    }

    private void printTestCaseFooter() {
	System.out.println("\n------------------------------");
    }

    private List<Event> addEvent(List<Event> events, int columnCounter, boolean value) {
	if (hasChanged(columnCounter, value)) {
	    independentStates.get(columnCounter).setValue(value);
	    Event e = new Event(Integer.toString(events.size()), getStateId(columnCounter),
		    Integer.toString(iterationCounter), Boolean.toString(value));
	    events.add(e);

	}
	return events;
    }

    private boolean hasChanged(int columnCounter, boolean value) {
	return value == independentStates.get(columnCounter).getValue();
    }

    private void printTestCaseHeader() {
	System.out.println("\n");
	System.out.println("------------------------------");
	System.out.println("Test Case " + testCaseNumber);
	System.out.println("------------------------------");
    }

    private String getStateId(int stateIdCounter) {
	return independentStates.get(stateIdCounter).getId();
    }

    private void countIteration(int j) {
	if (j % independentStateNumber == 0) {
	    System.out.println();
	    System.out.print("Iteration " + iterationCounter + ": ");
	    iterationCounter++;
	}
    }

}
