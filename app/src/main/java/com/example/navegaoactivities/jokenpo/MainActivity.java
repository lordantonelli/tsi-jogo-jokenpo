package com.example.navegaoactivities.jokenpo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView imgPedra;
    private ImageView imgPapel;
    private ImageView imgTesoura;
    private ImageView imgJogador;
    private ImageView imgComputador;
    private TextView lblResultado;
    private Button btnJogar;

    private Jokenpo escolhaUsuario;
    private Jokenpo escolhaComputador;

    enum Jokenpo {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.imgPedra = findViewById(R.id.imgPedra);
        this.imgPapel = findViewById(R.id.imgPapel);
        this.imgTesoura = findViewById(R.id.imgTesoura);
        this.imgJogador = findViewById(R.id.imgJogador);
        this.imgComputador = findViewById(R.id.imgComputador);
        this.lblResultado = findViewById(R.id.lblResultado);
        this.btnJogar = findViewById(R.id.btnJogar);

        this.imgPedra.setOnClickListener(new EscutadorClickImagem());
        this.imgPapel.setOnClickListener(new EscutadorClickImagem());
        this.imgTesoura.setOnClickListener(new EscutadorClickImagem());

        this.btnJogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                escolhaUsuario = null;
                alterarVisibilidadeImagens(true);
                imgJogador.setImageResource(R.drawable.vazio);
                imgComputador.setImageResource(R.drawable.vazio);
                btnJogar.setVisibility(View.INVISIBLE);
                lblResultado.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void alterarVisibilidadeImagens(boolean isVisivel) {
        float alpha = isVisivel ? 1.0F : 0.3F;
        this.imgPedra.setAlpha(alpha);
        this.imgPapel.setAlpha(alpha);
        this.imgTesoura.setAlpha(alpha);
    }

    private class EscutadorClickImagem implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (escolhaUsuario == null) {
                escolhaUsuario = Jokenpo.fromId(view.getId());
                imgJogador.setImageResource(escolhaUsuario.getImagem());
                alterarVisibilidadeImagens(false);
                sortearImagem();
                btnJogar.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(getApplicationContext(), "Você já escolheu uma opção!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sortearImagem() {
        Jokenpo[] opcoes = Jokenpo.values();
            long timestapInicial = new Date().getTime();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    int rnd = new Random().nextInt(opcoes.length);
                    imgComputador.setImageResource(opcoes[rnd].getImagem());
                    escolhaComputador = opcoes[rnd];
                    long timestapAtual = new Date().getTime();
                    if (timestapAtual - timestapInicial < 5000) {
                        handler.postDelayed(this, 150);
                    } else {
                        processarResultado();
                    }
                }
            }, 150);
    }

    private void processarResultado() {
        // 1 mod 3 = 1 + 1 -> 2 ---> Se usuario == 1 && computador == 2 Então computador ganha (usuario Pedra VS computador Papel)
        // 2 mod 3 = 2 + 1 -> 3 ---> Se usuario == 2 && computador == 3 Então computador ganha (usuario Papel VS computador Tesoura)
        // 3 mod 3 = 0 + 1 -> 1 ---> Se usuario == 3 && computador == 1 Então computador ganha (usuario Tesoura computador j2 Pedra)
        if (this.escolhaUsuario.getOpcao() % 3 + 1 == this.escolhaComputador.getOpcao())
            this.lblResultado.setText("O computador ganhou!!!!");
        else if (this.escolhaComputador.getOpcao() % 3 + 1 == this.escolhaUsuario.getOpcao())
            this.lblResultado.setText("Você ganhou!!!!");
        else {
            this.lblResultado.setText("Jogo empatado!");
        }
        this.lblResultado.setVisibility(View.VISIBLE);
    }
}