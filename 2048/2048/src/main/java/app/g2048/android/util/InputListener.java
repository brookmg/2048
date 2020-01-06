package app.g2048.android.util;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;

import app.g2048.android.ui.widget.G2048View;

public class InputListener implements View.OnTouchListener {

    private static final int SWIPE_MIN_DISTANCE = 0;
    private static final int SWIPE_THRESHOLD_VELOCITY = 25;
    private static final int MOVE_THRESHOLD = 250;
    private static final int RESET_STARTING = 10;
    private final G2048View mView;
    private float x;
    private float y;
    private float lastDx;
    private float lastDy;
    private float previousX;
    private float previousY;
    private float startingX;
    private float startingY;
    private int previousDirection = 1;
    private int veryLastDirection = 1;
    // Whether or not we have made a move, i.e. the blocks shifted or tried to shift.
    private boolean hasMoved = false;
    // Whether or not we began the press on an icon. This is to disable swipes if the user began
    // the press on an icon.
    private boolean beganOnIcon = false;

    public InputListener(G2048View view) {
        super();
        this.mView = view;
    }

    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();

                startingX = x;
                startingY = y;
                previousX = x;
                previousY = y;

                lastDx = 0;
                lastDy = 0;

                hasMoved = false;
                beganOnIcon = false;

                return true;

            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();

                if (mView.game.isActive() && !beganOnIcon) {
                    float dx = x - previousX;

                    if (Math.abs(lastDx + dx) < Math.abs(lastDx) + Math.abs(dx) && Math.abs(dx) > RESET_STARTING
                            && Math.abs(x - startingX) > SWIPE_MIN_DISTANCE) {
                        startingX = x;
                        startingY = y;
                        lastDx = dx;
                        previousDirection = veryLastDirection;
                    }

                    if (lastDx == 0) { lastDx = dx; }

                    float dy = y - previousY;
                    if (Math.abs(lastDy + dy) < Math.abs(lastDy) + Math.abs(dy) && Math.abs(dy) > RESET_STARTING
                            && Math.abs(y - startingY) > SWIPE_MIN_DISTANCE) {
                        startingX = x;
                        startingY = y;
                        lastDy = dy;
                        previousDirection = veryLastDirection;
                    }

                    if (lastDy == 0) { lastDy = dy; }

                    if (pathMoved() > SWIPE_MIN_DISTANCE * SWIPE_MIN_DISTANCE && !hasMoved) {
                        boolean moved = false;

                        //Vertical
                        boolean moreChangeOnYAxis = Math.abs(dy) >= Math.abs(dx);
                        if (((dy >= SWIPE_THRESHOLD_VELOCITY && moreChangeOnYAxis) || y - startingY >= MOVE_THRESHOLD) && previousDirection % 2 != 0) {
                            moved = true;
                            previousDirection = previousDirection * 2;
                            veryLastDirection = 2;
                            mView.game.move(2);
                        } else if (((dy <= -SWIPE_THRESHOLD_VELOCITY && moreChangeOnYAxis) || y - startingY <= -MOVE_THRESHOLD) && previousDirection % 3 != 0) {
                            moved = true;
                            previousDirection = previousDirection * 3;
                            veryLastDirection = 3;
                            mView.game.move(0);
                        }

                        //Horizontal
                        boolean moreChangeOnXAxis = Math.abs(dx) >= Math.abs(dy);
                        if (((dx >= SWIPE_THRESHOLD_VELOCITY && moreChangeOnXAxis)
                                || x - startingX >= MOVE_THRESHOLD) && previousDirection % 5 != 0) {
                            moved = true;
                            previousDirection = previousDirection * 5;
                            veryLastDirection = 5;
                            mView.game.move(1);
                        } else if (((dx <= -SWIPE_THRESHOLD_VELOCITY && moreChangeOnXAxis)
                                || x - startingX <= -MOVE_THRESHOLD) && previousDirection % 7 != 0) {
                            moved = true;
                            previousDirection = previousDirection * 7;
                            veryLastDirection = 7;
                            mView.game.move(3);
                        }

                        if (moved) {
                            hasMoved = true;
                            startingX = x;
                            startingY = y;
                        }

                    }
                }
                previousX = x;
                previousY = y;
                return true;

            case MotionEvent.ACTION_UP:
                x = event.getX();
                y = event.getY();
                previousDirection = 1;
                veryLastDirection = 1;
        }
        return true;
    }

    private float pathMoved() {
        return (x - startingX) * (x - startingX) + (y - startingY) * (y - startingY);
    }

}
