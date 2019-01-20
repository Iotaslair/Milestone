package com.williampembleton.milestone;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Player {

    static int health = 50;  //0
    static int exp = 0;      //1
    static int maxExp = 100; //2
    static int level = 1;    //3
    static ArrayList<Integer> playerInfo = new ArrayList<>();

    public static void increaseExp(int amount)
    {
        //increase xp by amount
        playerInfo.set(1,playerInfo.get(1) + amount);

        //if xp > maxExp
        if(playerInfo.get(1) > playerInfo.get(2))
        {
            //while xp > maxExp
            while(playerInfo.get(1) > playerInfo.get(2)) {
                //overflow
                //subtract xp from max xp
                playerInfo.set(1,playerInfo.get(1) - playerInfo.get(2));
                //increase level
                playerInfo.set(3,playerInfo.get(3) + 1);
                //sets maxExp
                playerInfo.set(2, (int) (Math.pow(playerInfo.get(3), 1.2) * 100));

                //increases health
                playerInfo.set(0,playerInfo.get(0) + 10);
                //so it doesn't go too high
                if(playerInfo.get(0) > 50)
                    playerInfo.set(0,50);
            }
        }
        savePlayerInfo();
    }

    public static void savePlayerInfo()
    {
        SharedPreferences sharedPreferences = Calendar.sharedPreferences;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(playerInfo);
        editor.putString("player info", json);
        editor.apply();
        Log.d("ME TESTING", playerInfo.toString());
    }

    public static void loadPlayerInfo()
    {
        SharedPreferences sharedPreferences = Calendar.sharedPreferences;
        Gson gson = new Gson();
        String json = sharedPreferences.getString("player info", null);
        Type type = new TypeToken<ArrayList<Integer>>(){}.getType();
        playerInfo = gson.fromJson(json, type);
        if (playerInfo == null) {
            playerInfo = new ArrayList<>();
            playerInfo.add(health);
            playerInfo.add(exp);
            playerInfo.add(maxExp);
            playerInfo.add(level);
        }
    }


}
