package smib.alexander_karg.passwortmanager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class ShowCredential extends AppCompatActivity {

    private EditText etitle;
    private EditText elink;
    private EditText eusername;
    private EditText epassword;
    private DatabaseConnection dbc;
    private int pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_credential);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbc = DatabaseConnection.getDatabaseConnection(this);
        etitle = findViewById(R.id.etTitel);
        elink = findViewById(R.id.etLink);
        eusername = findViewById(R.id.etUsername);
        epassword = findViewById(R.id.etPassword);

        Intent i = getIntent();
        if(i != null){
            pid = i.getIntExtra("PID",0);
            List<PasswordEntry> tmp = dbc.getPasswordDAO().getAll();
            for(PasswordEntry p : tmp){
                if(pid == p.getPid()){
                    etitle.setText(p.getTitle());
                    elink.setText(p.getLink());
                    eusername.setText(p.getUsername());
                    epassword.setText(p.getPassword());
                }
            }
        }else{
            Toast.makeText(this,"Fehler bei der Übertragung oder so",Toast.LENGTH_LONG).show();
            finishActivity(Credentials.FINISHCODE_FEHLER);
        }
    }

    public void saveEdit(View v){
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
            dbc.getPasswordDAO().updatePassword(new PasswordEntry(pid,title,link,username,password));
            finishActivity(Credentials.FINISHCODE_EDIT);
            finish();
        }
    }

    public void delete(View v){
        finishActivity(Credentials.FINISHCODE_NOTHINGS);
    }
}
