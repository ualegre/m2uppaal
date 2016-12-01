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



public class SAPGenerator extends AbstractTemplateGenerator {

    private static final String EVENTS_CHANNEL = "c_bop[";

    public SAPGenerator(MData systemData) {
    	super(systemData);
    }

    @Override
    public List<Automaton> generate() {
		List<Automaton> list = new ArrayList<>();
		list.add(generateSIPTemplate());
		return list;
    }

    private Automaton generateSIPTemplate() {
	Automaton m = new Automaton("S_SAP");
	Location initial;
	Location evaluate;
	Location next;
	Location wait;

	m.setParameter(
		"const id_bop id, const iter low_bound, const iter upp_bound,const id_s id_state, const bool status");
	m.setDeclaration(new Declaration(
		"bool alwaysTrue:= true;\r\n\r\nvoid update(){\r\n        s_bop[id] := alwaysTrue;\r\n}\r\n"));

	initial = generateLocation(m, SAPLocations.INITIAL, SAPLocations.INITIAL_X, SAPLocations.INITIAL_Y,
		SAPLocations.INITIAL_NAME_X, SAPLocations.INITIAL_NAME_Y, SAPLocations.INITIAL_EXP_X,
		SAPLocations.INITIAL_EXP_Y);

	evaluate = generateLocation(m, SAPLocations.EVALUATE, SAPLocations.EVALUATE_X, SAPLocations.EVALUATE_Y,
		SAPLocations.EVALUATE_NAME_X, SAPLocations.EVALUATE_NAME_Y, SAPLocations.EVALUATE_EXP_X,
		SAPLocations.EVALUATE_EXP_Y);

	next = generateLocation(m, SAPLocations.NEXT, SAPLocations.NEXT_X, SAPLocations.NEXT_Y,
		SAPLocations.NEXT_NAME_X, SAPLocations.NEXT_NAME_Y, SAPLocations.NEXT_EXP_X, SAPLocations.NEXT_EXP_Y);

	wait = generateLocation(m, SAPLocations.WAITING, SAPLocations.WAITING_X, SAPLocations.WAITING_Y,
		SAPLocations.WAITING_NAME_X, SAPLocations.WAITING_NAME_Y, SAPLocations.WAITING_EXP_X,
		SAPLocations.WAITING_EXP_Y);

	m.setInit(initial);

	generateTransitions(m, initial, evaluate, next, wait);

	return m;
    }

    private void generateTransitions(Automaton m, Location initial, Location evaluate, Location next, Location wait) {
	Transition auxiliarTransition;

	auxiliarTransition = new Transition(m, initial, wait);
	auxiliarTransition.setGuard(new Guard("iteration < low_bound", 0, -212));
	auxiliarTransition.setSync(new Synchronization(EVENTS_CHANNEL + "id]", SyncType.RECEIVER, 0, -195));
	auxiliarTransition.addNail(new Nail(-170, -170));

	auxiliarTransition = new Transition(m, evaluate, next);
	auxiliarTransition.setGuard(new Guard("s[id_state] == status", -297, -212));
	auxiliarTransition.addNail(new Nail(-306, -195));

	auxiliarTransition = new Transition(m, wait, initial);
	auxiliarTransition.setSync(new Synchronization(EVENTS_CHANNEL + "id+1]", SyncType.INITIATOR, 68, -289));
	auxiliarTransition.addNail(new Nail(229, -255));

	auxiliarTransition = new Transition(m, initial, wait);
	auxiliarTransition.setGuard(new Guard("iteration > upp_bound", 0, -127));
	auxiliarTransition.setSync(new Synchronization(EVENTS_CHANNEL + "id]", SyncType.RECEIVER, 34, -144));
	auxiliarTransition.setUpdate(new Update("update()", 42, -110));
	auxiliarTransition.addNail(new Nail(-68, -144));

	auxiliarTransition = new Transition(m, evaluate, next);
	auxiliarTransition.setGuard(new Guard("s[id_state] != status", -527, -212));
	auxiliarTransition.setUpdate(new Update("alwaysTrue := false", -527, -195));
	auxiliarTransition.addNail(new Nail(-391, -195));

	auxiliarTransition = new Transition(m, initial, evaluate);
	auxiliarTransition.setGuard(new Guard("iteration >= low_bound && iteration <= upp_bound", -399, -127));
	auxiliarTransition.setSync(new Synchronization(EVENTS_CHANNEL + "id]", SyncType.RECEIVER, -263, -144));
	auxiliarTransition.addNail(new Nail(-68, -144));

	auxiliarTransition = new Transition(m, next, initial);
	auxiliarTransition.setSync(new Synchronization(EVENTS_CHANNEL + "id+1]", SyncType.INITIATOR, -246, -289));
	auxiliarTransition.addNail(new Nail(-297, -255));

    }

}
