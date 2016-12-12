package edu.casetools.dcase.m2uppaal.mnta;

import java.io.IOException;
import java.util.List;

import edu.casetools.dcase.m2uppaal.data.MData;
import edu.casetools.dcase.m2uppaal.data.elements.BoundedOperator;
import edu.casetools.dcase.m2uppaal.data.elements.Event;
import edu.casetools.dcase.m2uppaal.data.elements.Rule;

public class SystemDeclarationWriter {

    private StringBuilder writer;
    private MData systemData;

    public SystemDeclarationWriter(MData systemData) {
	this.systemData = systemData;
	writer = new StringBuilder();
    }

    public String write() throws IOException {
	writeEvents();
	writeBOPs();
	initializeSystem();
	return writer.toString();
    }

    private void writeBOPs() throws IOException {
	List<BoundedOperator> auxiliarList = systemData.getBops(BoundedOperator.BOP_TYPE.STRONG_IMMEDIATE_PAST);
	appendImmediateBop(auxiliarList, "sip", "S_SIP");

	auxiliarList = systemData.getBops(BoundedOperator.BOP_TYPE.WEAK_IMMEDIATE_PAST);
	appendImmediateBop(auxiliarList, "wip", "S_WIP");

	auxiliarList = systemData.getBops(BoundedOperator.BOP_TYPE.STRONG_ABSOLUTE_PAST);
	appendAbsoluteBop(auxiliarList, "sap", "S_SAP");

	auxiliarList = systemData.getBops(BoundedOperator.BOP_TYPE.WEAK_ABSOLUTE_PAST);
	appendAbsoluteBop(auxiliarList, "wap", "S_WAP");

	writer.append("\n");

    }

    private void appendImmediateBop(List<BoundedOperator> auxiliarList, String variableName, String variableBody) {
	for (BoundedOperator operator : auxiliarList) {
	    writer.append(variableName + operator.getId() + " = " + variableBody + "(" + operator.getId() + ","
		    + operator.getLowBound() + "," + operator.getStateId() + "," + operator.getStatus() + ");");
	}
    }

    private void appendAbsoluteBop(List<BoundedOperator> auxiliarList, String variableName, String variableBody) {
	for (BoundedOperator operator : auxiliarList) {
	    writer.append(variableName + operator.getId() + " = " + variableBody + "(" + operator.getId() + ","
		    + operator.getLowBound() + "," + operator.getUppBound() + "," + operator.getStateId() + ","
		    + operator.getStatus() + ");\n");
	}
    }

    private void writeEvents() throws IOException {
	for (Event event : systemData.getEvents())
	    writer.append("E" + event.getId() + " = Event(" + event.getId() + "," + event.getTime() + ","
		    + event.getStateId() + "," + event.getStateValue() + ");\n");
    }

    private void initializeSystem() throws IOException {
	writer.append("system M\n");
	writeEventsSystemInitialization();
	writeBoundedOperatorsSystemInitialization();
	writeStatesSystemDeclaration();
	writer.append(";\n");
    }

    private void writeBoundedOperatorsSystemInitialization() throws IOException {
	List<BoundedOperator> auxiliarList = systemData.getBops(BoundedOperator.BOP_TYPE.STRONG_IMMEDIATE_PAST);
	appendBopInitialization(auxiliarList, "sip");
	writer.append("\n");
	auxiliarList = systemData.getBops(BoundedOperator.BOP_TYPE.WEAK_IMMEDIATE_PAST);
	appendBopInitialization(auxiliarList, "wip");
	writer.append("\n");
	auxiliarList = systemData.getBops(BoundedOperator.BOP_TYPE.STRONG_ABSOLUTE_PAST);
	appendBopInitialization(auxiliarList, "sap");
	writer.append("\n");
	auxiliarList = systemData.getBops(BoundedOperator.BOP_TYPE.WEAK_ABSOLUTE_PAST);
	appendBopInitialization(auxiliarList, "wap");
    }

    private void appendBopInitialization(List<BoundedOperator> auxiliarList, String name) throws IOException {
	for (int i = 0; i < auxiliarList.size(); i++) {
	    writer.append("," + name + auxiliarList.get(i).getId());
	    breakLine(i);
	}
    }

    private void writeStatesSystemDeclaration() throws IOException {
	writer.append("\n");
	writeRules(systemData.getStrs(), "STR");
	writeRules(systemData.getNtrs(), "NTR");
    }

    private void writeRules(List<Rule> rules, String name) throws IOException {
	for (int i = 0; i < rules.size(); i++) {
	    writer.append("," + name + "_" + rules.get(i).getId());
	    breakLine(i);
	}
    }

    private void writeEventsSystemInitialization() throws IOException {
	for (int i = 0; i < systemData.getEvents().size(); i++) {
	    writer.append(",E" + i);
	    breakLine(i);
	}
	writer.append("\n");
    }

    private void breakLine(int i) throws IOException {
	if (i % 10 == 0 && i != 0) {
	    writer.append("\n");
	}
    }

}
