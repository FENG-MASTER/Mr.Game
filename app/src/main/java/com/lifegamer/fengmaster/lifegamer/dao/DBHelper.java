package com.lifegamer.fengmaster.lifegamer.dao;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by qianzise on 2017/10/5.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="lifeGamer.db";

    public static final String TABLE_HERO="Hero";
    public static final String TABLE_ITEM="Items";
    public static final String TABLE_TASK="Tasks";
    public static final String TABLE_ACHIEVEMENT="Achievement";
    public static final String TABLE_NOTE="Notes";
    public static final String TABLE_REWARD="Rewards";
    public static final String TABLE_SKILL="Skills";

    /**
     * 创建 英雄 表
     */
    private static final String CREATE_TABLE_HERO="create table if not exists "+TABLE_HERO+
            "( id integer primary key autoincrement," +
            "name varchar," +
            "title varchar,"+
            "level integer," +
            "xp integer," +
            "upGradeXP integer," +
            "introduction varchar," +
            "avatar varchar)";

    /**
     * 创建 技能 表
     */
    private static final String CREATE_TABLE_SKILL="create table if not exists "+TABLE_SKILL+
            "( id integer primary key autoincrement," +
            "name varchar unique ," +
            "xp integer," +
            "level integer," +
            "type varchar," +
            "intro varchar," +
            "upGradeXP integer," +
            "icon varchar," +
            "notes text," +
            "integer createTime," +
            "integer updateTime)";

    /**
     * 创建 物品 表
     */
    private static final String CREATE_TABLE_ITEM="create table if not exists "+TABLE_ITEM+
            "( id integer primary key autoincrement," +
            "name varchar unique," +
            "desc varchar," +
            "quantity integer," +
            "icon varchar," +
            "expendable boolean," +
            "notes text," +
            "integer createTime," +
            "integer updateTime)";

    /**
     * 创建 成就 表
     */
    private static final String CREATE_TABLE_ACHIEVEMENT="create table if not exists "+TABLE_ACHIEVEMENT+
            "( id integer primary key autoincrement," +
            "name varchar unique," +
            "type varchar," +
            "icon varchar," +
            "desc varchar," +
            "isGot boolean," +
            "gainTime integer," +
            "notes text," +
            "createTime integer," +
            "updateTime integer)";

    /**
     * 创建 奖励 表
     */
    private static final String CREATE_TABLE_REWARD="create table if not exists "+TABLE_REWARD+
            "( id integer primary key autoincrement," +
            "name varchar unique," +
            "type varchar," +
            "icon varchar" +
            "desc varchar," +
            "costLPIncrement integer," +
            "addToItem boolean," +
            "notes text" +
            "costLP integer," +
            "quantityAvailable integer," +
            "gainTime integer,createTime integer,updateTime integer)";

    /**
     * 创建 笔记 表
     */
    private static final String CREATE_TABLE_NOTE="create table if not exists "+TABLE_NOTE+
            "( id integer primary key autoincrement," +
            "text text," +
            "crateTime integer," +
            "updateTime integer)";

    private static final String CREATE_TABLE_TASK="create table if not exists "+TABLE_TASK+
            "( id integer primary key autoincrement," +
            "name varchar unique," +
            "desc varchar," +
            "isAutoFail boolean," +
            "icon varchar," +
            "difficulty integer," +
            "urgency integer," +
            "fear integer," +
            "successSkills text," +
            "successItems text," +
            "successAchievements text," +
            "failureSkills text," +
            "failureItems text," +
            "failureAchievements text," +
            "earnLP integer," +
            "lostLP integer," +
            "repeatType integer," +
            "repeatInterval integer," +
            "repeatAvailableTime integer," +
            "expirationTime integer," +
            "completeTimes integer," +
            "failureTimes integer," +
            "preTasks text," +
            "notes text," +
            "createTime integer," +
            "updateTime integer)";



    public DBHelper(Context context){
        super(context,DB_NAME,null,1);
    }


    public DBHelper(Context context, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, version);
    }

    public DBHelper(Context context, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, DB_NAME, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_HERO);
        sqLiteDatabase.execSQL(CREATE_TABLE_SKILL);
        sqLiteDatabase.execSQL(CREATE_TABLE_ITEM);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}