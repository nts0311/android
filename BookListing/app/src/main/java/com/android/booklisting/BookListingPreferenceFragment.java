package com.android.booklisting;

import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class BookListingPreferenceFragment extends PreferenceFragmentCompat
{
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey)
    {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        EditTextPreference txtResultLimit = findPreference(getString(R.string.pref_result_limit_key));

        txtResultLimit.setOnBindEditTextListener(new EditTextPreference.OnBindEditTextListener()
        {
            @Override
            public void onBindEditText(@NonNull EditText editText)
            {
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            }
        });

        txtResultLimit.setSummaryProvider(new Preference.SummaryProvider()
        {
            @Override
            public CharSequence provideSummary(Preference preference)
            {
                return ((EditTextPreference) preference).getText();
            }
        });


        ListPreference lstSortType = findPreference(getString(R.string.pref_sort_type_key));

        lstSortType.setSummaryProvider(new Preference.SummaryProvider()
        {
            @Override
            public CharSequence provideSummary(Preference preference)
            {
                return ((ListPreference) preference).getValue();
            }
        });
    }

}














