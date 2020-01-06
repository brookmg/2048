package app.g2048.android.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import app.g2048.android.data.Tile;
import app.g2048.android.ui.widget.G2048View;
import app.g2048.android.ui.widget.MainView;

import static app.g2048.android.util.Constants.CAN_UNDO;
import static app.g2048.android.util.Constants.GAME_STATE;
import static app.g2048.android.util.Constants.HEIGHT;
import static app.g2048.android.util.Constants.HIGH_SCORE;
import static app.g2048.android.util.Constants.SCORE;
import static app.g2048.android.util.Constants.UNDO_GAME_STATE;
import static app.g2048.android.util.Constants.UNDO_GRID;
import static app.g2048.android.util.Constants.UNDO_SCORE;
import static app.g2048.android.util.Constants.WIDTH;

public class GameSaver {

    public static void saveGame(Context context, G2048View view) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();

        Tile[][] field = view.game.grid.field;
        Tile[][] undoField = view.game.grid.undoField;

        editor.putInt(WIDTH, field.length);
        editor.putInt(HEIGHT, field.length);

        for (int xx = 0; xx < field.length; xx++) {
            for (int yy = 0; yy < field[0].length; yy++) {
                if (field[xx][yy] != null) {
                    editor.putInt(xx + " " + yy, field[xx][yy].getValue());
                } else {
                    editor.putInt(xx + " " + yy, 0);
                }

                if (undoField[xx][yy] != null) {
                    editor.putInt(UNDO_GRID + xx + " " + yy, undoField[xx][yy].getValue());
                } else {
                    editor.putInt(UNDO_GRID + xx + " " + yy, 0);
                }
            }
        }
        editor.putLong(SCORE, view.game.score);
        editor.putLong(HIGH_SCORE, view.game.highScore);
        editor.putLong(UNDO_SCORE, view.game.lastScore);
        editor.putBoolean(CAN_UNDO, view.game.canUndo);
        editor.putInt(GAME_STATE, view.game.gameState);
        editor.putInt(UNDO_GAME_STATE, view.game.lastGameState);

        editor.apply();
    }

    public static void loadGame(Context context, G2048View view) {
        //Stopping all animations
        view.game.aGrid.cancelAnimations();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);

        for (int xx = 0; xx < view.game.grid.field.length; xx++) {
            for (int yy = 0; yy < view.game.grid.field[0].length; yy++) {
                int value = settings.getInt(xx + " " + yy, -1);
                if (value > 0) {
                    view.game.grid.field[xx][yy] = new Tile(xx, yy, value);
                } else if (value == 0) {
                    view.game.grid.field[xx][yy] = null;
                }

                int undoValue = settings.getInt(UNDO_GRID + xx + " " + yy, -1);
                if (undoValue > 0) {
                    view.game.grid.undoField[xx][yy] = new Tile(xx, yy, undoValue);
                } else if (value == 0) {
                    view.game.grid.undoField[xx][yy] = null;
                }
            }
        }

        view.game.score = settings.getLong(SCORE, view.game.score);
        view.game.highScore = settings.getLong(HIGH_SCORE, view.game.highScore);
        view.game.lastScore = settings.getLong(UNDO_SCORE, view.game.lastScore);
        view.game.canUndo = settings.getBoolean(CAN_UNDO, view.game.canUndo);
        view.game.gameState = settings.getInt(GAME_STATE, view.game.gameState);
        view.game.lastGameState = settings.getInt(UNDO_GAME_STATE, view.game.lastGameState);
    }

}
