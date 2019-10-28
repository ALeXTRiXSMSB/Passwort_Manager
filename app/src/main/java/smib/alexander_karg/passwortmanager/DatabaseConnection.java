package smib.alexander_karg.passwortmanager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

@Database(entities = {PasswordEntry.class}, exportSchema = false, version = 1)
public abstract class DatabaseConnection extends RoomDatabase {

    private static final String DB_NAME = "password_db";
    private static DatabaseConnection INSTANCE;

    public synchronized static DatabaseConnection getDatabaseConnection(Context context){
        if(INSTANCE == null){
            //INSTANCE = buildDatabase(context);
            INSTANCE = Room.databaseBuilder(context,DatabaseConnection.class,DB_NAME).allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public abstract PasswordEntryDAO getPasswordDAO();

    public static void destroyInstance(){
        INSTANCE = null;
    }

    private static DatabaseConnection buildDatabase(final Context context){
        return Room.databaseBuilder(context, DatabaseConnection.class,DB_NAME).addCallback(new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        getDatabaseConnection(context).getPasswordDAO().insertNewEntry(new PasswordEntry(0,"Passwort Manager","PasswortManager","master","master"));
                    }
                });
            }
        }).build();
    }
}
