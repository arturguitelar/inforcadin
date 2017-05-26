package trabalhoandroid.com.br.inforcadin.banco;

/**
 * Created by Artur on 21/05/2017.
 */

public class CriaDados {

    ForcaDao forcaDao;

    public CriaDados(ForcaDao forcaDao) {
        this.forcaDao = forcaDao;
    }

    public void criar() {

        forcaDao.inserir("Filme",
                "Vingadores",
                "Lançado em 2012.",
                "Direção de Joss Whedon.",
                "A eterna luta entre o bem e o mal.",
                "Tem mais de um herói.",
                "Baseado em uma equipe de histórias em quadrinhos.");

        forcaDao.inserir("Filme",
                "O Profissional",
                "Lançado em 1995.",
                "Dirigido por Luc Besson",
                "No filme tem um assassino.",
                "Os vizinhos morrem.",
                "Um dos principais é uma menina.");

        forcaDao.inserir("Filme",
                "O Senhor dos Anéis",
                "Existe uma animação do mesmo tema de 1979.",
                "Não é uma Terra muito grande...",
                "Alguns habitantes são pequenos.",
                "Dirigido por Peter Jackson.",
                "No nome tem muitos mas o filme é sobre um só.");

        forcaDao.inserir("Série",
                "Mr. Robot",
                "É uma série meio dramática.",
                "A primeira temporada tem 10 episódios.",
                "Criada por Sam Esmail.",
                "O protagonista não é muito descolado.",
                "Assuntos Hackers.");

        forcaDao.inserir("Série",
                "Todo mundo odeia o Chris",
                "Conta a história de um famoso.",
                "Tem 4 temporadas.",
                "É sobre uma família.",
                "É de Humor.",
                "As pessoas não gostam muito dele.");

        forcaDao.inserir("Série",
                "Eu a patroa e as crianças",
                "Criada por Damon Wayans e Don Reo.",
                "É de Humor.",
                "Tem 5 temporadas.",
                "Criada em 2001.",
                "É uma sitcom.");

        forcaDao.inserir("Quadrinho",
                "Wolverine",
                "Ele é um pouco nervoso.",
                "Seus ossos devem ser pesados.",
                "Primeira aparição foi brigando contra um cara bem forte.",
                "Faz parte de um grupo de heróis.",
                "Se cura muito rápido.");

        forcaDao.inserir("Quadrinho",
                "Homem-Aranha",
                "Gosta de pular de um lado para o outro.",
                "Gosta de fazer piadas.",
                "Tem vermelho no uniforme.",
                "Sua primeira aparição foi em Amazing Fantasy nº15.",
                "Seu nome do meio é Benjamin, quem diria hein?");

        forcaDao.inserir("Quadrinho",
                "Batman",
                "Gosta de botar medo.",
                "Inteligente até demais.",
                "Sabe várias artes-marciais.",
                "Às vezes é um tanto arrogante.",
                "Tem uma fundação com seu nome.");
    }
}
