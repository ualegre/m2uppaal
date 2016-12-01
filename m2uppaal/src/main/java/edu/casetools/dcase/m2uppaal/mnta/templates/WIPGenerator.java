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
import edu.casetools.dcase.m2uppaal.mnta.locations.WIPLocations;

public class WIPGenerator extends AbstractTemplateGenerator {

    private static final String EVENTS_CHANNEL = "c_bop[";

    public WIPGenerator(MData systemData) {
    	super(systemData);
    }

    @Override
    public List<Automaton> generate() {
		List<Automaton> list = new ArrayList<>();
		list.add(generateSIPTemplate());
		return list;
    }

    private Automaton generateSIPTemplate() {
	Automaton m = new Automaton("S_WIP");
	Location transition;
	Location transitionTrue;
	Location trueT;
	Location transitionFalse;
	Location falseT;

	m.setParameter("const id_bop id, const iter bound, const id_s id_state, const bool status");
	m.setDeclaration(new Declaration("int lastTrue:= 0 - bound;"));

	transition = generateLocation(m, WIPLocations.TRANSITION, WIPLocations.TRANSITION_X, WIPLocations.TRANSITION_Y,
		WIPLocations.TRANSITION_NAME_X, WIPLocations.TRANSITION_NAME_Y, WIPLocations.TRANSITION_EXP_X,
		WIPLocations.TRANSITION_EXP_Y);

	transitionTrue = generateLocation(m, WIPLocations.TRANSITION_TRUE, WIPLocations.TRANSITION_TRUE_X,
		WIPLocations.TRANSITION_TRUE_Y, WIPLocations.TRANSITION_TRUE_NAME_X,
		WIPLocations.TRANSITION_TRUE_NAME_Y, WIPLocations.TRANSITION_TRUE_EXP_X,
		WIPLocations.TRANSITION_TRUE_EXP_Y);

	transitionFalse = generateLocation(m, WIPLocations.TRANSITION_FALSE, WIPLocations.TRANSITION_FALSE_X,
		WIPLocations.TRANSITION_FALSE_Y, WIPLocations.TRANSITION_FALSE_NAME_X,
		WIPLocations.TRANSITION_FALSE_NAME_Y, WIPLocations.TRANSITION_FALSE_EXP_X,
		WIPLocations.TRANSITION_FALSE_EXP_Y);

	trueT = generateLocation(m, WIPLocations.TRUE_T, WIPLocations.TRUE_T_X, WIPLocations.TRUE_T_Y,
		WIPLocations.TRUE_T_X, WIPLocations.TRUE_T_NAME_Y, WIPLocations.TRUE_T_EXP_X,
		WIPLocations.TRUE_T_EXP_Y);

	falseT = generateLocation(m, WIPLocations.FALSE_T, WIPLocations.FALSE_T_X, WIPLocations.FALSE_T_Y,
		WIPLocations.FALSE_T_NAME_X, WIPLocations.FALSE_T_NAME_Y, WIPLocations.FALSE_T_EXP_X,
		WIPLocations.FALSE_T_EXP_Y);

	m.setInit(falseT);

	generateTransitions(m, transition, transitionTrue, transitionFalse, trueT, falseT);

	return m;
    }

    private void generateTransitions(Automaton m, Location transition, Location transitionTrue,
	    Location transitionFalse, Location trueT, Location falseT) {
	Transition auxiliarTransition;

	auxiliarTransition = new Transition(m, transition, transitionTrue);
	auxiliarTransition.setGuard(new Guard("(s[id_state] == status) && ((iteration + 1) > bound)", -51, -204));
	auxiliarTransition.setUpdate(new Update("lastTrue := iteration, s_bop[id]:= true", -51, -136));
	auxiliarTransition.addNail(new Nail(76, -153));

	auxiliarTransition = new Transition(m, transition, transitionFalse);
	auxiliarTransition
		.setGuard(new Guard("(((iteration - lastTrue) <= bound ) && (s[id_state] != status))", -467, -195));
	auxiliarTransition.addNail(new Nail(-204, -153));

	auxiliarTransition = new Transition(m, transitionFalse, falseT);
	auxiliarTransition.setSync(new Synchronization(EVENTS_CHANNEL + "id+1]", SyncType.INITIATOR, -297, -68));
	auxiliarTransition.addNail(new Nail(-314, -42));

	auxiliarTransition = new Transition(m, transitionTrue, trueT);
	auxiliarTransition.setSync(new Synchronization(EVENTS_CHANNEL + "id+1]", SyncType.INITIATOR, 221, -42));
	auxiliarTransition.addNail(new Nail(221, -42));

	auxiliarTransition = new Transition(m, transition, transitionTrue);
	auxiliarTransition.setGuard(new Guard("(s[id_state] == status) && ((iteration + 1) <= bound)", -25, -8));
	auxiliarTransition.setUpdate(new Update("lastTrue := iteration", -25, 8));
	auxiliarTransition.addNail(new Nail(-68, -42));

	auxiliarTransition = new Transition(m, transition, transitionFalse);
	auxiliarTransition
		.setGuard(new Guard("(((iteration - lastTrue) > bound) && (s[id_state] != status))", -425, 8));
	auxiliarTransition.setUpdate(new Update("s_bop[id] := false", -178, 34));
	auxiliarTransition.addNail(new Nail(-69, -42));

	auxiliarTransition = new Transition(m, trueT, transition);
	auxiliarTransition.setSync(new Synchronization(EVENTS_CHANNEL + "id]", SyncType.RECEIVER, 229, -280));
	auxiliarTransition.addNail(new Nail(221, -255));
	auxiliarTransition.addNail(new Nail(-68, -255));

	auxiliarTransition = new Transition(m, falseT, transition);
	auxiliarTransition.setSync(new Synchronization(EVENTS_CHANNEL + "id]", SyncType.RECEIVER, -391, -272));
	auxiliarTransition.addNail(new Nail(-314, -255));
	auxiliarTransition.addNail(new Nail(-68, -255));

    }

}
