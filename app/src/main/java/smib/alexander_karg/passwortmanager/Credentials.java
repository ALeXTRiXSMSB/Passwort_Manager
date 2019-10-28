package smib.alexander_karg.passwortmanager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.List;

public class Credentials extends AppCompatActivity {

    private DatabaseConnection dbc;
    private ListView lvAllCredentials;
    private ArrayAdapter<String> adapter;
    private List<PasswordEntry> entrys;
    public static final int FINISHCODE_NEW = 0;
    public static final int FINISHCODE_EDIT = 1;
    public static final int FINISHCODE_NOTHINGS = 2;
    public static final int FINISHCODE_FEHLER = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credentials);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbc = DatabaseConnection.getDatabaseConnection(this);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        this.update();
        lvAllCredentials.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String symbol = parent.getItemAtPosition(position).toString();
                if(symbol.equals("+")){
                    Intent i = new Intent(parent.getContext(),AddCredential.class);
                    startActivityForResult(i,FINISHCODE_NEW);
                }else{
                    int tmp = entrys.get(position).getPid();
                    Intent i = new Intent(parent.getContext(),ShowCredential.class);
                    Bundle b = new Bundle();
                    b.putInt("PID",tmp);
                    i.putExtras(b);
                    startActivityForResult(i,FINISHCODE_EDIT);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestcode, int resultCode, Intent data){
        update();
    }

    public ListView getLvAllCredentials() {
        return lvAllCredentials;
    }

    private void update(){
        entrys = dbc.getPasswordDAO().getAll();
        for(PasswordEntry p: entrys){
            this.adapter.add(p.getTitle());
        }
        this.adapter.add("+");
        lvAllCredentials = findViewById(R.id.allCreds);
        lvAllCredentials.setAdapter(adapter);
    }
}
