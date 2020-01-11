package app.g2048.android.ui.widget;

public interface MainViewHooks {

    void onScoreChanged(long score);
    void onHighScoreChanged(long highScore);
    void gameLost();
    void gameWon();

    void endlessModeEntered();
    void onNewGame();
    void onGameReverted();

}
