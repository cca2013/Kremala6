package com.example.androidgridview;
public class Player {

	private int id;
	private String name;
	private int score;

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}