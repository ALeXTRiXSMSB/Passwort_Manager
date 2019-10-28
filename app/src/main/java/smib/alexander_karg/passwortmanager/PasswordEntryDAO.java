package smib.alexander_karg.passwortmanager;

import java.util.List;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PasswordEntryDAO {

    //CRUD
    //CREATE
    @Insert
    void insertNewEntry(PasswordEntry password);
    //READ
    @Query("SELECT * FROM PasswordEntry")
    List<PasswordEntry> getAll();
    //UPDATE
    @Update
    void updatePassword(PasswordEntry password);
    //DELETE
}
