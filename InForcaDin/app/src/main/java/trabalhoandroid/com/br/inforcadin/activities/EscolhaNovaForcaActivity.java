package trabalhoandroid.com.br.inforcadin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import trabalhoandroid.com.br.inforcadin.R;

/**
 * Created by Artur on 21/05/2017.
 */

public class EscolhaNovaForcaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolha_nova_forca);

        Button btnCriarNovaForca = (Button) findViewById(R.id.btnCriarNovaForca);
        Button btnListarForcas = (Button) findViewById(R.id.btnListarForcas);

        btnCriarNovaForca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(EscolhaNovaForcaActivity.this, CriaDesafioActivity.class);
                startActivity(it);

                finish();
            }
        });

        btnListarForcas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(EscolhaNovaForcaActivity.this, ListaForcasAcivity.class);
                startActivity(it);

                finish();
            }
        });
    }
}
