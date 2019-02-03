package com.williampembleton.milestone;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

public class Player {

    static int health = 50;  //0
    static int exp = 0;      //1
    static int maxExp = 100; //2
    static int level = 1;    //3
    static int streak = 0;   //4
    static long lastDayIn = new Date().getTime();//5
    static ArrayList<Long> playerInfo = new ArrayList<>();

    //increases the player's xp (called when task is checked off
    public static void increaseExp(int amount) {
        //increase xp by amount
        playerInfo.set(1, playerInfo.get(1) + amount);

        //if xp > maxExp
        if (playerInfo.get(1) > playerInfo.get(2)) {
            //while xp > maxExp
            while (playerInfo.get(1) > playerInfo.get(2)) {
                //overflow
                //subtract xp from max xp
                playerInfo.set(1, playerInfo.get(1) - playerInfo.get(2));
                //increase level
                playerInfo.set(3, playerInfo.get(3) + 1);
                //sets maxExp
                playerInfo.set(2, (long) (Math.pow(playerInfo.get(3), 1.2) * 100));

                //increases health
                increaseHealth(10);
                //so it doesn't go too high
                if (playerInfo.get(0) > 50)
                    playerInfo.set(0, (long) 50);
            }
        }
        savePlayerInfo();
    }

    //decreases the player's health, called by AllTasks.streak to punish players for failing tasks
    public static void decreaseHealth(int amount) {
        //decreases the health amount
        playerInfo.set(0, playerInfo.get(0) - amount);

        //if your health is below or equal to 0
        if (playerInfo.get(0) <= 0) {
            if (playerInfo.get(3) > 5) {
                //decrease level by 5 if you die
                playerInfo.set(3, playerInfo.get(3) - 5);
            } else {
                //if you're below lvl 5 sets your level to 1
                playerInfo.set(3, (long) 1);
            }
            //sets health to max (50)
            playerInfo.set(0, (long) 50);
        }
        //sets streak to 0
        playerInfo.set(4, (long) 0);
        savePlayerInfo();
    }

    //increases the player's health
    public static void increaseHealth(int amount) {
        health = (int) (playerInfo.get(0) + amount);
        if (health > 50)
            health = 50;
        playerInfo.set(0, (long) health);
    }

    //increases the player's streak (days they had no tasks past due
    public static void increaseStreak() {
        playerInfo.set(4, playerInfo.get(4) + 1);
        savePlayerInfo();
    }

    public static void setStreakToZero()
    {
        playerInfo.set(4,(long)0);
        savePlayerInfo();
    }

    //saves the player's info so when the app closes it keeps the information saved
    public static void savePlayerInfo() {
        SharedPreferences sharedPreferences = Calendar.sharedPreferences;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(playerInfo);
        editor.putString("player info", json);
        editor.apply();
        Log.d("Saving player info", playerInfo.toString());
    }

    //loads the players info into playerInfo
    public static void loadPlayerInfo() {
        //SharedPreferences settings = Calendar.sharedPreferences;
        //settings.edit().clear().commit();

        SharedPreferences sharedPreferences = Calendar.sharedPreferences;
        Gson gson = new Gson();
        String json = sharedPreferences.getString("player info", null);
        Type type = new TypeToken<ArrayList<Long>>() {
        }.getType();
        playerInfo = gson.fromJson(json, type);
        if (playerInfo == null) {
            playerInfo = new ArrayList<>();
            playerInfo.add((long) health);
            playerInfo.add((long) exp);
            playerInfo.add((long) maxExp);
            playerInfo.add((long) level);
            playerInfo.add((long) streak);
            playerInfo.add(lastDayIn);
        }

        Log.d("Loading player info", playerInfo.toString());
    }

    //called by ActivityName.setupStreak to change the last time the player logged in
    public static void setLastDayIn(long time) {
        playerInfo.set(5, time);
        savePlayerInfo();
    }
}
