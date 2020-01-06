package app.g2048.android.util;

public class Constants {

    public static final String TAG_HOME = "home_tag";

    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    public static final String SCORE = "score";
    public static final String HIGH_SCORE = "high_score_temp";
    public static final String UNDO_SCORE = "undo_score";
    public static final String CAN_UNDO = "can_undo";
    public static final String UNDO_GRID = "undo";
    public static final String GAME_STATE = "game_state";
    public static final String UNDO_GAME_STATE = "undo_game_state";
    public static final String HIGH_SCORE_PERM = "high_score";
    public static final String FIRST_RUN = "first_run";

    //Odd state = game is not active
    //Even state = game is active
    //Win state = active state + 1
    public static final int GAME_WIN = 1;
    public static final int GAME_LOST = -1;
    public static final int GAME_NORMAL = 0;

    public static final int GAME_ENDLESS = 2;
    public static final int GAME_ENDLESS_WON = 3;

    public static final int BASE_ANIMATION_TIME = 100_000_000;
    public static final float MERGING_ACCELERATION = (float) -0.5;
    public static final float INITIAL_VELOCITY = (1 - MERGING_ACCELERATION) / 4;

}
