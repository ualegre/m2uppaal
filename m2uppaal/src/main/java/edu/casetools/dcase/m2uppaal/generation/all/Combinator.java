package edu.casetools.dcase.m2uppaal.generation.all;

import java.util.ArrayList;
import java.util.List;

public abstract class Combinator {
    protected int independentStateNumber;
    private int simulationTime;
    private int depth;
    protected int testCaseNumber;
    protected int iterationCounter;
    private final Boolean[] binaryElements = { false, true };
    List<Boolean> testCase;

    public Combinator() {

    }

    public Combinator(int independentStateNumber, int simulationTime) {
	initialiseAlgorithm(independentStateNumber, simulationTime);
    }

    protected void initialiseAlgorithm(int independentStateNumber, int simulationTime) {
	this.independentStateNumber = independentStateNumber;
	this.simulationTime = simulationTime;
	depth = (independentStateNumber * simulationTime) - 1;
	testCaseNumber = 0;
	iterationCounter = 0;
	initialiseTestCases();
    }

    private void initialiseTestCases() {
	testCase = new ArrayList<>();
	for (int i = 0; i <= depth; i++) {
	    testCase.add(false);
	}
    }

    public void generateCombinations() {
	initialiseAlgorithm(independentStateNumber, simulationTime);
	for (int i = 0; i < binaryElements.length; i++) {
	    if (depth == 0) {
		for (int j = 0; j < binaryElements.length; j++) {
		    testCase.set(0, binaryElements[i]);
		    handleResult(testCase);
		    testCaseNumber++;
		}
		return;
	    } else {
		testCase.set(depth, binaryElements[i]);
		combinate(depth - 1);
	    }
	}

    }

    public void combinate(int depth) {
	for (int i = 0; i < binaryElements.length; i++) {
	    if (depth == 0) {
		for (int j = 0; j < binaryElements.length; j++) {
		    testCase.set(0, binaryElements[i]);
		    handleResult(testCase);
		    testCaseNumber++;
		}
		return;
	    } else {
		testCase.set(depth, binaryElements[i]);
		combinate(depth - 1);
	    }
	}

    }

    public void printResult(List<Integer> values) {

	System.out.println("\n");
	System.out.println("------------------------------");
	System.out.println("Test Case " + testCaseNumber);
	System.out.println("------------------------------");
	for (int j = 0; j < values.size(); j++) {
	    if (j % independentStateNumber == 0) {
		System.out.println();
		System.out.print("Iteration " + iterationCounter + ": ");
		iterationCounter++;
	    }
	    System.out.print(values.get(j));

	}
	System.out.println("\n------------------------------");
    }

    public void printCombinationNumber() {
	System.out.println("\nTotal ammount of combinations: 2^(" + (independentStateNumber * simulationTime) + ") = "
		+ testCaseNumber);
    }

    protected abstract void handleResult(List<Boolean> values);

}
