package com.example.jcoder.gp_androidcloud.fragment;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;

import com.example.jcoder.gp_androidcloud.R;

/**
 * Created by JCoder on 2018/3/15.
 */

public class SettingFragment extends PreferenceFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_settings);


    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if ("select_linkage".equals(preference.getKey())) {
            CheckBoxPreference checkBox = (CheckBoxPreference) findPreference("select_linkage");
            ListPreference editBox = (ListPreference) findPreference("select_city");
            editBox.setEnabled(checkBox.isChecked());
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
}
