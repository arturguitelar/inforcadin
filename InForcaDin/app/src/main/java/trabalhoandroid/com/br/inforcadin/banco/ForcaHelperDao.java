package trabalhoandroid.com.br.inforcadin.banco;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Artur on 20/05/2017.
 */

public class ForcaHelperDao extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "inforcadin.db";
    private static final String ID = "_id";
    private static final int VERSAO = 1;

    private static final String TABELA = "forca";
    private static final String NOME_CATEGORIA = "nome_categoria";
    private static final String PALAVRA = "palavra";
    private static final String DICA_1 = "dica_1";
    private static final String DICA_2 = "dica_2";
    private static final String DICA_3 = "dica_3";
    private static final String DICA_4 = "dica_4";
    private static final String DICA_5 = "dica_5";

    public ForcaHelperDao(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE " + TABELA + "("
                + ID + " integer primary key autoincrement, "
                + NOME_CATEGORIA + " text,"
                + PALAVRA + " text,"
                + DICA_1 + " text,"
                + DICA_2 + " text,"
                + DICA_3 + " text,"
                + DICA_4 + " text,"
                + DICA_5 + " text"
                + ")";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA);
        onCreate(db);
    }

    public static String getID() {
        return ID;
    }

    public static String getTABELA() {
        return TABELA;
    }

    public static String getNomeCategoria() {
        return NOME_CATEGORIA;
    }

    public static String getPALAVRA() {
        return PALAVRA;
    }

    public static String getDica1() {
        return DICA_1;
    }

    public static String getDica2() {
        return DICA_2;
    }

    public static String getDica3() {
        return DICA_3;
    }

    public static String getDica4() {
        return DICA_4;
    }

    public static String getDica5() {
        return DICA_5;
    }
}