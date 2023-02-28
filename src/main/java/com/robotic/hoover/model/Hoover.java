package com.robotic.hoover.model;

import java.util.List;

public class Hoover {

	int[] roomSize ;
    int[] coords ;
    List<int[]> patches ;
    List<Character> instructions;
    
    
	public Hoover(int[] roomSize, int[] coords, List<int[]> patches, List<Character> instructions) {
		this.roomSize = roomSize;
		this.coords = coords;
		this.patches = patches;
		this.instructions = instructions;
	}
	
	public int[] getRoomSize() {
		return roomSize;
	}
	public void setRoomSize(int[] roomSize) {
		this.roomSize = roomSize;
	}
	public int[] getCoords() {
		return coords;
	}
	public void setCoords(int[] coords) {
		this.coords = coords;
	}
	public List<int[]> getPatches() {
		return patches;
	}
	public void setPatches(List<int[]> patches) {
		this.patches = patches;
	}
	public List<Character> getInstructions() {
		return instructions;
	}
	public void setInstructions(List<Character> instructions) {
		this.instructions = instructions;
	}	

}
