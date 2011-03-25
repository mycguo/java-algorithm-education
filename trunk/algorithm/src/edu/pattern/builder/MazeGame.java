package edu.pattern.builder;

public class MazeGame {
	public Maze createGame(MazeBuilder builder) {
		builder.buildMaze();
		builder.buildRoom(1);
		builder.buildRoom(2);
		
		builder.buildDoor(1, 2);
		
		return builder.getMaze();
	}
}
