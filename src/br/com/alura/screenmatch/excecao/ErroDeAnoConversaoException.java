package br.com.alura.screenmatch.excecao;

public class ErroDeAnoConversaoException extends RuntimeException {

    private String mensagem;

    public ErroDeAnoConversaoException(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return this.mensagem;
    }
}
