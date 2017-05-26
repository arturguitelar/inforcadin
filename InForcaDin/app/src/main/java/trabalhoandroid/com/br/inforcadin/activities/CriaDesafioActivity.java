package trabalhoandroid.com.br.inforcadin.activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import trabalhoandroid.com.br.inforcadin.R;
import trabalhoandroid.com.br.inforcadin.banco.*;

/**
 * Created by Artur on 19/05/2017.
 */

public class CriaDesafioActivity extends AppCompatActivity {

    private Spinner spnCategorias;
    private Button btnSalvar, btnEditar, btnExcluir;
    private EditText etNovaCategoria, etPalavraForca;
    private EditText[] dicas;
    private ArrayList<String> categorias;
    private ForcaDao crud;

    String codigo;
    Cursor cursorCodigo;

    // para armazenar os campos quando convertidos para string
    String pal, d1, d2, d3, d4, d5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cria_desafio);

        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnEditar = (Button) findViewById(R.id.btnEditar);
        btnExcluir = (Button) findViewById(R.id.btnExcluir);

        etNovaCategoria = (EditText) findViewById(R.id.etNovaCategoria);
        etPalavraForca = (EditText) findViewById(R.id.etPalavraForca);

        dicas = new EditText[] {
                (EditText) findViewById(R.id.etDica_1),
                (EditText) findViewById(R.id.etDica_2),
                (EditText) findViewById(R.id.etDica_3),
                (EditText) findViewById(R.id.etDica_4),
                (EditText) findViewById(R.id.etDica_5)
        };

        spnCategorias = (Spinner) findViewById(R.id.spnCategoria);

        crud = new ForcaDao(getBaseContext());

        codigo = getIntent().getStringExtra("codigo");

        preencherSpinner();

        if(getIntent().hasExtra("codigo")) {

            btnSalvar.setVisibility(View.GONE);
            btnEditar.setVisibility(View.VISIBLE);
            btnExcluir.setVisibility(View.VISIBLE);

            cursorCodigo = crud.buscarById(Integer.parseInt(codigo));

            etNovaCategoria.setText(cursorCodigo.getString(cursorCodigo.getColumnIndexOrThrow(ForcaHelperDao.getNomeCategoria())));
            etPalavraForca.setText(cursorCodigo.getString(cursorCodigo.getColumnIndexOrThrow(ForcaHelperDao.getPALAVRA())));
            dicas[0].setText(cursorCodigo.getString(cursorCodigo.getColumnIndexOrThrow(ForcaHelperDao.getDica1())));
            dicas[1].setText(cursorCodigo.getString(cursorCodigo.getColumnIndexOrThrow(ForcaHelperDao.getDica2())));
            dicas[2].setText(cursorCodigo.getString(cursorCodigo.getColumnIndexOrThrow(ForcaHelperDao.getDica3())));
            dicas[3].setText(cursorCodigo.getString(cursorCodigo.getColumnIndexOrThrow(ForcaHelperDao.getDica4())));
            dicas[4].setText(cursorCodigo.getString(cursorCodigo.getColumnIndexOrThrow(ForcaHelperDao.getDica5())));
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar();
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizar();
            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluir();
            }
        });
    }

    private void salvar() {
        String categoria = "";

        // caso o seletor de categorias esteja vazio, o usuário deve preencher a categoria
        // se existir ago no seletor, o usuário pode selecionar
        if(isCampoVazio(etNovaCategoria)) {
            if( !categorias.isEmpty() ) {
                categoria = spnCategorias.getSelectedItem().toString();
            } else {
                Toast.makeText(CriaDesafioActivity.this, "Não há categorias criadas então você deve preencher a categoria.", Toast.LENGTH_SHORT).show();
            }

        } else {
            categoria = etNovaCategoria.getText().toString();
        }

        // caso o campo forca não esteja vazio, verifia se todas as dicas estão devidamente preenchidas
        if( !isCampoVazio(etPalavraForca) ) {
            if(isNotCamposDeDicasVazio()) {
                salvarDados(categoria);

                Toast.makeText(CriaDesafioActivity.this, "Forca salva com sucesso.", Toast.LENGTH_SHORT).show();

                voltarParaActivity();
            } else {
                Toast.makeText(CriaDesafioActivity.this, "Você deve preencher todas as dicas.", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(CriaDesafioActivity.this, "Você deve preencher a palavra da forca.", Toast.LENGTH_SHORT).show();
        }
    }

    private void atualizar() {
        String categoria = "";
        if(isCampoVazio(etNovaCategoria)) {
            categoria = spnCategorias.getSelectedItem().toString();
        } else {
            categoria = etNovaCategoria.getText().toString();
        }

        if(!isCampoVazio(etPalavraForca)) {
            if(isNotCamposDeDicasVazio()) {
                atualizarDados(categoria);
                Toast.makeText(CriaDesafioActivity.this, "Alterado.", Toast.LENGTH_SHORT).show();

                voltarParaActivity();
            } else {
                Toast.makeText(CriaDesafioActivity.this, "Você deve preencher todas as dicas.", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(CriaDesafioActivity.this, "Você deve preencher a palavra da forca.", Toast.LENGTH_SHORT).show();
        }
    }

    private void excluir() {
        crud.deletar(Integer.parseInt(codigo));
        Toast.makeText(CriaDesafioActivity.this, "Excluído.", Toast.LENGTH_SHORT).show();

        voltarParaActivity();
    }

    private void salvarDados(String categoria) {
        String cat = categoria;
        converterCamposParaString();

        crud.inserir(cat, pal, d1, d2, d3, d4, d5);
    }

    private void atualizarDados(String categoria) {
        String cat = categoria;
        converterCamposParaString();

        crud.alterar(Integer.parseInt(codigo),cat, pal, d1, d2, d3, d4, d5);
    }

    private void converterCamposParaString() {
        pal = etPalavraForca.getText().toString();
        d1 = dicas[0].getText().toString();
        d2 = dicas[1].getText().toString();
        d3 = dicas[2].getText().toString();
        d4 = dicas[3].getText().toString();
        d5 = dicas[4].getText().toString();
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

    private boolean isCampoVazio(EditText campo) {
        return campo.getText().length() <= 0;
    }

    private boolean isNotCamposDeDicasVazio() {
        int contador = 0;

        for (EditText dica : dicas) {
            if (dica.getText().length() > 0) {
                contador++;
            }
        }

        return contador >= dicas.length;
    }

    private void voltarParaActivity() {
        Intent intent = new Intent(CriaDesafioActivity.this, EscolhaNovaForcaActivity.class);

        startActivity(intent);

        finish();
    }
}
