package edu.casetools.dcase.m2uppaal.data.elements;

import java.util.List;


public class Rule {
	private int id;
	private List<RuleElement> antecedents;
	private RuleElement consequent;
	
    public String getRuleString(String operator, String comparison) {
		String result = "";
		for (RuleElement antecedent : antecedents) {
			result = result + operator;
			result = result + getStateString(antecedent, comparison);
		}
		return result;
	}	
    
    private String getStateString(RuleElement state, String equals) {

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

	public List<RuleElement> getAntecedents() {
		return antecedents;
	}

	public void setAntecedents(List<RuleElement> antecedents) {
		this.antecedents = antecedents;
	}

	public RuleElement getConsequent() {
		return consequent;
	}

	public void setConsequent(RuleElement consequent) {
		this.consequent = consequent;
	}
    
    
}
