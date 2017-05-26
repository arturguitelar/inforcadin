package trabalhoandroid.com.br.inforcadin.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import trabalhoandroid.com.br.inforcadin.R;
import trabalhoandroid.com.br.inforcadin.banco.ForcaDao;
import trabalhoandroid.com.br.inforcadin.banco.ForcaHelperDao;
import trabalhoandroid.com.br.inforcadin.game.DesafioForca;

/**
 * Created by Artur on 21/05/2017.
 */

public class SelecionaCategoriaActivity extends AppCompatActivity {
    private Spinner spnCategorias;
    private Button btnJogar;
    private ForcaDao crud;
    private ArrayList<String> categorias;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleciona_categoria);

        spnCategorias = (Spinner) findViewById(R.id.spinnerSelecionar);
        btnJogar = (Button) findViewById(R.id.btnJogar);

        crud = new ForcaDao(getBaseContext());

        preencherSpinner();

        btnJogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DesafioForca forca = randomizarDesafio();

                Intent it = new Intent(SelecionaCategoriaActivity.this, InGameActivity.class);

                it.putExtra("forca", forca);

                startActivity(it);

                finish();
            }
        });
    }

    private void preencherSpinner() {
        final Cursor cursor = crud.buscar();
        Set<String> catSet = new HashSet<>();

        categorias = new ArrayList<>();

        for(cursor.moveToFirst(); (!cursor.isAfterLast()); cursor.moveToNext()) {
            catSet.add(cursor.getString(1).toString());
        }

        for(String set : catSet) {
            categorias.add(set);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnCategorias.setAdapter(adapter);
    }

    private DesafioForca randomizarDesafio() {
        DesafioForca desafioForca;
        String categoria = spnCategorias.getSelectedItem().toString();
        Cursor cursor = crud.buscarByCategoriaRandom(categoria);

        String cat = cursor.getString(cursor.getColumnIndexOrThrow(ForcaHelperDao.getNomeCategoria()));
        String palavra = cursor.getString(cursor.getColumnIndexOrThrow(ForcaHelperDao.getPALAVRA()));
        List<String> dicas = new ArrayList<>();
        dicas.add(cursor.getString(cursor.getColumnIndexOrThrow(ForcaHelperDao.getDica1())));
        dicas.add(cursor.getString(cursor.getColumnIndexOrThrow(ForcaHelperDao.getDica2())));
        dicas.add(cursor.getString(cursor.getColumnIndexOrThrow(ForcaHelperDao.getDica3())));
        dicas.add(cursor.getString(cursor.getColumnIndexOrThrow(ForcaHelperDao.getDica4())));
        dicas.add(cursor.getString(cursor.getColumnIndexOrThrow(ForcaHelperDao.getDica5())));

        desafioForca = new DesafioForca(cat);
        desafioForca.setPalavra(palavra);
        desafioForca.setDicas(dicas);

        return desafioForca;
    }
}
