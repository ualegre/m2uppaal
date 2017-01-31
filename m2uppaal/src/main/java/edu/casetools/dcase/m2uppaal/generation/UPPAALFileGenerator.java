package edu.casetools.dcase.m2uppaal.generation;

import java.io.IOException;

import edu.casetools.dcase.m2uppaal.data.MData;

public interface UPPAALFileGenerator {

    public abstract void generateTestCases(MData systemData) throws IOException;

}
