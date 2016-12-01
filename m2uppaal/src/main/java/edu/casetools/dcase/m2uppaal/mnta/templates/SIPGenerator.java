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
import edu.casetools.dcase.m2uppaal.mnta.locations.SIPLocations;

public class SIPGenerator extends AbstractTemplateGenerator {

    private static final String EVENTS_CHANNEL = "c_bop[";

    public SIPGenerator(MData systemData) {
	super(systemData);
    }

    @Override
    public List<Automaton> generate() {
	List<Automaton> list = new ArrayList<>();
	list.add(generateSIPTemplate());
	return list;
    }

    private Automaton generateSIPTemplate() {
	Automaton m = new Automaton("S_SIP");
	Location check;
	Location transition;
	Location transitionTrue;
	Location trueT;
	Location transitionFalse;
	Location falseT;

	m.setParameter("const id_bop id, const iter bound, const id_s id_state, const bool status");
	m.setDeclaration(new Declaration(
		"int counter:=0;\r\n\r\nvoid count(){\r\n     if(counter == INTEGER_OVERFLOW){\r\n        counter := bound;\r\n     }else{\r\n        counter++;\r\n     }\r\n}"));

	check = generateLocation(m, SIPLocations.CHECK, SIPLocations.CHECK_X, SIPLocations.CHECK_Y,
		SIPLocations.CHECK_NAME_X, SIPLocations.CHECK_NAME_Y, SIPLocations.CHECK_EXP_X,
		SIPLocations.CHECK_EXP_Y);

	transition = generateLocation(m, SIPLocations.TRANSITION, SIPLocations.TRANSITION_X, SIPLocations.TRANSITION_Y,
		SIPLocations.TRANSITION_NAME_X, SIPLocations.TRANSITION_NAME_Y, SIPLocations.TRANSITION_EXP_X,
		SIPLocations.TRANSITION_EXP_Y);

	transitionTrue = generateLocation(m, SIPLocations.TRANSITION_TRUE, SIPLocations.TRANSITION_TRUE_X,
		SIPLocations.TRANSITION_TRUE_Y, SIPLocations.TRANSITION_TRUE_NAME_X,
		SIPLocations.TRANSITION_TRUE_NAME_Y, SIPLocations.TRANSITION_TRUE_EXP_X,
		SIPLocations.TRANSITION_TRUE_EXP_Y);

	transitionFalse = generateLocation(m, SIPLocations.TRANSITION_FALSE, SIPLocations.TRANSITION_FALSE_X,
		SIPLocations.TRANSITION_FALSE_Y, SIPLocations.TRANSITION_FALSE_NAME_X,
		SIPLocations.TRANSITION_FALSE_NAME_Y, SIPLocations.TRANSITION_FALSE_EXP_X,
		SIPLocations.TRANSITION_FALSE_EXP_Y);

	trueT = generateLocation(m, SIPLocations.TRUE_T, SIPLocations.TRUE_T_X, SIPLocations.TRUE_T_Y,
		SIPLocations.TRUE_T_X, SIPLocations.TRUE_T_NAME_Y, SIPLocations.TRUE_T_EXP_X,
		SIPLocations.TRUE_T_EXP_Y);

	falseT = generateLocation(m, SIPLocations.FALSE_T, SIPLocations.FALSE_T_X, SIPLocations.FALSE_T_Y,
		SIPLocations.FALSE_T_NAME_X, SIPLocations.FALSE_T_NAME_Y, SIPLocations.FALSE_T_EXP_X,
		SIPLocations.FALSE_T_EXP_Y);

	m.setInit(falseT);

	generateTransitions(m, check, transition, transitionTrue, transitionFalse, trueT, falseT);

	return m;
    }

    private void generateTransitions(Automaton m, Location check, Location transition, Location transitionTrue,
	    Location transitionFalse, Location trueT, Location falseT) {
	Transition auxiliarTransition;

	auxiliarTransition = new Transition(m, transitionFalse, falseT);
	auxiliarTransition.setSync(new Synchronization(EVENTS_CHANNEL + "id+1]", SyncType.INITIATOR, -339, -374));
	auxiliarTransition.addNail(new Nail(-416, -382));

	auxiliarTransition = new Transition(m, transitionTrue, trueT);
	auxiliarTransition.setSync(new Synchronization(EVENTS_CHANNEL + "id+1]", SyncType.INITIATOR, 179, -374));
	auxiliarTransition.addNail(new Nail(289, -382));

	auxiliarTransition = new Transition(m, transition, transitionTrue);
	auxiliarTransition.setGuard(new Guard("s[id_state] == status", -59, -425));
	auxiliarTransition.setUpdate(new Update("count()", -59, -408));
	auxiliarTransition.addNail(new Nail(-76, -382));

	auxiliarTransition = new Transition(m, transition, transitionFalse);
	auxiliarTransition.setGuard(new Guard("s[id_state] != status", -212, -425));
	auxiliarTransition.setUpdate(new Update("counter:=0", -212, -408));
	auxiliarTransition.addNail(new Nail(-76, -382));

	auxiliarTransition = new Transition(m, trueT, check);
	auxiliarTransition.setSync(new Synchronization(EVENTS_CHANNEL + "id]", SyncType.RECEIVER, 111, -629));
	auxiliarTransition.addNail(new Nail(289, -603));

	auxiliarTransition = new Transition(m, check, transition);
	auxiliarTransition.setGuard(new Guard("(counter >= bound) && ((iteration+1) > bound)", -25, -561));
	auxiliarTransition.setUpdate(new Update("s_bop[id]:=true", -25, -544));
	auxiliarTransition.addNail(new Nail(-25, -544));

	auxiliarTransition = new Transition(m, check, transition);
	auxiliarTransition.setGuard(new Guard("(counter < bound) || ((iteration+1) <= bound)", -407, -561));
	auxiliarTransition.setUpdate(new Update("s_bop[id]:= false", -237, -544));
	auxiliarTransition.addNail(new Nail(-127, -544));

	auxiliarTransition = new Transition(m, falseT, check);
	auxiliarTransition.setSync(new Synchronization(EVENTS_CHANNEL + "id]", SyncType.RECEIVER, -305, -629));
	auxiliarTransition.addNail(new Nail(-416, -603));

    }

}
