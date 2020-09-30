package com.zoyo.mvvmkotlindemo.ui.fragment

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.zoyo.mvvmkotlindemo.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}