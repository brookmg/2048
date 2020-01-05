package app.g2048.android.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import app.g2048.android.R;
import app.g2048.android.ui.fragment.BaseFragment;
import app.g2048.android.ui.fragment.HomeFragment;

import static app.g2048.android.util.Constants.TAG_HOME;

public class CoreActivity extends AppCompatActivity {

    private FrameLayout _fragmentContainer;
    private WeakReference<Fragment> currentFragment;
    private List<AppCompatImageView> componentButtons = new ArrayList<>(4);

    private void setCurrentFragment(BaseFragment fragment) {
        currentFragment = new WeakReference<>(fragment);
    }

    public interface ActionOnComponent {
        void perform(AppCompatImageView on);
    }

    public void setupComponentButton(int atIndex , ActionOnComponent actionOnComponent) {
        if (componentButtons.size() > atIndex) {
            actionOnComponent.perform(componentButtons.get(atIndex));
        }
    }

    public void setupComponentButtons(ActionOnComponent actionOnComponent, int... indices) {
        for (int index : indices)
            if (componentButtons.size() > index) {
                actionOnComponent.perform(componentButtons.get(index));
            }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core);

        componentButtons.add(findViewById(R.id.component_1_button));
        componentButtons.add(findViewById(R.id.component_2_button));
        componentButtons.add(findViewById(R.id.component_3_button));
        componentButtons.add(findViewById(R.id.component_4_button));

        changeFragment(TAG_HOME , new Bundle(), null);

//        setupComponentButtons(appCompatImageView -> {
//            appCompatImageView.setImageResource(R.drawable.ic_back);
//            appCompatImageView.setVisibility(View.VISIBLE);
//        }, 0, 1, 2);

    }

    private void fragStarter (String fragTag , BaseFragment baseFragment , Bundle bundle, View sharedView) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer , baseFragment , fragTag);
        if (sharedView != null){
            String transitionName = ViewCompat.getTransitionName(sharedView);
            fragmentTransaction.addSharedElement(sharedView , transitionName != null ? transitionName : "\\[_0_0_]/");
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        setCurrentFragment(baseFragment);
    }

    public void changeFragment (String fragmentTag , Bundle bundle, @Nullable View view) {
        switch (fragmentTag) {
            case TAG_HOME: {
                BaseFragment baseFragment = HomeFragment.newInstance();
                fragStarter(fragmentTag , baseFragment , bundle, view);
                break;
            }
        }
    }


}