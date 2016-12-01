package edu.casetools.dcase.m2uppaal.data.elements;

public class BoundedOperator {
		

	
	public enum BOP_TYPE {
		STRONG_IMMEDIATE_PAST,
		WEAK_IMMEDIATE_PAST,
		STRONG_ABSOLUTE_PAST,
		WEAK_ABSOLUTE_PAST}; 
		
	private	int id;
	private	String status;
	private	BOP_TYPE type;
	private	int lowBound;
	private	int uppBound;
	private int stateId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public BOP_TYPE getType() {
		return type;
	}
	public void setType(BOP_TYPE type) {
		this.type = type;
	}
	public int getLowBound() {
		return lowBound;
	}
	public void setLowBound(int lowBound) {
		this.lowBound = lowBound;
	}
	public int getUppBound() {
		return uppBound;
	}
	public void setUppBound(int uppBound) {
		this.uppBound = uppBound;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getStateId() {
		return stateId;
	}
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
	
	
	
	
}
