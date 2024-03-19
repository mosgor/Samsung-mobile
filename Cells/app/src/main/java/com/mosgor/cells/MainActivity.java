package com.mosgor.cells;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

	Button[][] cells;

	Button retry;

	TextView mines;

	final int MINESCONST = 35;

	int minesCurrent = 0;

	boolean firstClick = true;

	final int HEIGHT = 20;

	final int WIDTH = 9;

	final int EMPTYCELLS = HEIGHT * WIDTH - MINESCONST;

	int currentCells = EMPTYCELLS;

	String[][] values = new String[HEIGHT][WIDTH];

	boolean[][] opened = new boolean[HEIGHT][WIDTH];

	private void showArea(int heightPos, int widthPos) {
		int topLock = heightPos - 1, bottomLock = heightPos + 1, rightLock = widthPos + 1, leftLock = widthPos - 1;
		if (widthPos != 0 && !opened[heightPos][leftLock]) {
			cells[heightPos][leftLock].performClick();
		}
		if (widthPos != WIDTH - 1 && !opened[heightPos][rightLock]) {
			cells[heightPos][rightLock].performClick();
		}
		if (heightPos != 0 && !opened[topLock][widthPos]) {
			cells[topLock][widthPos].performClick();
		}
		if (heightPos != HEIGHT - 1 && !opened[bottomLock][widthPos]) {
			cells[bottomLock][widthPos].performClick();
		}
		if (heightPos != HEIGHT - 1 && widthPos != 0 && !opened[bottomLock][leftLock]) {
			cells[bottomLock][leftLock].performClick();
		}
		if (heightPos != HEIGHT - 1 && widthPos != WIDTH - 1 && !opened[bottomLock][rightLock]) {
			cells[bottomLock][rightLock].performClick();
		}
		if (heightPos != 0 && widthPos != 0 && !opened[topLock][leftLock]) {
			cells[topLock][leftLock].performClick();
		}
		if (heightPos != 0 && widthPos != WIDTH - 1 && !opened[topLock][rightLock]) {
			cells[topLock][rightLock].performClick();
		}
	}

	private void win() {
		Toast.makeText(getApplicationContext(), "YOU WIN!!!", Toast.LENGTH_SHORT).show();
		for (int k = 0; k < HEIGHT; k++) {
			for (int l = 0; l < WIDTH; l++) {
				cells[k][l].setEnabled(false);
			}
		}
		retry.setVisibility(View.VISIBLE);
	}

	@SuppressLint("DefaultLocale")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mines = findViewById(R.id.mines);
		mines.setText(String.format("%d", MINESCONST));
		retry = findViewById(R.id.retry);
		generate();
	}

	@SuppressLint("SetTextI18n")
	public void generate() {
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				opened[i][j] = false;
			}
		}
		GridLayout layout = findViewById(R.id.grid);
		layout.removeAllViews();
		layout.setColumnCount(WIDTH);
		cells = new Button[HEIGHT][WIDTH];
		LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				cells[i][j] = (Button) inflater.inflate(R.layout.cell, layout, false);
			}
		}
		retry.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("DefaultLocale")
			@Override
			public void onClick(View v) {
				minesCurrent = 0;
				firstClick = true;
				values = new String[HEIGHT][WIDTH];
				mines.setText(String.format("%d", MINESCONST));
				retry.setVisibility(View.GONE);
				currentCells = EMPTYCELLS;
				generate();
			}
		});
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				cells[i][j].setTag(WIDTH * i + j);
				cells[i][j].setId(WIDTH * i + j);

				//ВАЖНО!!!
				//Log.d("ab", String.valueOf(WIDTH * i + j)+ " " + cells[i][j].getText());

				if ((i + j) % 2 == 0) {
					cells[i][j].setBackgroundColor(ContextCompat.getColor(this, R.color.light_green));
				} else
					cells[i][j].setBackgroundColor(ContextCompat.getColor(this, R.color.dark_green));
				cells[i][j].setOnClickListener(new View.OnClickListener() {
					@SuppressLint("DefaultLocale")
					@Override
					public void onClick(View v) {
						int widthPos = v.getId() % WIDTH;
						int heightPos = v.getId() / WIDTH;
						if (!opened[heightPos][widthPos] && !cells[heightPos][widthPos].getText().toString().equals("F")) {
							currentCells--;
							opened[heightPos][widthPos] = true;
						}
						Log.d("num", String.valueOf(currentCells) + " " + String.valueOf(minesCurrent));
						if (firstClick) {
							Random rand = new Random();
							while (minesCurrent != MINESCONST) {
								int w = rand.nextInt(WIDTH);
								int h = rand.nextInt(HEIGHT);
								if (cells[h][w].getText() == "M" ||
										((h >= heightPos - 1 && h <= heightPos + 1) &&
												(w >= widthPos - 1 && w <= widthPos + 1))) continue;
								else {
									cells[h][w].setText("M");
									values[h][w] = "M";
									minesCurrent++;
								}
							}
							for (int k = 0; k < HEIGHT; k++) {
								for (int l = 0; l < WIDTH; l++) {
									int topLock = k - 1, bottomLock = k + 1, rightLock = l + 1, leftLock = l - 1;
									if (l == 0) leftLock = 0;
									else if (l == WIDTH - 1) rightLock = WIDTH - 1;
									if (k == 0) topLock = 0;
									else if (k == HEIGHT - 1) bottomLock = HEIGHT - 1;
									int number = 0;
									for (int m = topLock; m <= bottomLock; m++) {
										for (int n = leftLock; n <= rightLock; n++) {
											if (cells[m][n].getText().toString().equals("M"))
												number++;
										}
									}
									if (!cells[k][l].getText().toString().equals("M")) {
										cells[k][l].setText(String.valueOf(number));
										values[k][l] = String.valueOf(number);
									}
								}
							}
							mines.setText(String.format("%d / %d", minesCurrent, MINESCONST));
							firstClick = false;
						}
						if (cells[heightPos][widthPos].getText().toString().equals("F")) return;
						if ((widthPos + heightPos) % 2 == 0) {
							v.setBackgroundColor(getColor(R.color.light_grey));
						} else {
							v.setBackgroundColor(getColor(R.color.dark_grey));
						}
						if (cells[heightPos][widthPos].getText().toString().equals("0")) {
							showArea(heightPos, widthPos);
						} else if (cells[heightPos][widthPos].getText().toString().equals("1")) {
							cells[heightPos][widthPos].setTextColor(getColor(R.color.blue_letter));
						} else if (cells[heightPos][widthPos].getText().toString().equals("2")) {
							cells[heightPos][widthPos].setTextColor(getColor(R.color.green_letter));
						} else if (cells[heightPos][widthPos].getText().toString().equals("3")) {
							cells[heightPos][widthPos].setTextColor(getColor(R.color.red_letter));
						} else if (cells[heightPos][widthPos].getText().toString().equals("4")) {
							cells[heightPos][widthPos].setTextColor(getColor(R.color.purple_letter));
						} else if (cells[heightPos][widthPos].getText().toString().equals("5")) {
							cells[heightPos][widthPos].setTextColor(getColor(R.color.orange_letter));
						} else if (cells[heightPos][widthPos].getText().toString().equals("6")) {
							cells[heightPos][widthPos].setTextColor(getColor(R.color.cyan_letter));
						} else if (cells[heightPos][widthPos].getText().toString().equals("7")) {
							cells[heightPos][widthPos].setTextColor(getColor(R.color.black));
						} else if (cells[heightPos][widthPos].getText().toString().equals("8")) {
							cells[heightPos][widthPos].setTextColor(getColor(R.color.white));
						} else if (cells[heightPos][widthPos].getText().toString().equals("M")) {
							cells[heightPos][widthPos].setTextColor(getColor(R.color.yellow_letter));
							Toast.makeText(getApplicationContext(), "YOU LOSE!!!", Toast.LENGTH_SHORT).show();
							for (int k = 0; k < HEIGHT; k++) {
								for (int l = 0; l < WIDTH; l++) {
									if (cells[k][l].getText().toString().equals("M")) {
										cells[k][l].setTextColor(getColor(R.color.yellow_letter));
									} else if (cells[k][l].getText().toString().equals("F")) {
										if (!values[k][l].equals("M")) {
											cells[k][l].setText("X");
											cells[k][l].setTextColor(getColor(R.color.red_letter));
										}
									}
									cells[k][l].setEnabled(false);
								}
							}
							retry.setVisibility(View.VISIBLE);
						}
						if (currentCells == 0 && minesCurrent == 0) {
							win();
						}
					}
				});
				cells[i][j].setOnLongClickListener(new View.OnLongClickListener() {
					@SuppressLint("DefaultLocale")
					@Override
					public boolean onLongClick(View v) {
						int widthPos = v.getId() % WIDTH;
						int heightPos = v.getId() / WIDTH;
						if (!firstClick && !opened[heightPos][widthPos]) {
							if (!cells[heightPos][widthPos].getText().toString().equals("F") && minesCurrent > 0) {
								cells[heightPos][widthPos].setText("F");
								cells[heightPos][widthPos].setTextColor(getColor(R.color.red_letter));
								minesCurrent--;
							} else if (cells[heightPos][widthPos].getText().toString().equals("F") && minesCurrent >= 0) {
								cells[heightPos][widthPos].setText(values[heightPos][widthPos]);
								cells[heightPos][widthPos].setTextColor(getColor(R.color.invisible));
								minesCurrent++;
							}
							mines.setText(String.format("%d / %d", minesCurrent, MINESCONST));
							if (currentCells == 0 && minesCurrent == 0) {
								win();
							}
						}
						if (!firstClick && opened[heightPos][widthPos]) {
							int topLock = heightPos - 1, bottomLock = heightPos + 1, rightLock = widthPos + 1, leftLock = widthPos - 1;
							if (widthPos == 0) leftLock = 0;
							else if (widthPos == WIDTH - 1) rightLock = WIDTH - 1;
							if (heightPos == 0) topLock = 0;
							else if (heightPos == HEIGHT - 1) bottomLock = HEIGHT - 1;
							int number = 0;
							for (int m = topLock; m <= bottomLock; m++) {
								for (int n = leftLock; n <= rightLock; n++) {
									if (cells[m][n].getText().toString().equals("F")) number++;
								}
							}
							if (Integer.parseInt(cells[heightPos][widthPos].getText().toString()) == number) {
								for (int m = topLock; m <= bottomLock; m++) {
									for (int n = leftLock; n <= rightLock; n++) {
										if (!opened[m][n]) cells[m][n].performClick();
									}
								}
							}
						}
						return true;
					}
				});
				layout.addView(cells[i][j]);
			}
		}
	}
}