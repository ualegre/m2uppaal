package edu.casetools.dcase.m2uppaal.mnta.templates;

import java.util.ArrayList;
import java.util.List;

import edu.casetools.dcase.m2uppaal.data.MData;
import edu.casetools.dcase.m2uppaal.juppaal.elements.Automaton;
import edu.casetools.dcase.m2uppaal.juppaal.elements.Location;
import edu.casetools.dcase.m2uppaal.juppaal.elements.Nail;
import edu.casetools.dcase.m2uppaal.juppaal.elements.Transition;
import edu.casetools.dcase.m2uppaal.juppaal.labels.Guard;
import edu.casetools.dcase.m2uppaal.juppaal.labels.Synchronization;
import edu.casetools.dcase.m2uppaal.juppaal.labels.Synchronization.SyncType;
import edu.casetools.dcase.m2uppaal.juppaal.labels.Update;
import edu.casetools.dcase.m2uppaal.mnta.locations.EventLocations;

public class EventGenerator extends AbstractTemplateGenerator {

    private static final String EVENTS_CHANNEL = "c_e[";

    public EventGenerator(MData systemData) {
    	super(systemData);
    }

    @Override
    public List<Automaton> generate() {
	List<Automaton> list = new ArrayList<>();
	list.add(generateEventTemplate());
	return list;
    }

    private Automaton generateEventTemplate() {
	Automaton m = new Automaton("Event");
	Location waiting;
	Location triggered;
	Location notTriggered;

	m.setParameter("const id_e id, const iter i, const id_s id_state, const bool status");

	waiting = generateLocation(m, EventLocations.WAITING, EventLocations.WAITING_X, EventLocations.WAITING_Y,
		EventLocations.WAITING_NAME_X, EventLocations.WAITING_NAME_Y, EventLocations.WAITING_EXP_X,
		EventLocations.WAITING_EXP_Y);

	triggered = generateLocation(m, EventLocations.TRIGGERED, EventLocations.TRIGGERED_X,
		EventLocations.TRIGGERED_Y, EventLocations.TRIGGERED_NAME_X, EventLocations.TRIGGERED_NAME_Y,
		EventLocations.TRIGGERED_EXP_X, EventLocations.WAITING_EXP_Y);

	notTriggered = generateLocation(m, EventLocations.NOT_TRIGGERED, EventLocations.NOT_TRIGGERED_X,
		EventLocations.NOT_TRIGGERED_Y, EventLocations.NOT_TRIGGERED_NAME_X,
		EventLocations.NOT_TRIGGERED_NAME_Y, EventLocations.NOT_TRIGGERED_EXP_X,
		EventLocations.NOT_TRIGGERED_EXP_Y);

	m.setInit(waiting);

	generateTransitions(m, waiting, triggered, notTriggered);

	return m;
    }

    private void generateTransitions(Automaton m, Location waiting, Location triggered, Location notTriggered) {
	Transition auxiliarTransition;

	auxiliarTransition = new Transition(m, triggered, waiting);
	auxiliarTransition.setSync(new Synchronization(EVENTS_CHANNEL + "id+1]", SyncType.INITIATOR, 76, -170));
	auxiliarTransition.addNail(new Nail(161, -170));

	auxiliarTransition = new Transition(m, notTriggered, waiting);
	auxiliarTransition.setSync(new Synchronization(EVENTS_CHANNEL + "id+1]", SyncType.INITIATOR, 195, -170));
	auxiliarTransition.addNail(new Nail(161, -170));

	auxiliarTransition = new Transition(m, waiting, notTriggered);
	auxiliarTransition.setGuard(new Guard("iteration != i", 221, -255));
	auxiliarTransition.setSync(new Synchronization(EVENTS_CHANNEL + "id]", SyncType.RECEIVER, 221, -272));

	auxiliarTransition = new Transition(m, waiting, triggered);
	auxiliarTransition.setGuard(new Guard("iteration == i", 8, -297));
	auxiliarTransition.setSync(new Synchronization(EVENTS_CHANNEL + "id]", SyncType.RECEIVER, 8, -314));
	auxiliarTransition.setUpdate(new Update("s[id_state] := status", 8, -280));

    }

}
