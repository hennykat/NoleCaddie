package com.syntacticsugar.nolecaddie.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
        return gson.fromJson(scoreListJson, listType);
    }

    public void saveCurrentHole(List<Hole> scoreList) {
        Gson gson = new Gson();
        String scoreListJson = gson.toJson(scoreList);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SCORE_LIST_KEY, scoreListJson);
        editor.apply();
    }
}
