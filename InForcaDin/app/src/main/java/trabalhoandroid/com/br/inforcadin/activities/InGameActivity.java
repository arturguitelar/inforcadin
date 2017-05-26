package trabalhoandroid.com.br.inforcadin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

import trabalhoandroid.com.br.inforcadin.R;
import trabalhoandroid.com.br.inforcadin.game.DesafioForca;

/**
 * Created by Artur on 21/05/2017.
 */

public class InGameActivity extends AppCompatActivity {
    // elementos do layout
    ImageView imgForca;
    TextView txtCategorias, txtPalavraForca, txtLetrasErradas;
    Spinner spnEscolherLetra;
    Button btnConfirmarLetra;
    ListView lvDicas;

    // demais propriedades
    int contadorLetrasErradas;
    String letraEscolhida, msgPlacarErro, msgPlacarErroNumeros;
    List<String> listaLetrasValidas, palavraForcaArrayList, placeholderArrayList, listaLetrasErradas, listaDicas;
    ArrayAdapter<String> adapterDicas;

    DesafioForca desafioForca;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingame);

        // pegando os elementos do layout
        imgForca = (ImageView) findViewById(R.id.imgForca);
        txtCategorias = (TextView) findViewById(R.id.txtCategorias);
        txtPalavraForca = (TextView) findViewById(R.id.txtPalavraForca);
        txtLetrasErradas = (TextView) findViewById(R.id.txtLetrasErradas);
        spnEscolherLetra = (Spinner) findViewById(R.id.spnEscolherLetra);
        btnConfirmarLetra = (Button) findViewById(R.id.btnConfirmarLetra);
        lvDicas = (ListView) findViewById(R.id.lvDicas);

        // pegando os dados necessários passados pela intent
        getElementosParaCriarForca();

        // inicializando variáveis de contagem, letras e listas
        contadorLetrasErradas = 0;
        listaLetrasValidas = listarLetrasValidas();
        palavraForcaArrayList = desafioForca.getArrayPalavraFormatado();
        msgPlacarErro = "Letras erradas: ";
        msgPlacarErroNumeros = "(0/6) ";

        // criando array para letras já erradas
        listaLetrasErradas = new ArrayList<>();

        // preenchendo os campos necessários
        txtCategorias.setText("Categoria: " + desafioForca.getCategoria());
        txtPalavraForca.setText(transformarPalavraPrimeiraContagem(palavraForcaArrayList));
        txtLetrasErradas.setText(msgPlacarErroNumeros + msgPlacarErro);

        // tratando do preenchimento do spinner
        spnEscolherLetra.setAdapter(preencherSpinner(listaLetrasValidas));

        // setando uma nova lista para armazenar as dicas
        listaDicas = new ArrayList<>();

        // setando adaptador para a lista de dicas
        adapterDicas = new ArrayAdapter<String>(InGameActivity.this,
                android.R.layout.simple_list_item_1, android.R.id.text1, listaDicas);
        lvDicas.setAdapter(adapterDicas);

        btnConfirmarLetra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jogo();
            }
        });
    }

    private void getElementosParaCriarForca() {
        Intent it = getIntent();

        this.desafioForca = it.getParcelableExtra("forca");
    }

    // lógica do jogo em si
    private void jogo() {
        this.letraEscolhida = spnEscolherLetra.getSelectedItem().toString();

        if(isLetraNaPalavra(letraEscolhida, palavraForcaArrayList)) {
            acertarLetra(letraEscolhida);

            if(isListasIguais(placeholderArrayList, palavraForcaArrayList)) {
                fimDeJogo("vitoria");
            }
        } else {
            if(contadorLetrasErradas >= 5)
                fimDeJogo("derrota");

            erraLetra(letraEscolhida);
        }
    }

    private String transformarPalavraPrimeiraContagem(List<String> palavraArray) {
        // aqui guarda a quantidade de espaços para usar depois
        this.placeholderArrayList = new ArrayList<String>();

        String temp = "";
        for(String letra : palavraArray) {
            if(getListagemCaracteresInvalidos().contains(letra)) {
                this.placeholderArrayList.add(letra);

                temp += letra + " ";

            } else {
                this.placeholderArrayList.add("._.");

                temp += "._. ";
            }
        }

        return temp;
    }

    private boolean isLetraNaPalavra(String letra, List<String> palavra) {
        if(palavra.contains(letra))
            return true;

        return false;
    }

    private void acertarLetra(String letraEscolhida) {
        // verificando se a letra já foi usada antes
        if(isLetraUsada(letraEscolhida)) {
            Toast.makeText(InGameActivity.this, "Você já selecionou esta letra", Toast.LENGTH_SHORT).show();
            return;
        }

        String novaPalavra = "";

        for(int i = 0; i < this.palavraForcaArrayList.size(); i++) {
            if (this.palavraForcaArrayList.get(i).equals(letraEscolhida)) {
                this.placeholderArrayList.set(i, letraEscolhida);
            }
        }

        for(String letra : this.placeholderArrayList)
            novaPalavra += letra + " ";

        this.txtPalavraForca.setText(novaPalavra);

        Toast.makeText(InGameActivity.this, "Você acertou!", Toast.LENGTH_SHORT).show();
    }

    private boolean isListasIguais(List<String> listaA, List<String> listaB) {
        return listaA.equals(listaB);
    }

    private void erraLetra(String letraEscolhida) {
        // verificando se a letra já foi usada antes
        if(isLetraUsada(letraEscolhida)) {
            Toast.makeText(InGameActivity.this, "Você já selecionou esta letra", Toast.LENGTH_SHORT).show();
            return;
        }

        atualizarPlacarErro(letraEscolhida);
        atualizarImagemForca();
        atualizarDicas();

        Toast.makeText(InGameActivity.this, "Você errou!", Toast.LENGTH_SHORT).show();
    }

    private boolean isLetraUsada(String letra) {
        if(this.listaLetrasErradas.contains(letra)) {
            return true;
        }

        listaLetrasErradas.add(letra);

        return false;
    }

    private void atualizarPlacarErro(String letra) {
        this.contadorLetrasErradas++;

        this.msgPlacarErro += letra + " ";

        this.msgPlacarErroNumeros = "(" + contadorLetrasErradas + "/6) ";

        this.txtLetrasErradas.setText(msgPlacarErroNumeros + msgPlacarErro);
    }

    private void atualizarImagemForca() {
        switch (contadorLetrasErradas) {
            case 1 :
                imgForca.setImageResource(R.drawable.boneco_forca_02);
                break;

            case 2 :
                imgForca.setImageResource(R.drawable.boneco_forca_03);
                break;

            case 3 :
                imgForca.setImageResource(R.drawable.boneco_forca_04);
                break;

            case 4 :
                imgForca.setImageResource(R.drawable.boneco_forca_05);
                break;

            case 5 :
                imgForca.setImageResource(R.drawable.boneco_forca_06);
                break;

            default:
                break;
        }
    }

    private void atualizarDicas() {

        switch (contadorLetrasErradas) {
            case 1 :
                listaDicas.add(desafioForca.getDicas().get(0));
                adapterDicas.notifyDataSetChanged();
                break;

            case 2 :
                listaDicas.add(desafioForca.getDicas().get(1));
                adapterDicas.notifyDataSetChanged();
                break;

            case 3 :
                listaDicas.add(desafioForca.getDicas().get(2));
                adapterDicas.notifyDataSetChanged();
                break;

            case 4 :
                listaDicas.add(desafioForca.getDicas().get(3));
                adapterDicas.notifyDataSetChanged();
                break;

            case 5 :
                listaDicas.add(desafioForca.getDicas().get(4));
                adapterDicas.notifyDataSetChanged();
                break;

            default:
                break;
        }
    }

    // trata do preenchimento de listas de letras válidas e inválidas
    private List<String> listarLetrasValidas() {
        String[] validos = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
                "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "Á", "À", "Ã", "Â", "É", "Ê", "Í", "Ó", "Ô", "Ú", "Ü", "Ç"};

        List<String> lista = arrayParaList(validos);

        return lista;
    }

    private List<String> getListagemCaracteresInvalidos() {
        String[] invalidos = {" ", "'", "\"", "!", "?", "@", "#", "$", "%", "¨",
                                "&", "+", "-", "*", "/", "^", "~", "´", "`",
                                "(", ")", "{", "}", "[", "]", ",", ".", ":", ";",
                                "|", "\\", "/", "ª", "º", "°", "="};

        List<String> lista = arrayParaList(invalidos);

        return lista;
    }

    private List<String> arrayParaList(String[] arrayStrings) {
        List<String> lista = new ArrayList<>();

        for(int i = 0; i < arrayStrings.length; i++)
            lista.add(arrayStrings[i]);

        return lista;
    }

    // trata de preencher o spinner na inicialização
    private ArrayAdapter<String> preencherSpinner(List<String> lista) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, lista);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return adapter;
    }

    // tratando dos fins do jogo
    private void fimDeJogo(String resultado) {
        Intent it;
        if(resultado == "vitoria") {
            it = new Intent(InGameActivity.this, VitoriaActivity.class);
            startActivity(it);
        } else {
            it = new Intent(InGameActivity.this, DerrotaActivity.class);
            startActivity(it);
        }

        finish();
    }

}
