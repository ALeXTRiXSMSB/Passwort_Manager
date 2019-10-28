package smib.alexander_karg.passwortmanager;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class AddCredential extends AppCompatActivity {

    private EditText etitle;
    private EditText elink;
    private EditText eusername;
    private EditText epassword;
    private DatabaseConnection dbc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_credential);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbc = DatabaseConnection.getDatabaseConnection(this);
        etitle = findViewById(R.id.etTitel);
        elink = findViewById(R.id.etLink);
        eusername = findViewById(R.id.etUsername);
        epassword = findViewById(R.id.etPassword);
    }

    public void save(View v){
        List<PasswordEntry> tmp = dbc.getPasswordDAO().getAll();

        int newpid = tmp.size()+1;

        String title = etitle.getText().toString();
        String link = elink.getText().toString();
        String username = elink.getText().toString();
        String password = epassword.getText().toString();
        boolean error;
        if(title == null || link == null || username == null || password == null){
            error = true;
        }else if(title.isEmpty() || link.isEmpty() || username.isEmpty() || password.isEmpty()){
            error = true;
        }else{
            error = false;
        }
        if(error){
            Toast.makeText(this,"Bitte alle Felder ausfüllen",Toast.LENGTH_LONG).show();
        }else{
            dbc.getPasswordDAO().insertNewEntry(new PasswordEntry(newpid,title,link,username,password));
            finishActivity(Credentials.FINISHCODE_NEW);
        }
    }

    public void back(View v){
        finishActivity(Credentials.FINISHCODE_NOTHINGS);
    }


}
