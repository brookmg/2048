/*
 * Copyright (C) 2019 Brook Mezgebu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.g2048.android.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import app.g2048.android.ui.activity.CoreActivity;

public abstract class BaseFragment extends Fragment {

    final void runOnUiThread(Runnable run) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(run);
        }
    }

    final void changeFragment(String tag, Bundle bundle) {
        if (getActivity() != null) ((CoreActivity) getActivity())
                .changeFragment(tag, bundle, null);
    }

    final void changeFragment(String tag, Bundle bundle, View view) {
        if (getActivity() != null) ((CoreActivity) getActivity())
                .changeFragment(tag, bundle, view);
    }

    final void setupComponentButton(int atIndex , CoreActivity.ActionOnComponent actionOnComponent) {
        if (getActivity() != null) ((CoreActivity) getActivity()).setupComponentButton(atIndex, actionOnComponent);
    }

    final void setupComponentButtons(CoreActivity.ActionOnComponent actionOnComponent, int... indices) {
        if (getActivity() != null) ((CoreActivity) getActivity()).setupComponentButtons(actionOnComponent, indices);
    }

}
