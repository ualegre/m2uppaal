package edu.casetools.dcase.m2uppaal.data.elements;

public class BoundedOperator {

    public enum BOP_TYPE {
	STRONG_IMMEDIATE_PAST, WEAK_IMMEDIATE_PAST, STRONG_ABSOLUTE_PAST, WEAK_ABSOLUTE_PAST
    };

    private String id;
    private String status;
    private BOP_TYPE type;
    private String lowBound;
    private String uppBound;
    private String stateId;

    public BoundedOperator() {
	id = "";
	status = "";
	lowBound = "";
	uppBound = "";
	stateId = "";
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public BOP_TYPE getType() {
	return type;
    }

    public void setType(BOP_TYPE type) {
	this.type = type;
    }

    public String getLowBound() {
	return lowBound;
    }

    public void setLowBound(String lowBound) {
	this.lowBound = lowBound;
    }

    public String getUppBound() {
	return uppBound;
    }

    public void setUppBound(String uppBound) {
	this.uppBound = uppBound;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public String getStateId() {
	return stateId;
    }

    public void setStateId(String stateId) {
	this.stateId = stateId;
    }

}
