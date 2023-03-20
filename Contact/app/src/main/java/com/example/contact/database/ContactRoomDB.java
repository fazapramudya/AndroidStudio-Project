package com.example.contact.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactRoomDB extends RoomDatabase {
    private static volatile ContactRoomDB INSTANCE;

    public static ContactRoomDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ContactRoomDB.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ContactRoomDB.class, "note_db").build();
            }
        }
        return INSTANCE;
    }

    public abstract DAOContact daoContact();
}

