package edu.casetools.dcase.m2uppaal.data.elements;

public class State {

    private String id;
    private String name;
    private String initialValue;
    private String independent;
    private boolean value;

    public State() {
	id = "";
	name = "";
	initialValue = "";
	this.setValue(false);
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getInitialValue() {
	return initialValue;
    }

    public void setInitialValue(String initialValue) {
	this.initialValue = initialValue;
	if (initialValue.equalsIgnoreCase("false")) {
	    value = false;
	} else {
	    value = true;
	}
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public boolean getValue() {
	return value;
    }

    public void setValue(boolean value) {
	this.value = value;
    }

    public boolean isIndependent() {
	return independent.equalsIgnoreCase("true");
    }

}
