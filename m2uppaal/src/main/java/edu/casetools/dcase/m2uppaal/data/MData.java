package edu.casetools.dcase.m2uppaal.data;

import java.util.ArrayList;
import java.util.List;

import edu.casetools.dcase.m2uppaal.data.elements.BoundedOperator;
import edu.casetools.dcase.m2uppaal.data.elements.Event;
import edu.casetools.dcase.m2uppaal.data.elements.Rule;
import edu.casetools.dcase.m2uppaal.data.elements.State;





public class MData {

	private int maxIteration;
    private List<State> states;
    private List<Event> events;
    private List<Rule> strs;
    private List<Rule> ntrs;
    private List<BoundedOperator> bops;

    public MData() {
    	initialiseLists();
    }

    private void initialiseLists() {
		states = new ArrayList<>();
		events = new ArrayList<>();
		strs   = new ArrayList<>();
		ntrs   = new ArrayList<>();
		bops   = new ArrayList<>();

    }

	public int getMaxIteration() {
		return maxIteration;
	}

	public void setMaxIteration(int maxIteration) {
		this.maxIteration = maxIteration;
	}

	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public List<Rule> getStrs() {
		return strs;
	}

	public void setStrs(List<Rule> strs) {
		this.strs = strs;
	}

	public List<Rule> getNtrs() {
		return ntrs;
	}

	public void setNtrs(List<Rule> ntrs) {
		this.ntrs = ntrs;
	}

	public List<BoundedOperator> getBops() {
		return bops;
	}
	
	public List<BoundedOperator> getBops(BoundedOperator.BOP_TYPE type) {
		List<BoundedOperator> list = new ArrayList<>();
		for (int i=0; i < bops.size(); i++){
			if(bops.get(i).getType() == type) 
				list.add(bops.get(i));
		}
		return list;
	}

	public void setBops(List<BoundedOperator> bops) {
		this.bops = bops;
	}
	
	public int getBopNumber(BoundedOperator.BOP_TYPE type){
		int result = 0;
		for (int i=0; i < bops.size(); i++){
			if(bops.get(i).getType() == type)
				result++;
		}
		return result;
	}

    


}
