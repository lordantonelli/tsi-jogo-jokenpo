package com.example.navegaoactivities.jokenpo;

public enum Jokenpo {
    PEDRA(1, R.id.imgPedra, R.drawable.pedra),
    PAPEL(2, R.id.imgPapel, R.drawable.papel),
    TESOURA(3, R.id.imgTesoura, R.drawable.tesoura);

    private int opcao;
    private int id;
    private int imagem;

    Jokenpo(int opcao, int id, int imagem) {
        this.opcao = opcao;
        this.id = id;
        this.imagem = imagem;
    }

    public int getOpcao() {
        return this.opcao;
    }

    public int getImagem() {
        return this.imagem;
    }

    public static Jokenpo fromId(int id) {
        Jokenpo[] opcoes = Jokenpo.values();
        for(int i = 0; i < opcoes.length; i++) {
            if(opcoes[i].id == id) {
                return opcoes[i];
            }
        }
        return null;
    }
}
