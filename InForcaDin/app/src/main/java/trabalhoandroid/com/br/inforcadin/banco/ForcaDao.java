package trabalhoandroid.com.br.inforcadin.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Artur on 20/05/2017.
 */

public class ForcaDao {
    private SQLiteDatabase db;
    private ForcaHelperDao banco;

    public ForcaDao(Context context) {
        banco = new ForcaHelperDao(context);
    }

    public String inserir(String categoria, String palavra,
                          String dica1, String dica2, String dica3,
                          String dica4, String dica5) {
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();

        valores.put(ForcaHelperDao.getNomeCategoria(), categoria);
        valores.put(ForcaHelperDao.getPALAVRA(), palavra);
        valores.put(ForcaHelperDao.getDica1(), dica1);
        valores.put(ForcaHelperDao.getDica2(), dica2);
        valores.put(ForcaHelperDao.getDica3(), dica3);
        valores.put(ForcaHelperDao.getDica4(), dica4);
        valores.put(ForcaHelperDao.getDica5(), dica5);

        resultado = db.insert(ForcaHelperDao.getTABELA(), null, valores);

        db.close();

        if(resultado == -1)
            return "Erro ao inserir registro.";
        else
            return "Registro inserido com sucesso!";
    }

    public Cursor buscar() {
        Cursor cursor;
        String[] campos = {ForcaHelperDao.getID(), ForcaHelperDao.getNomeCategoria(), ForcaHelperDao.getPALAVRA()};

        db = banco.getReadableDatabase();

        cursor = db.query(ForcaHelperDao.getTABELA(), campos, null, null, null, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();

        db.close();

        return cursor;
    }

    public Cursor buscarById(int id) {
        Cursor cursor;
        String[] campos = {ForcaHelperDao.getID(), ForcaHelperDao.getNomeCategoria(),
                ForcaHelperDao.getPALAVRA(), ForcaHelperDao.getDica1(), ForcaHelperDao.getDica2(),
                ForcaHelperDao.getDica3(), ForcaHelperDao.getDica4(), ForcaHelperDao.getDica5()
        };

        String where = ForcaHelperDao.getID() + " = " + id;

        db = banco.getReadableDatabase();
        cursor = db.query(ForcaHelperDao.getTABELA(), campos, where, null, null, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();

        db.close();

        return cursor;
    }

    public Cursor buscarByCategoriaRandom(String categoria) {

        String sql = "select * from " + ForcaHelperDao.getTABELA() + " where " + ForcaHelperDao.getNomeCategoria()
                + " = '" + categoria + "' order by random() limit 1";

        db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor != null)
            cursor.moveToFirst();

        db.close();

        return cursor;
    }

    public void alterar(int id, String categoria, String palavra,
                        String dica1, String dica2, String dica3,
                        String dica4, String dica5) {
        ContentValues valores;
        String where;

        db = banco.getWritableDatabase();

        where = ForcaHelperDao.getID() + " = " + id;

        valores = new ContentValues();
        valores.put(ForcaHelperDao.getNomeCategoria(), categoria);
        valores.put(ForcaHelperDao.getPALAVRA(), palavra);
        valores.put(ForcaHelperDao.getDica1(), dica1);
        valores.put(ForcaHelperDao.getDica1(), dica2);
        valores.put(ForcaHelperDao.getDica1(), dica3);
        valores.put(ForcaHelperDao.getDica1(), dica4);
        valores.put(ForcaHelperDao.getDica1(), dica5);

        db.update(ForcaHelperDao.getTABELA(), valores, where, null);
        db.close();
    }

    public void deletar(int id) {
        String where = ForcaHelperDao.getID() + " = " + id;
        db = banco.getReadableDatabase();

        db.delete(ForcaHelperDao.getTABELA(), where, null);
        db.close();
    }

}
