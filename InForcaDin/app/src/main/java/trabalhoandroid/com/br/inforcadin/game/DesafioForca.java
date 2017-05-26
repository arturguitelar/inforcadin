package trabalhoandroid.com.br.inforcadin.game;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artur on 18/05/2017.
 */

public class DesafioForca implements Parcelable {
    private String categoria;
    private String palavra;
    private List<String> dicas;

    public DesafioForca(String categoria) {
        setCategoria(categoria);
    }

    protected DesafioForca(Parcel in) {
        categoria = in.readString();
        palavra = in.readString();
        dicas = in.createStringArrayList();
    }

    public static final Creator<DesafioForca> CREATOR = new Creator<DesafioForca>() {
        @Override
        public DesafioForca createFromParcel(Parcel in) {
            return new DesafioForca(in);
        }

        @Override
        public DesafioForca[] newArray(int size) {
            return new DesafioForca[size];
        }
    };

    public String getCategoria() {
        return categoria;
    }

    private void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public List<String> getDicas() {
        return dicas;
    }

    public void setDicas(List<String> dicas) {
        this.dicas = dicas;
    }

    public List<String> getArrayPalavraFormatado() {
        List<String> palavraArray = new ArrayList<>();

        for(int i = 0; i < getPalavra().length(); i++) {
            palavraArray.add("" + getPalavra().toUpperCase().charAt(i));
        }

        return palavraArray;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DesafioForca desafioForca = (DesafioForca) o;

        if (!categoria.equals(desafioForca.categoria)) return false;
        if (palavra != null ? !palavra.equals(desafioForca.palavra) : desafioForca.palavra != null)
            return false;
        return dicas != null ? dicas.equals(desafioForca.dicas) : desafioForca.dicas == null;

    }

    @Override
    public int hashCode() {
        int result = categoria.hashCode();
        result = 31 * result + (palavra != null ? palavra.hashCode() : 0);
        result = 31 * result + (dicas != null ? dicas.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(categoria);
        dest.writeString(palavra);
        dest.writeStringList(dicas);
    }
}
