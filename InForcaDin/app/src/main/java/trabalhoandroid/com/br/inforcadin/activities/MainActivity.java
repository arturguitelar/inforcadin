package trabalhoandroid.com.br.inforcadin.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import trabalhoandroid.com.br.inforcadin.R;
import trabalhoandroid.com.br.inforcadin.banco.ForcaDao;
import trabalhoandroid.com.br.inforcadin.banco.CriaDados;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_PREFS_FIRST_LAUNCH = "first_launch";
    private Button btnIniciar, btnCriar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // criando algumas forcas para o app já ter algo no banco de dados quando for iniciado pela primera vez
        checarPrimeiraVezECriarForcas();

        setContentView(R.layout.activity_main);

        btnIniciar = (Button) findViewById(R.id.btnJogarInicio);
        btnCriar = (Button) findViewById(R.id.btnCriarInicio);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForcaDao crud = new ForcaDao(getBaseContext());
                Cursor cursor = crud.buscar();
                if(cursor.moveToFirst() == false) {
                    Toast.makeText(MainActivity.this, "Você precisa registrar alguns desafios para brincar de forca.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent it = new Intent(MainActivity.this, SelecionaCategoriaActivity.class);
                    startActivity(it);
                }
            }
        });

        btnCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, EscolhaNovaForcaActivity.class);
                startActivity(it);
            }
        });
    }

    private void checarPrimeiraVezECriarForcas() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        if(pref.getBoolean(KEY_PREFS_FIRST_LAUNCH, true)) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean(KEY_PREFS_FIRST_LAUNCH, false);
            editor.commit();

            ForcaDao crud = new ForcaDao(getBaseContext());
            CriaDados criaDados = new CriaDados(crud);
            criaDados.criar();
        }
    }
}
