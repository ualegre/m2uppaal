package edu.casetools.dcase.m2uppaal.data.elements;

public class Event {

	private int id;
	private int stateId;
	private int time;
	private String stateValue;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStateId() {
		return stateId;
	}
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public String getStateValue() {
		return stateValue;
	}
	public void setStateValue(String status) {
		this.stateValue = status;
	}
	
	
	
	
}
