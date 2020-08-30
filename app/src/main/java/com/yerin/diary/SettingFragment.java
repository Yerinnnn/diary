package com.yerin.diary;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;

public class SettingFragment extends PreferenceFragment {
    SharedPreferences sp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_preference);

        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());

        boolean isMessage = sp.getBoolean("message", false);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals("alarm")) {
                boolean b = sp.getBoolean("alarm", false);

            } else if (key.equals("password")) {
                boolean b = sp.getBoolean("password", false);

            } else if (key.equals("font_list")) {

            } else if (key.equals("developer_information")) {

            }
        }
    };
}
