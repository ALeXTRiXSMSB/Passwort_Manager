package smib.alexander_karg.passwortmanager;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.List;

public class Login extends AppCompatActivity {

    private DatabaseConnection dbc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbc = DatabaseConnection.getDatabaseConnection(this);
    }

    public void loginClick(View v){
        List<PasswordEntry> tmp = dbc.getPasswordDAO().getAll();
        if(tmp.size() == 0){
            Toast.makeText(this, "Noch kein Passwort gesetzt Init passwort wird gesetzt mit master", Toast.LENGTH_LONG).show();
            dbc.getPasswordDAO().insertNewEntry(new PasswordEntry(0,"master","master","master","master"));
            Toast.makeText(this, getDatabasePath("password_db").getAbsolutePath(), Toast.LENGTH_LONG).show();
        }else{
            EditText loginForm = findViewById(R.id.ETpassword);
            String inputpw = loginForm.getText().toString();
            String masterpw = tmp.get(0).getPassword();
            if(inputpw.equals(masterpw)){
                Intent i = new Intent(this,Credentials.class);
                startActivity(i);
            }else{
                Toast.makeText(this,"Password nicht korrekt", Toast.LENGTH_LONG).show();
            }
        }
    }
}
