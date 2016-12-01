package edu.casetools.dcase.m2uppaal.mnta;

import java.io.IOException;

import edu.casetools.dcase.m2uppaal.data.MData;
import edu.casetools.dcase.m2uppaal.data.elements.State;

public class DeclarationsWriter {
    private static final String STRING_FALSE = "false";
    private MData systemData;
    private StringBuilder writer;

    public DeclarationsWriter(MData systemData) {
	this.systemData = systemData;
	this.writer = new StringBuilder();
    }

    public String write() throws IOException {
	writeVariables();
	writeStates();

	return writer.toString();
    }

    private void writeVariables() throws IOException {
	writer.append("\n\n");
	writer.append("int __reach__ = 0;\nint __single__ = 0;\n\n");
	writer.append("const int MAX_ITERATION = " + systemData.getMaxIteration() + ";\n");
	writer.append("const int INTEGER_OVERFLOW = 32767;");
	writer.append("\n");
	writeElementNo();
	writer.append("\n");
	writeIDs();
	writer.append("\n");
	writeChannels();
	writer.append("\n");
	writer.append("iter iteration:=0;\n");
	writer.append("bool reset:= true;\n");
    }

    private void writeChannels() {
	writer.append("broadcast chan c_e   [EVENT_NO+1];\n");
	writer.append("broadcast chan c_str [STR_NO+1];\n");
	writer.append("broadcast chan c_ntr [NTR_NO+1];\n");
	writer.append("broadcast chan c_bop [BOP_NO+1];\n");
    }

    private void writeIDs() {
	writer.append("typedef int[0,MAX_ITERATION] iter;\n");
	if (!systemData.getStates().isEmpty())
	    writer.append("typedef int[0,STATE_NO-1]    id_s;\n");
	if (!systemData.getStrs().isEmpty())
	    writer.append("typedef int[0,STR_NO-1]      id_str;\n");
	if (!systemData.getNtrs().isEmpty())
	    writer.append("typedef int[0,NTR_NO-1]      id_ntr;\n");
	if (!systemData.getEvents().isEmpty())
	    writer.append("typedef int[0,EVENT_NO-1]    id_e;\n");
	if (!systemData.getBops().isEmpty())
	    writer.append("typedef int[0,BOP_NO-1]      id_bop;\n");
    }

    private void writeElementNo() {
	writer.append("const int STATE_NO = " + systemData.getStates().size() + ";\n");
	writer.append("const int EVENT_NO = " + systemData.getEvents().size() + ";\n");
	writer.append("const int STR_NO   = " + systemData.getStrs().size() + ";\n");
	writer.append("const int NTR_NO   = " + systemData.getNtrs().size() + ";\n");
	writer.append("const int BOP_NO   = " + systemData.getBops().size() + ";\n");
    }

    private void writeStates() throws IOException {
	if (!systemData.getStates().isEmpty()) {
	    declareStates();
	    declareInitialStates();
	}
	if (!systemData.getBops().isEmpty())
	    declareBoundedStates();
    }

    private void breakLine(int i) throws IOException {
	if (i % 10 == 0 && i != 0) {
	    writer.append("\n");
	}
    }

    private void declareStates() throws IOException {
	writer.append("bool s[STATE_NO] := {");
	getInitialStateValues();
	writer.append("};\n");
    }

    private void declareInitialStates() throws IOException {
	writer.append("bool s_init[STATE_NO] := {");
	getInitialStateValues();
	writer.append("};\n\n");
    }

    private void getInitialStateValues() throws IOException {
	State auxiliar;
	for (int i = 0; i < systemData.getStates().size(); i++) {
	    if (i != 0) {
		writer.append(",");
	    }
	    auxiliar = systemData.getStates().get(i);
	    writer.append(auxiliar.getInitialValue());
	    breakLine(i);
	}
    }

    private void declareBoundedStates() throws IOException {
	writer.append("bool s_bop[BOP_NO] := {");
	for (int i = 0; i < systemData.getBops().size(); i++) {
	    if (i != 0) {
		writer.append(",");
	    }
	    writer.append(STRING_FALSE);
	    breakLine(i);
	}
	writer.append("};\n");
	writer.append("");
    }

}