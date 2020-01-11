package app.g2048.android.ui.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

import app.g2048.android.R;
import app.g2048.android.ui.widget.G2048View;
import app.g2048.android.ui.widget.MainViewHooks;
import app.g2048.android.util.GameSaver;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment {

    private G2048View gameView;

    private boolean isGameLost = false;
    private boolean isGameWon = false;

    private AppCompatTextView scoreField , highScoreField;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    MainViewHooks g2048Hook = new MainViewHooks() {
        @Override
        public void onScoreChanged(long score) {
            Log.e(HomeFragment.class.getName() , "SCORE " + score);
            scoreField.setText(String.format(Locale.ENGLISH, "%d", score));
        }

        @Override
        public void onHighScoreChanged(long highScore) {
            Log.e(HomeFragment.class.getName() , "HIGH-SCORE " + highScore);
            highScoreField.setText(String.format(Locale.ENGLISH, "%d", highScore));
        }

        @Override
        public void gameLost() {
            isGameLost = true;
            Log.e(HomeFragment.class.getName() , "GAME IS LOST !!!");
        }

        @Override
        public void gameWon() {
            isGameWon = true;
            Log.e(HomeFragment.class.getName() , "GAME IS WON !!!");
        }

        @Override
        public void endlessModeEntered() {
            Log.e(HomeFragment.class.getName() , "ENDLESS MODE ENTERED.");
        }

        @Override
        public void onNewGame() {
            Log.e(HomeFragment.class.getName() , "NEW GAME???");
        }

        @Override
        public void onGameReverted() {
            isGameLost = false;

                // until we find a better way to find out if they are in endless mode or not.
                // We shouldn't keep a third variable that holds the endless state of the game
            isGameWon = gameView.game.gameWon();
        }
    };

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("hasState", true);
        GameSaver.saveGame(getActivity(), gameView);
    }

    @Override
    public void onPause() {
        super.onPause();
        GameSaver.saveGame(getActivity() , gameView);
    }

    @Override
    public void onResume() {
        super.onResume();
        GameSaver.loadGame(getActivity() , gameView);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_home, container, false);

        gameView = mainView.findViewById(R.id.g2048View);
        scoreField = mainView.findViewById(R.id.score_field);
        highScoreField = mainView.findViewById(R.id.high_score_field);

        gameView.setMainViewHooks(g2048Hook);

        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean("hasState")) {
                GameSaver.loadGame(getActivity(), gameView);
            }
        }

        mainView.findViewById(R.id.revert_button).setOnClickListener(this::handleRevertButtonClick);
        mainView.findViewById(R.id.restart_button).setOnClickListener(this::handleResetButtonClick);
        mainView.findViewById(R.id.save_button).setOnClickListener(this::handleSaveButtonClick);

        return mainView;
    }

    private void handleResetButtonClick(View view) {
        if (!isGameLost && !isGameWon) {
            if (getActivity() != null)
                new AlertDialog.Builder(getActivity())
                        .setPositiveButton(R.string.reset, (dialog, which) -> gameView.game.newGame())
                        .setNegativeButton(R.string.continue_game, null)
                        .setTitle(R.string.reset_dialog_title)
                        .setMessage(R.string.reset_dialog_message)
                        .show();
        } else gameView.game.newGame();
    }

    private void handleRevertButtonClick(View view) {
        gameView.game.revertUndoState();
        isGameLost = false;
    }

    private void handleSaveButtonClick(View view) {
        GameSaver.saveGame(getActivity() , gameView);
        Snackbar.make(gameView , "Game State Saved !!!" , Snackbar.LENGTH_SHORT).show();
    }

}
