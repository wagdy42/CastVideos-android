/*
 * Copyright (C) 2013 Google Inc. All Rights Reserved. 
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

package com.google.sample.cast.refplayer.settings;

import com.google.sample.cast.refplayer.CastApplication;
import com.google.sample.cast.refplayer.R;
import com.google.sample.cast.refplayer.utils.Utils;
import com.google.sample.castcompanionlibrary.cast.VideoCastManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

public class CastPreference extends PreferenceActivity
        implements OnSharedPreferenceChangeListener {

    public static final String APP_DESTRUCTION_KEY = "application_destruction";
    public static final String FTU_SHOWN_KEY = "ftu_shown";
    public static final String VOLUME_SELCTION_KEY = "volume_target";
    private static final String TAG = "CastPreference";
    private ListPreference mListPreference;
    private SharedPreferences mPrefs;
    private VideoCastManager mCastManager;
    boolean mStopOnExit;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.application_preference);
        getPreferenceScreen().getSharedPreferences().
                registerOnSharedPreferenceChangeListener(this);

        mListPreference = (ListPreference) getPreferenceScreen()
                .findPreference(VOLUME_SELCTION_KEY);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String value = mPrefs.getString(
                VOLUME_SELCTION_KEY, getString(R.string.prefs_volume_default));
        String summary = getResources().getString(R.string.prefs_volume_title_summary, value);
        mListPreference.setSummary(summary);
        mCastManager = CastApplication.getCastManager(this);
        EditTextPreference versionPref = (EditTextPreference) findPreference("app_version");
        versionPref.setTitle(getString(R.string.version, Utils.getAppVersionName(this)));
        mStopOnExit = mPrefs.getBoolean(APP_DESTRUCTION_KEY, false);
        mCastManager.setStopOnDisconnect(mStopOnExit);
    }

    public static boolean isDestroyAppOnDisconnect(Context ctx) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx);
        return sharedPref.getBoolean(APP_DESTRUCTION_KEY, false);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
            String key) {
        if (VOLUME_SELCTION_KEY.equals(key)) {
            String value = sharedPreferences.getString(VOLUME_SELCTION_KEY, "");
            String summary = getResources().getString(R.string.prefs_volume_title_summary, value);
            mListPreference.setSummary(summary);
        } else if (APP_DESTRUCTION_KEY.equals(key)) {
            mStopOnExit = sharedPreferences.getBoolean(APP_DESTRUCTION_KEY, false);
            Log.d(TAG, "destroyOnDisconnect: " + mStopOnExit);
            mCastManager.setStopOnDisconnect(mStopOnExit);
        }
    }

    public static boolean isFtuShown(Context ctx) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx);
        return sharedPref.getBoolean(FTU_SHOWN_KEY, false);
    }

    public static void setFtuShown(Context ctx) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx);
        sharedPref.edit().putBoolean(FTU_SHOWN_KEY, true).commit();
    }

    @Override
    protected void onResume() {
        if (null != mCastManager) {
            mCastManager.incrementUiCounter();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (null != mCastManager) {
            mCastManager.decrementUiCounter();
        }
        super.onPause();
    }

}
