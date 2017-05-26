package trabalhoandroid.com.br.inforcadin.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import trabalhoandroid.com.br.inforcadin.R;
import trabalhoandroid.com.br.inforcadin.banco.ForcaDao;
import trabalhoandroid.com.br.inforcadin.banco.ForcaHelperDao;

/**
 * Created by Artur on 21/05/2017.
 */

public class ListaForcasAcivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_forcas);

        ForcaDao crud = new ForcaDao(getBaseContext());
        final Cursor cursor = crud.buscar();

        String[] nomeCampos = new String[] {ForcaHelperDao.getNomeCategoria(), ForcaHelperDao.getPALAVRA()};
        int[] idViews = new int[] {R.id.txtCategoriaLV, R.id.txtPalavraLV};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(getBaseContext(),
                R.layout.lista_de_forcas, cursor, nomeCampos, idViews, 0);

        ListView lista = (ListView) findViewById(R.id.lvBuscar);
        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String codigo;
                cursor.moveToPosition(position);
                codigo = cursor.getString(cursor.getColumnIndexOrThrow(ForcaHelperDao.getID()));

                Intent intent = new Intent(ListaForcasAcivity.this, CriaDesafioActivity.class);
                intent.putExtra("codigo", codigo);

                startActivity(intent);

                finish();
            }
        });
    }
}
