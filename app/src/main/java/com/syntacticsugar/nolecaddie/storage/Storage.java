package com.syntacticsugar.nolecaddie.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.syntacticsugar.nolecaddie.config.AppConfig;
import com.syntacticsugar.nolecaddie.model.Hole;

import java.lang.reflect.Type;
import java.util.List;

public class Storage {

    public static final String TAG = Storage.class.getSimpleName();

    private static final String STORAGE_FILE = "com.syntacticsugar.nolecaddie.storage";
    // keys
    private static final String CURRENT_HOLE_KEY = "com.syntacticsugar.nolecaddie.storage.current_hole";
    private static final String SCORE_LIST_KEY = "com.syntacticsugar.nolecaddie.storage.score_list";

    private SharedPreferences sharedPreferences;

    public Storage(Context context) {
        this.sharedPreferences = context.getSharedPreferences(STORAGE_FILE, Context.MODE_PRIVATE);
    }

    public int loadCurrentHole() {
        return sharedPreferences.getInt(CURRENT_HOLE_KEY, 0);
    }

    public void saveCurrentHole(int currentHole) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CURRENT_HOLE_KEY, currentHole);
        editor.apply();
    }

    public List<Hole> loadScoreList() {
        Gson gson = new Gson();
        String scoreListJson = sharedPreferences.getString(SCORE_LIST_KEY, null);
        Type listType = new TypeToken<List<Hole>>() {
        }.getType();

        List<Hole> scoreList = gson.fromJson(scoreListJson, listType);
        if (scoreList == null || scoreList.isEmpty()) {
            Log.w(TAG, "failed to load score list, returning default");
            return AppConfig.getHoleList();
        } else {
            return scoreList;
        }
    }

    public void saveScoreList(List<Hole> scoreList) {
        Gson gson = new Gson();
        String scoreListJson = gson.toJson(scoreList);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SCORE_LIST_KEY, scoreListJson);
        editor.apply();
    }

    public void updateScore(int score, int holeNum) {

        List<Hole> scoreList = loadScoreList();
        if (scoreList == null || scoreList.isEmpty()) {
            Log.w(TAG, "failed to update score, invalid score list");
            return;
        }

        // alter hole
        Hole current = scoreList.get(holeNum);
        if (current == null) {
            Log.w(TAG, "failed to update score, invalid hole");
            return;
        }

        current.setScore(score);
        scoreList.set(holeNum, current);
        saveScoreList(scoreList);
    }

    public Integer getScore(int holeNum) {

        List<Hole> scoreList = loadScoreList();
        if (scoreList == null || scoreList.isEmpty()) {
            Log.w(TAG, "failed to get score, invalid score list");
            return null;
        }

        // alter hole
        Hole current = scoreList.get(holeNum);
        if (current != null) {
            return current.getScore();
        } else {
            Log.w(TAG, "failed to get score, invalid hole");
            return null;
        }
    }
}
