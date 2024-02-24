package com.mosgor.gyroscopesensor;

public class Circle {

	private final double COEFF = 0.7;
	public int x = 0;
	public int y = 0;
	private int speedY = 0;
	private int speedX = 0;

	private int mX;
	private int mY;

	public void setZYangle(double angle) {
		double degr = Math.toDegrees(angle);
		if (degr > 90){
			degr = 180 - degr;
		}
		else if (degr < -90){
			degr = - 180 - degr;
		}
		speedX = (int) (COEFF * degr);
//		if (angle < 0){
//			speedX = -speedX;
//		}
//		if (angle == 0){
//			speedX = 0;
//		}
	}

	public void setZXangle(double angle) {
		speedY = - (int) (COEFF * Math.toDegrees(angle));
//		if (angle > 0){
//			speedY = -speedY;
//		}
//		if (angle == 0){
//			speedY = 0;
//		}
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
