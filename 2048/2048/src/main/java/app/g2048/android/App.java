package app.g2048.android;

import android.app.Application;
import android.content.res.Configuration;

import app.g2048.android.util.Util;

public class App extends Application {

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_YES:
                Util.setCurrentTheme(this, 1);
                break;
            case Configuration.UI_MODE_NIGHT_NO:
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                Util.setCurrentTheme(this , 0);
                break;
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();


        // system wide dark mode?
        switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_YES:
                Util.setCurrentTheme(this, 1);
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                Util.setCurrentTheme(this, 0);
                break;
        }
    }
}
