package app.g2048.android.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import app.g2048.android.ui.widget.MainView;
import app.g2048.android.util.GameSaver;

public class MainActivity extends AppCompatActivity {

    private MainView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new MainView(this);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        view.hasSaveState = settings.getBoolean("save_state", false);

        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean("hasState")) {
                GameSaver.loadGame(this, view);
            }
        }
        setContentView(view);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("hasState", true);
        GameSaver.saveGame(this, view);
    }

    protected void onPause() {
        super.onPause();
        GameSaver.saveGame(this , view);
    }

    protected void onResume() {
        super.onResume();
        GameSaver.loadGame(this, view);
    }

}
