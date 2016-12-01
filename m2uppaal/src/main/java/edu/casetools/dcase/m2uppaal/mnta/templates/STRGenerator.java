package edu.casetools.dcase.m2uppaal.mnta.templates;

import java.util.ArrayList;
import java.util.List;

import edu.casetools.dcase.m2uppaal.data.MData;
import edu.casetools.dcase.m2uppaal.juppaal.elements.Automaton;
import edu.casetools.dcase.m2uppaal.juppaal.elements.Declaration;
import edu.casetools.dcase.m2uppaal.juppaal.elements.Location;
import edu.casetools.dcase.m2uppaal.juppaal.elements.Nail;
import edu.casetools.dcase.m2uppaal.juppaal.elements.Transition;
import edu.casetools.dcase.m2uppaal.juppaal.labels.Guard;
import edu.casetools.dcase.m2uppaal.juppaal.labels.Synchronization;
import edu.casetools.dcase.m2uppaal.juppaal.labels.Synchronization.SyncType;
import edu.casetools.dcase.m2uppaal.juppaal.labels.Update;
import edu.casetools.dcase.m2uppaal.mnta.locations.SAPLocations;
import edu.casetools.dcase.m2uppaal.mnta.locations.STRLocations;

public class STRGenerator extends AbstractTemplateGenerator {

    private static final String STR_CHANNEL = "c_str[";


    public STRGenerator(MData systemData) {
    	super(systemData);

    }

    @Override
    public List<Automaton> generate() {
	List<Automaton> list = new ArrayList<>();
	String rule;
	String invertedRule;
	String consequent;

	for (int i = 0; i < systemData.getStrs().size(); i++) {
	    rule = systemData.getStrs().get(i).getRuleString("&&", "==");
	    invertedRule = systemData.getStrs().get(i).getRuleString("||", "!=");
	    consequent = systemData.getStrs().get(i).getConsequentString();
	    list.add(generateSTR(i, rule, invertedRule, consequent));
	}

	return list;
    }


    private Automaton generateSTR(int id, String rule, String invertedRule, String consequent) {
	Automaton m = new Automaton("STR_" + id);
	Location waiting;
	Location triggered;
	Location notTriggered;

	waiting = generateLocation(m, STRLocations.WAITING, STRLocations.WAITING_X, STRLocations.WAITING_Y,
		STRLocations.WAITING_NAME_X, STRLocations.WAITING_NAME_Y, STRLocations.WAITING_EXP_X,
		STRLocations.WAITING_EXP_Y);

	triggered = generateLocation(m, STRLocations.TRIGGERED, STRLocations.TRIGGERED_X, STRLocations.TRIGGERED_Y,
		STRLocations.TRIGGERED_NAME_X, STRLocations.TRIGGERED_NAME_Y, STRLocations.TRIGGERED_EXP_X,
		STRLocations.WAITING_EXP_Y);

	notTriggered = generateLocation(m, STRLocations.NOT_TRIGGERED, STRLocations.NOT_TRIGGERED_X,
		STRLocations.NOT_TRIGGERED_Y, STRLocations.NOT_TRIGGERED_NAME_X, STRLocations.NOT_TRIGGERED_NAME_Y,
		STRLocations.NOT_TRIGGERED_EXP_X, STRLocations.NOT_TRIGGERED_EXP_Y);

	m.setInit(waiting);

	generateTransitions(id, rule, invertedRule, consequent, m, waiting, triggered, notTriggered);

	return m;
    }

    private void generateTransitions(int id, String rule, String invertedRule, String consequent, Automaton m,
	    Location waiting, Location triggered, Location notTriggered) {
	Transition auxiliarTransition;

	auxiliarTransition = new Transition(m, triggered, waiting);
	auxiliarTransition.setSync(new Synchronization(STR_CHANNEL + id + "+1]", SyncType.INITIATOR, -280, -323));
	auxiliarTransition.addNail(new Nail(-331, -323));

	auxiliarTransition = new Transition(m, notTriggered, waiting);
	auxiliarTransition.setSync(new Synchronization(STR_CHANNEL + id + "+1]", SyncType.INITIATOR, -425, -323));
	auxiliarTransition.addNail(new Nail(-331, -323));

	auxiliarTransition = new Transition(m, waiting, notTriggered);
	auxiliarTransition.setGuard(new Guard(invertedRule, -493, -408));
	auxiliarTransition.setSync(new Synchronization(STR_CHANNEL + id + "]", SyncType.RECEIVER, -467, -425));

	auxiliarTransition = new Transition(m, waiting, triggered);
	auxiliarTransition.setGuard(new Guard(rule, -246, -425));
	auxiliarTransition.setSync(new Synchronization(STR_CHANNEL + id + "]", SyncType.RECEIVER, -246, -408));
	auxiliarTransition.setUpdate(new Update(consequent, -246, -408));
    }

}
