package ufpb.luis.vitor.advinha.view;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import java.util.*;

import com.plattysoft.leonids.ParticleSystem;

import ufpb.luis.vitor.advinha.R;

import ufpb.luis.vitor.advinha.model.Challenge;
import ufpb.luis.vitor.advinha.utils.QueueChallenge;
import ufpb.luis.vitor.advinha.utils.ConfigurationLogger;

public class MainActivity extends Activity  {
    private EditText tentativa;
    private Context context = this;
    private QueueChallenge fila_challenge = new QueueChallenge();
    private boolean turnKeyboardInvisible ;
    private TextToSpeech tts;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);
        //Inicialização dos Componentes
        Toolbar toolbar = findViewById(R.id.main_activity_toolbar);
        View keyboard_layout = findViewById(R.id.keyboard_layout);
        tentativa = findViewById(R.id.tentativa);
        tentativa.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        ImageView challenge_image = findViewById(R.id.challenge_image);
        Button btn_confirma = findViewById(R.id.btn_confirma);
        Vibrator vibro = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        //


            try {
                boolean[] teclado = new ConfigurationLogger(this).readConfig();
                if (teclado[1]) {
                    tentativa.setFocusable(true);
                    this.turnKeyboardInvisible = true;
                    keyboard_layout.setVisibility(View.INVISIBLE);
                } else {
                    tentativa.setFocusable(false);
                    this.turnKeyboardInvisible = false;
                    keyboard_layout.setVisibility(View.VISIBLE);
                }
            } catch (IOException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }

            toolbar.setNavigationOnClickListener(v -> {
            Intent voltar = new Intent(this,ContextChoose.class);
            startActivity(voltar);
        });
            toolbar.setOnMenuItemClickListener(item -> {
             // 2º @param   ---> classe anonima
                tts = new TextToSpeech(context, status -> {
                    if(status == TextToSpeech.SUCCESS) {
                        int result = tts.setLanguage(Locale.getDefault());

                        if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                            Toast.makeText(context,"LANG NOT SUPPORTED",Toast.LENGTH_SHORT).show();
                        }else{
                            tts.speak(fila_challenge.peek().getWord(),TextToSpeech.QUEUE_FLUSH,null);

                        }
                    }else{Toast.makeText(context,"TextToSpeech_Not_Sucess",Toast.LENGTH_SHORT).show(); }});
             return true;
         });

        Intent receber = getIntent();
        ArrayList<Challenge> tmp = receber.getParcelableArrayListExtra("listinha");
        if(!tmp.isEmpty()) {
            for (Challenge c : tmp) {fila_challenge.offer(c);}
        }

        if(!fila_challenge.isEmpty()) {
            loadImage(fila_challenge.peek().getImageUrl(), challenge_image);
            btn_confirma.setOnClickListener(v -> {
                    if(!tentativa.getText().toString().isEmpty()) {
                        if (acertou(fila_challenge.peek().getWord(), tentativa.getText().toString())) {
                            tentativa.setBackgroundResource(R.drawable.edit_text_correct);
                            fila_challenge.poll();
                            if (!fila_challenge.isEmpty()) {
                                confetes();
                                vibro.vibrate(400);
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        Intent enviar = new Intent(context, MainActivity.class);
                                        enviar.putParcelableArrayListExtra("listinha", TransformarQueueChallengeIntoArray(fila_challenge));
                                        startActivity(enviar);
                                    }
                                }, 4000);

                            } else {
                                confetes();
                                vibro.vibrate(400);
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        Intent winnerScreen = new Intent(context, WinnerScreen.class);
                                        startActivity(winnerScreen);
                                    }
                                }, 4000);

                            }
                        } else {
                            tentativa.setBackgroundResource(R.drawable.edit_text_incorrect);
                            vibro.vibrate(600);
                            tts = new TextToSpeech(context, status -> {
                                if (status == TextToSpeech.SUCCESS) {
                                    int result = tts.setLanguage(Locale.getDefault());

                                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                        Toast.makeText(context, "LANG NOT SUPPORTED", Toast.LENGTH_SHORT).show();
                                    } else {
                                        tts.speak("A palavra deveria ser : " + fila_challenge.peek().getWord(), TextToSpeech.QUEUE_FLUSH, null);

                                    }
                                } else {
                                    Toast.makeText(context, "TextToSpeech_Not_Sucess", Toast.LENGTH_SHORT).show();
                                }
                            });
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    fila_challenge.ReiniciarChallenge(fila_challenge.element());
                                    Intent enviar = new Intent(context, MainActivity.class);
                                    enviar.putParcelableArrayListExtra("listinha", (TransformarQueueChallengeIntoArray(fila_challenge)));
                                    startActivity(enviar);
                                }
                            }, 4000);

                        }
                    }else{
                        Toast.makeText(context,"Tente adivinhar a palavra",Toast.LENGTH_SHORT).show();
                    }//else
            });//onClickView
        }

    }

    private void loadImage(String url, ImageView view) {
        Picasso.get()
                .load(url)
                .into(view);
    }

    private boolean acertou(String word, String tentativa) {
        if (word.toLowerCase().trim().equals(tentativa.toLowerCase().trim())){
           return true;
        }
       return false;
    }

    private static ArrayList<Challenge> TransformarQueueChallengeIntoArray (QueueChallenge q){
        ArrayList<Challenge> tmp = new ArrayList<>();
        tmp.addAll(q);
        return tmp;
    }

    public void textFill (View v){

            String str = "";

            switch (v.getId()){

                case R.id.btnA:
                    str = tentativa.getText()+"a";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;
                case R.id.btnB:
                    str = tentativa.getText()+"b";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;
                case R.id.btnC:
                    str = tentativa.getText()+"c";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;

                case R.id.btnD:
                    str = tentativa.getText()+"d";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;

                case R.id.btnE:
                    str = tentativa.getText()+"e";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;

                case R.id.btnF:
                    str = tentativa.getText()+"f";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;

                case R.id.btnG:
                    str = tentativa.getText()+"g";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;

                case R.id.btnH:
                    str = tentativa.getText()+"h";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;

                case R.id.btnI:
                    str = tentativa.getText()+"i";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;

                case R.id.btnJ:
                    str = tentativa.getText()+"j";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;

                case R.id.btnK:
                    str = tentativa.getText()+"k";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;

                case R.id.btnL:
                    str = tentativa.getText()+"l";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;

                case R.id.btnM:
                    str = tentativa.getText()+"m";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;

                case R.id.btnN:
                    str = tentativa.getText()+"n";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;

                case R.id.btnO:
                    str = tentativa.getText()+"o";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;

                case R.id.btnP:
                    str = tentativa.getText()+"p";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;

                case R.id.btnQ:
                    str = tentativa.getText()+"q";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;

                case R.id.btnR:
                    str = tentativa.getText()+"r";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;

                case R.id.btnS:
                    str = tentativa.getText()+"s";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;

                case R.id.btnT:
                    str = tentativa.getText()+"t";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;

                case R.id.btnU:
                    str = tentativa.getText()+"u";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;

                case R.id.btnV:
                    str = tentativa.getText()+"v";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;

                case R.id.btnW:
                    str = tentativa.getText()+"w";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;

                case R.id.btnX:
                    str = tentativa.getText()+"x";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;

                case R.id.btnY:
                    str = tentativa.getText()+"y";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;

                case R.id.btnZ:
                    str = tentativa.getText()+"z";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;
                case R.id.btnBackSpace:
                    if(tentativa.getText().length() == 0){
                        str = "";
                    }else{
                        str = tentativa.getText().subSequence(0,tentativa.getText().length()-1).toString();
                    }
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;
                case R.id.btnSpace:
                    str = tentativa.getText()+" ";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;
                case R.id.btnAacute:
                    str = tentativa.getText()+"á";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;
                case R.id.btnAcircum:
                    str = tentativa.getText()+"â";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;
                case R.id.btnAtilde:
                    str = tentativa.getText()+"ã";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;
                case R.id.btnEacute:
                    str = tentativa.getText()+"é";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;
                case R.id.btnEcircum:
                    str = tentativa.getText()+"ê";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;
                case R.id.btnIacute:
                    str = tentativa.getText()+"í";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;
                case R.id.btnOacute:
                    str = tentativa.getText()+"ó";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;
                case R.id.btnOcircum:
                    str = tentativa.getText()+"ô";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;
                case R.id.btnOtilde:
                    str = tentativa.getText()+"õ";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;
                case R.id.btnUacute:
                    str = tentativa.getText()+"ú";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;
                case R.id.btnCcedilha:
                    str = tentativa.getText()+"ç";
                    tentativa.setText(str, TextView.BufferType.EDITABLE);
                    break;
            }
    }

    public void confetes(){
        View viewLeft = findViewById(R.id.emiter_top_left);
        View viewRight = findViewById(R.id.emiter_top_right);
        View viewCenterRight = findViewById(R.id.emiter_center);

        new ParticleSystem(this, 80, R.drawable.confeti2, 10000)
                .setSpeedModuleAndAngleRange(0f, 0.1f, 0, 0)
                .setRotationSpeed(144)
                .setAcceleration(0.00005f, 90)
                .emit(viewLeft, 8);

        new ParticleSystem(this, 80, R.drawable.confeti3, 10000)
                .setSpeedModuleAndAngleRange(0f, 0.1f, 180, 180)
                .setRotationSpeed(144)
                .setAcceleration(0.00005f, 90)
                .emit(viewRight, 8);
        new ParticleSystem(this, 80, R.drawable.confeti2, 10000)
                .setSpeedModuleAndAngleRange(0f, 0.1f, 0, 180)
                .setRotationSpeed(144)
                .setAcceleration(0.00005f, 90)
                .emit(viewCenterRight, 8);
    }




}

