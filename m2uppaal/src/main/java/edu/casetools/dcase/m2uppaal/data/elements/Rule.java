package edu.casetools.dcase.m2uppaal.data.elements;

import java.util.List;


public class Rule {
	private int id;
	private List<State> antecedents;
	private State consequent;
	
    public String getRuleString(String operator, String comparison) {
		String result = "";
		for (State antecedent : antecedents) {
			result = result + operator;
			result = result + getStateString(antecedent, comparison);
		}
		return result;
	}	
    
    private String getStateString(State state, String equals) {

    	return "s[" + state.getId() + "] " + equals + " " + state.getStatus();

    }
    
    public String getConsequentString() {
    	return getStateString(consequent,"==");
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<State> getAntecedents() {
		return antecedents;
	}

	public void setAntecedents(List<State> antecedents) {
		this.antecedents = antecedents;
	}

	public State getConsequent() {
		return consequent;
	}

	public void setConsequent(State consequent) {
		this.consequent = consequent;
	}
    
    
}
