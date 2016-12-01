package edu.casetools.dcase.m2uppaal.data.elements;

public class RuleElement {

    private String id;
    private String status;

    public RuleElement() {
	id = "";
	status = "";
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

}
