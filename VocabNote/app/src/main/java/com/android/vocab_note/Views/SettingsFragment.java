package com.android.vocab_note.Views;

import android.os.Bundle;

import 	androidx.preference.PreferenceFragmentCompat;

import com.android.vocab_note.R;

public class SettingsFragment extends PreferenceFragmentCompat
{
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey)
    {
        addPreferencesFromResource(R.xml.main_prefs);
    }
}
