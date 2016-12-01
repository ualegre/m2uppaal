package edu.casetools.dcase.m2uppaal.data.elements;

import java.util.ArrayList;
import java.util.List;

public class Rule {
    private String id;
    private List<RuleElement> antecedents;
    private RuleElement consequent;

    public Rule() {
	id = "";
	antecedents = new ArrayList<>();
	consequent = new RuleElement();
    }

    public String getRuleString(String operator, String comparison) {
	String result = "";
	for (int i = 0; i < antecedents.size(); i++) {
	    if (i != 0) {
		result = result + operator;
	    }
	    result = result + getStateString(antecedents.get(i), comparison);
	}
	return result;
    }

    private String getStateString(RuleElement state, String equals) {

	return "s[" + state.getId() + "] " + equals + " " + state.getStatus();

    }

    public String getConsequentString() {
	return getStateString(consequent, ":=");
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
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
