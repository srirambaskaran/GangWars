package org.gangwars.agent.models;


import java.util.HashSet;

public class Move {
	public Cell cell;
	public String moveType;
	public int utlityAfterMove;
	public HashSet<Cell> otherCellsUpdated;
	int numCellsUpdated;
	char moveBy;
	public Move(){
		this.otherCellsUpdated = new HashSet<>();
	}
	public void addOtherCellsUpdated(Cell cell){
		this.otherCellsUpdated.add(cell);
		this.numCellsUpdated++;
	}
	@Override
	public String toString() {
		return "["+cell.toFormattedString()+" "+moveType+"("+moveBy+") - ("+utlityAfterMove+")]";
	}
}
