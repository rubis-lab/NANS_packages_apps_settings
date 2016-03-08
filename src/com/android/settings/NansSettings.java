/*
 * Copyright (C) 2016 RUBIS Laboratory at Seoul National University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceGroup;
import android.preference.PreferenceScreen;
import android.provider.Settings;
import android.util.Log;

public class NansSettings extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    private static final String TAG = "NansSettings";

    private static final String KEY_NANS_SETTINGS = "nans_settings";
    private static final String KEY_NANS = "nans";
	private static final String KEY_SCREEN_SWIPER_SETTINGS = "screen_swiper_settings";
	private static final String KEY_SCREEN_SWIPER = "screen_swiper";

    private CheckBoxPreference mNans;
	private CheckBoxPreference mScreenSwiper;

    private PreferenceGroup mNansSettings;
	private PreferenceGroup mScreenSwiperSettings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContentResolver resolver = getContentResolver();
        addPreferencesFromResource(R.xml.nans_settings);

        mNans = (CheckBoxPreference) findPreference(KEY_NANS);
		mScreenSwiper = (CheckBoxPreference) findPreference(KEY_SCREEN_SWIPER);
        mNans.setPersistent(false);
		mScreenSwiper.setPersistent(false);
        mNans.setChecked(Settings.Global.getInt(resolver,
                Settings.Global.NANS_ENABLED, 0) != 0);

		mScreenSwiper.setEnabled(mNans.isChecked());
        mScreenSwiper.setChecked(Settings.Secure.getInt(resolver,
                Settings.Secure.ACCESSIBILITY_SCREEN_SWIPE_ENABLED, 0) != 0);

        mNansSettings = (PreferenceGroup) findPreference(KEY_NANS_SETTINGS);
        mScreenSwiperSettings = (PreferenceGroup) findPreference(KEY_SCREEN_SWIPER_SETTINGS);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference == mNans) {
			boolean enabled = mNans.isChecked();
            boolean result = Settings.Global.putInt(getContentResolver(), Settings.Global.NANS_ENABLED, enabled ? 1 : 0);
			mScreenSwiper.setEnabled(enabled);
        }

        if (preference == mScreenSwiper) {
        	boolean result = Settings.Secure.putInt(getContentResolver(), Settings.Secure.ACCESSIBILITY_SCREEN_SWIPE_ENABLED,
            		mScreenSwiper.isChecked() ? 1 : 0);
        }

        return true;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return true;
    }
}
