package trabalhoandroid.com.br.inforcadin.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import trabalhoandroid.com.br.inforcadin.R;

public class DerrotaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_derrota);

        Button btnJogarNovamente = (Button) findViewById(R.id.btnJogarNovamente);

        btnJogarNovamente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(DerrotaActivity.this, SelecionaCategoriaActivity.class);
                startActivity(it);

                finish();
            }
        });
    }
}
