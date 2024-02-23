package com.mosgor.gyroscopesensor;

public class Circle {

	private final int COEFF = 5;
	public int x = 0;
	public int y = 0;
	private int speedY = 0;
	private int speedX = 0;

	private int mX;
	private int mY;

	public void setZYangle(double angle) {
		speedX = (int) (COEFF / Math.cos(angle));
		if (angle < 0){
			speedX = -speedX;
		}
	}

	public void setZXangle(double angle) {
		speedY = (int) (COEFF / Math.cos(angle));
		if (angle > 0){
			speedY = -speedY;
		}
	}

	public void setMax(int x, int y) {
		this.x = x / 2;
		this.y = y / 2;
		mX = x;
		mY = y;
	}

	public void move() {
		int nX = x + speedX;
		int nY = y + speedY;
		if (nX > 0 && nY > 0 && nX < mX && nY < mY) {
			x = nX;
			y = nY;
		}
	}
}
