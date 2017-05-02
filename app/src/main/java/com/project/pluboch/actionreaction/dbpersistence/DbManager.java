package com.project.pluboch.actionreaction.dbpersistence;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.project.pluboch.actionreaction.ActionReaction;
import com.project.pluboch.actionreaction.actions.AbstractUserAction;
import com.project.pluboch.actionreaction.actions.LocationUserAction;
import com.project.pluboch.actionreaction.actions.TimeUserAction;
import com.project.pluboch.actionreaction.actions.UserActionType;
import com.project.pluboch.actionreaction.actions.WifiNameUserAction;
import com.project.pluboch.actionreaction.reactions.AbstractUserReaction;
import com.project.pluboch.actionreaction.reactions.SmsSendUserReaction;
import com.project.pluboch.actionreaction.reactions.UserReactionType;
import com.project.pluboch.actionreaction.reactions.VolumeUserReaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr on 2017-05-02.
 */

public class DbManager extends SQLiteOpenHelper {
    private static final String TAG = "DbManager";

    public static final String DATABASE_NAME = "ActionReaction.db";

    public static final String ACTION_REACTION_TABLE_NAME = "ACTION_REACTION";
    public static final String ACTION_REACTION_ID = "AR_ID";
    public static final String ACTION_REACTION_A_ID = "A_ID";
    public static final String ACTION_REACTION_R_ID = "R_ID";
    public static final String ACTION_REACTION_DESCRIPTION = "AR_DESCRIPTION";

    public static final String ACTION_TABLE_NAME = "ACTION";
    public static final String ACTION_ID = "A_ID";
    public static final String ACTION_TYPE_ID = "A_TYPE_ID";
    public static final String ACTION_PARAMS_COUNT = "A_PARAMS_COUNT";
    public static final String ACTION_PARAMS = "A_PARAMS";

    public static final String REACTION_TABLE_NAME = "REACTION";
    public static final String REACTION_ID = "R_ID";
    public static final String REACTION_TYPE_ID = "R_TYPE_ID";
    public static final String REACTION_PARAMS_COUNT = "R_PARAMS_COUNT";
    public static final String REACTION_PARAMS = "R_PARAMS";

    public static final String TYPE_ACTION_TABLE_NAME = "TYPE_ACTION";
    public static final String TYPE_ACTION_ID = "TA_ID";
    public static final String TYPE_ACTION_NAME = "TA_NAME";

    public static final String TYPE_REACTION_TABLE_NAME = "TYPE_REACTION";
    public static final String TYPE_REACTION_ID = "TR_ID";
    public static final String TYPE_REACTION_NAME = "TR_NAME";

    public DbManager(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    public void insertActionReaction(ActionReaction actionReaction) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();
        AbstractUserAction userAction = actionReaction.getUserAction();
        insertAction(userAction, db);
        AbstractUserReaction userReaction = actionReaction.getUserReaction();
        insertReaction(userReaction, db);

        ContentValues contentValues = new ContentValues();
        int id = getNumberOfRecords(db) + 1;
        contentValues.put(ACTION_REACTION_A_ID, id);
        contentValues.put(ACTION_REACTION_R_ID, id);
        contentValues.put(ACTION_REACTION_DESCRIPTION, actionReaction.getTitle());
        db.insert(ACTION_REACTION_TABLE_NAME, null, contentValues);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    private void insertAction(AbstractUserAction userAction, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACTION_TYPE_ID, userAction.getUserActionType().getId());
        contentValues.put(ACTION_PARAMS_COUNT, userAction.getParamNumber());
        contentValues.put(ACTION_PARAMS, userAction.dbParamsRepresentation());
        db.insert(ACTION_TABLE_NAME, null, contentValues);
    }

    private void insertReaction(AbstractUserReaction userReaction, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(REACTION_TYPE_ID, userReaction.getUserReactionType().getId());
        contentValues.put(REACTION_PARAMS_COUNT, userReaction.getParamNumber());
        contentValues.put(REACTION_PARAMS, userReaction.dbParamsRepresentation());
        db.insert(REACTION_TABLE_NAME, null, contentValues);
    }

    private int getNumberOfRecords(SQLiteDatabase db) {
        return (int) DatabaseUtils.queryNumEntries(db, ACTION_REACTION_TABLE_NAME);
    }

    public List<ActionReaction> getAllActionReactions(Context context, Activity activity) {
        SQLiteDatabase db = this.getWritableDatabase();

        String columns = ACTION_REACTION_DESCRIPTION + ", " + TYPE_ACTION_NAME + "," +
                " " + TYPE_REACTION_NAME + "," +
                " " + ACTION_PARAMS_COUNT + "," +
                " " + ACTION_PARAMS + "," +
                " " + REACTION_PARAMS_COUNT + "," +
                " " + REACTION_PARAMS;

        String query = "select " + columns + " from " + ACTION_REACTION_TABLE_NAME + " as ar" +
                " inner join " + ACTION_TABLE_NAME + " as a on ar." + ACTION_REACTION_A_ID + " = a." + ACTION_ID +
                " inner join " + REACTION_TABLE_NAME + " as r on ar." + ACTION_REACTION_R_ID + " = r." + REACTION_ID +
                " inner join " + TYPE_ACTION_TABLE_NAME + " as ta on a." + ACTION_TYPE_ID + " = ta." + TYPE_ACTION_ID +
                " inner join " + TYPE_REACTION_TABLE_NAME + " as tr on r." + REACTION_TYPE_ID + " = tr." + TYPE_REACTION_ID + " ;";
        Log.i(TAG, "getAllActionReactions: executed query: " + query);
        Cursor cursor = db.rawQuery(query, new String[]{});
        cursor.moveToFirst();

        List<ActionReaction> list = new ArrayList<>();

        while (cursor.isAfterLast() == false) {
            int index = cursor.getColumnIndex(ACTION_REACTION_DESCRIPTION);
            String description = cursor.getString(index);

            index = cursor.getColumnIndex(TYPE_ACTION_NAME);
            String actionName = cursor.getString(index);
            UserActionType userActionType = UserActionType.getActionByName(actionName);

            index = cursor.getColumnIndex(ACTION_PARAMS);
            String actionString = cursor.getString(index);

            AbstractUserAction userAction = parseActionString(userActionType, actionString, context, activity);
            Log.i(TAG, "getAllActionReactions: " + userAction.toString());

            index = cursor.getColumnIndex(TYPE_REACTION_NAME);
            String reactionName = cursor.getString(index);
            UserReactionType userReactionType = UserReactionType.getReactionByName(reactionName);

            index = cursor.getColumnIndex(REACTION_PARAMS);
            String reactionString = cursor.getString(index);

            AbstractUserReaction userReaction = parseReactionString(userReactionType, reactionString, context);
            Log.i(TAG, "getAllActionReactions: " + userReaction);

            ActionReaction actionReaction = new ActionReaction(description, userAction, userReaction);

            list.add(actionReaction);
            cursor.moveToNext();
        }

        return list;
    }

    private AbstractUserAction parseActionString(UserActionType userActionType, String actionString, Context context, Activity activity) {
        AbstractUserAction userAction = null;
        String delimeter = AbstractUserAction.DELIMETER;
        String[] strings = actionString.split(delimeter);

        switch (userActionType) {
            case LOCATION:
                userAction = new LocationUserAction(strings[0], Double.parseDouble(strings[1]), Double.parseDouble(strings[2]), context, activity);
                break;
            case WIFI_NAME:
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                userAction = new WifiNameUserAction(strings[0], wifiManager);
                break;
            case TIME:
                userAction = new TimeUserAction(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]));
                break;
        }
        return userAction;
    }

    private AbstractUserReaction parseReactionString(UserReactionType userReactionType, String actionString, Context context) {
        AbstractUserReaction userReaction = null;
        String delimeter = AbstractUserReaction.DELIMETER;
        String[] strings = actionString.split(delimeter);

        switch (userReactionType) {
            case SEND_SMS:
                userReaction = new SmsSendUserReaction(strings[0], strings[1]);
                break;
            case VOLUME:
                AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                userReaction = new VolumeUserReaction(Integer.parseInt(strings[0]), audioManager);
                break;
        }
        return userReaction;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate: creating database");
        db.beginTransaction();
        db.execSQL("create table " + ACTION_REACTION_TABLE_NAME + " (" +
                ACTION_REACTION_ID + " integer primary key autoincrement, " +
                ACTION_REACTION_A_ID + " integer, " +
                ACTION_REACTION_R_ID + " integer, " +
                ACTION_REACTION_DESCRIPTION + " text, " +
                "foreign key(" + ACTION_REACTION_A_ID + ") references " + ACTION_TABLE_NAME + "(" + ACTION_ID + "), " +
                "foreign key(" + ACTION_REACTION_R_ID + ") references " + REACTION_TABLE_NAME + "(" + REACTION_ID + ") " +
                ");");

        db.execSQL("create table " + ACTION_TABLE_NAME + " (" +
                ACTION_ID + " integer primary key autoincrement, " +
                ACTION_TYPE_ID + " integer, " +
                ACTION_PARAMS_COUNT + " integer, " +
                ACTION_PARAMS + " text, " +
                "foreign key(" + ACTION_TYPE_ID + ") references " + TYPE_ACTION_TABLE_NAME + "(" + TYPE_ACTION_ID + ") " +
                ");");

        db.execSQL("create table " + REACTION_TABLE_NAME + " (" +
                REACTION_ID + " integer primary key autoincrement, " +
                REACTION_TYPE_ID + " integer, " +
                REACTION_PARAMS_COUNT + " integer, " +
                REACTION_PARAMS + " text, " +
                "foreign key(" + REACTION_TYPE_ID + ") references " + TYPE_REACTION_TABLE_NAME + "(" + TYPE_REACTION_ID + ") " +
                ");");

        db.execSQL("create table " + TYPE_ACTION_TABLE_NAME + " (" +
                TYPE_ACTION_ID + " integer primary key autoincrement, " +
                TYPE_ACTION_NAME + " text unique" +
                ");");

        db.execSQL("create table " + TYPE_REACTION_TABLE_NAME + " (" +
                TYPE_REACTION_ID + " integer primary key autoincrement, " +
                TYPE_REACTION_NAME + " text unique" +
                ");");

        db.execSQL("insert into " + TYPE_ACTION_TABLE_NAME +
                " (" + TYPE_ACTION_ID + ", " + TYPE_ACTION_NAME + ") values " +
                " (1,'LOCATION')," +
                " (2,'WIFI_NAME')," +
                " (3,'TIME')," +
                " (4,'DATE')," +
                " (5,'SMS_RECEIVED')," + "" +
                " (6,'BATTERY_STATE')" +
                ";"
        );
        db.execSQL("insert into " + TYPE_REACTION_TABLE_NAME +
                " (" + TYPE_REACTION_ID + ", " + TYPE_REACTION_NAME + ") values " +
                " (1,'VOLUME')," +
                " (2,'SEND_SMS')," +
                " (3,'NOTIFICATION')" +
                ";"
        );

        db.setTransactionSuccessful();
        db.endTransaction();
        Log.i(TAG, "onCreate: created database");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ACTION_REACTION_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ACTION_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + REACTION_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TYPE_ACTION_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TYPE_REACTION_TABLE_NAME);
        onCreate(db);
    }
}
