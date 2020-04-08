package ufpb.luis.vitor.advinha.view;


import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;


import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;


import com.squareup.picasso.Picasso;


import java.util.*;



import ufpb.luis.vitor.advinha.R;
import ufpb.luis.vitor.advinha.control.QueueChallenge;

import ufpb.luis.vitor.advinha.model.ChallengeDTO;

public class MainActivity extends Activity {
    private ImageView challenge_image;
    private EditText tentativa;
    private Button btn_confirma;
    private QueueChallenge fila_challenge = new QueueChallenge();





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);


        Intent receber = getIntent();
        ArrayList<ChallengeDTO> tmp = receber.getParcelableArrayListExtra("listinha");



        assert tmp != null;
        if(!tmp.isEmpty()) {
            for (ChallengeDTO c : tmp) {fila_challenge.offer(c);}
        }
            challenge_image = findViewById(R.id.challenge_image);
            tentativa = findViewById(R.id.tentativa);
            btn_confirma = findViewById(R.id.btn_confirma);

            if(!fila_challenge.isEmpty()) {
                    loadImage(fila_challenge.peek().getImageUrl(), challenge_image);
                    btn_confirma.setOnClickListener(v -> {
                        if (acertou(fila_challenge.peek().getWord(), tentativa.getText().toString())) {
                            tentativa.setBackgroundResource(R.drawable.edit_text_correct);
                            fila_challenge.poll();
                                if(!fila_challenge.isEmpty()) {
                                    Intent enviar = new Intent(this, MainActivity.class);
                                    enviar.putParcelableArrayListExtra("listinha", TransformarQueueChallengeIntoArray(fila_challenge));
                                    startActivity(enviar);
                                }else {
                                    Intent winnerScreen = new Intent(this, WinnerScreen.class);
                                    startActivity(winnerScreen);

                                }
                            } else {
                            tentativa.setBackgroundResource(R.drawable.edit_text_incorrect);
                            fila_challenge.ReiniciarChallenge(fila_challenge.element());
                            Intent enviar = new Intent(this, MainActivity.class);
                            enviar.putParcelableArrayListExtra("listinha", (TransformarQueueChallengeIntoArray(fila_challenge)));
                            startActivity(enviar);
                        }//else
                    });//onClickView
                }
    }


    private void loadImage(String url, ImageView view) {
        Picasso.get()
                .load(url)
                .error(R.drawable.erroimage)
                .centerInside()
                .into(view);
    }


    public boolean acertou(String word, String tentativa) {
        if (word.toLowerCase().equals(tentativa.toLowerCase())){
           return true;
        }
       return false;
    }

    public static ArrayList<ChallengeDTO> TransformarQueueChallengeIntoArray (QueueChallenge q){
        ArrayList<ChallengeDTO> tmp = new ArrayList<>();
        for(ChallengeDTO c : q){
            tmp.add(c);
        }
        return tmp;
    }



}

