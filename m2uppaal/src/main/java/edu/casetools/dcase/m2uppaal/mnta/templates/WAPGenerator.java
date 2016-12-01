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
import edu.casetools.dcase.m2uppaal.mnta.locations.WAPLocations;

public class WAPGenerator extends AbstractTemplateGenerator {

    private static final String EVENTS_CHANNEL = "c_bop[";

    public WAPGenerator(MData systemData) {
	super(systemData);
    }

    @Override
    public List<Automaton> generate() {
	List<Automaton> list = new ArrayList<>();
	list.add(generateSIPTemplate());
	return list;
    }

    private Automaton generateSIPTemplate() {
	Automaton m = new Automaton("S_WAP");
	Location initial;
	Location evaluate;
	Location next;
	Location wait;

	m.setParameter(
		"const id_bop id, const iter low_bound, const iter upp_bound,const id_s id_state, const bool status");
	m.setDeclaration(new Declaration(
		"bool atLeastOnceTrue:= false;\r\n\r\nvoid update(){\r\n        s_bop[id] := atLeastOnceTrue;\r\n}\r\n"));

	initial = generateLocation(m, WAPLocations.INITIAL, WAPLocations.INITIAL_X, WAPLocations.INITIAL_Y,
		WAPLocations.INITIAL_NAME_X, WAPLocations.INITIAL_NAME_Y, WAPLocations.INITIAL_EXP_X,
		WAPLocations.INITIAL_EXP_Y);

	evaluate = generateLocation(m, WAPLocations.EVALUATE, WAPLocations.EVALUATE_X, WAPLocations.EVALUATE_Y,
		WAPLocations.EVALUATE_NAME_X, WAPLocations.EVALUATE_NAME_Y, WAPLocations.EVALUATE_EXP_X,
		WAPLocations.EVALUATE_EXP_Y);

	next = generateLocation(m, WAPLocations.NEXT, WAPLocations.NEXT_X, WAPLocations.NEXT_Y,
		WAPLocations.NEXT_NAME_X, WAPLocations.NEXT_NAME_Y, WAPLocations.NEXT_EXP_X, WAPLocations.NEXT_EXP_Y);

	wait = generateLocation(m, WAPLocations.WAITING, WAPLocations.WAITING_X, WAPLocations.WAITING_Y,
		WAPLocations.WAITING_NAME_X, WAPLocations.WAITING_NAME_Y, WAPLocations.WAITING_EXP_X,
		WAPLocations.WAITING_EXP_Y);

	m.setInit(initial);

	generateTransitions(m, initial, evaluate, next, wait);

	return m;
    }

    private void generateTransitions(Automaton m, Location initial, Location evaluate, Location next, Location wait) {
	Transition auxiliarTransition;

	auxiliarTransition = new Transition(m, initial, wait);
	auxiliarTransition.setGuard(new Guard("iteration < low_bound", 111, -68));
	auxiliarTransition.setSync(new Synchronization(EVENTS_CHANNEL + "id]", SyncType.RECEIVER, 111, -51));
	auxiliarTransition.addNail(new Nail(77, -26));
	auxiliarTransition.addNail(new Nail(323, -26));

	auxiliarTransition = new Transition(m, evaluate, next);
	auxiliarTransition.setGuard(new Guard("s[id_state] == status", -195, -68));
	auxiliarTransition.setUpdate(new Update("atLeastOnceTrue := true", -195, -51));
	auxiliarTransition.addNail(new Nail(-195, -51));

	auxiliarTransition = new Transition(m, wait, initial);
	auxiliarTransition.setSync(new Synchronization(EVENTS_CHANNEL + "id+1]", SyncType.INITIATOR, 179, -145));
	auxiliarTransition.addNail(new Nail(340, -111));

	auxiliarTransition = new Transition(m, initial, wait);
	auxiliarTransition.setGuard(new Guard("iteration > upp_bound", 111, 17));
	auxiliarTransition.setSync(new Synchronization(EVENTS_CHANNEL + "id]", SyncType.RECEIVER, 145, 0));
	auxiliarTransition.setUpdate(new Update("update()", 153, 34));
	auxiliarTransition.addNail(new Nail(43, 0));

	auxiliarTransition = new Transition(m, evaluate, next);
	auxiliarTransition.setGuard(new Guard("s[id_state] != status", -416, -68));
	auxiliarTransition.addNail(new Nail(-280, -51));

	auxiliarTransition = new Transition(m, initial, evaluate);
	auxiliarTransition.setGuard(new Guard("iteration >= low_bound && iteration <= upp_bound", -288, 17));
	auxiliarTransition.setSync(new Synchronization(EVENTS_CHANNEL + "id]", SyncType.RECEIVER, -152, 0));
	auxiliarTransition.addNail(new Nail(43, 0));

	auxiliarTransition = new Transition(m, next, initial);
	auxiliarTransition.setSync(new Synchronization(EVENTS_CHANNEL + "id+1]", SyncType.INITIATOR, -135, -145));
	auxiliarTransition.addNail(new Nail(-186, -111));

    }

}
