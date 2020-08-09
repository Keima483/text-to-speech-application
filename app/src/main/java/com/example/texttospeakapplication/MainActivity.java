package com.example.texttospeakapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech textToSpeech ;
    private EditText text ;
    private SeekBar voicePitch;
    private SeekBar voiceSpeed;
    private Button speak ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speak= findViewById(R.id.speakButton) ;

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {

                    int result = textToSpeech.setLanguage(Locale.ENGLISH) ;
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)  {
                        Log.e("TTS", "Language not supported: ", null );
                    } else {
                        speak.setEnabled(true);
                    }

                } else {
                    Log.e("TTS", "Initialisation error: ", null );
                }
            }
        });

        text = findViewById(R.id.userText) ;
        voicePitch = findViewById(R.id.pitchRate) ;
        voiceSpeed = findViewById(R.id.speechRate) ;

        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakTheText() ;
            }
        });
    }


    private void speakTheText() {

        String textToSpeak = text.getText().toString() ;
        float pitch = (float) voicePitch.getProgress() / 50 ;
        if (pitch < 0.1) {
            pitch = 0.1F ;
        }
        float speed = (float) voiceSpeed.getProgress() / 50 ;
        if (speed < 0.1) {
            speed = 0.1F ;
        }

        textToSpeech.setPitch(pitch) ;
        textToSpeech.setSpeechRate(speed) ;

        textToSpeech.speak(textToSpeak , TextToSpeech.QUEUE_FLUSH , null) ;
    }

    @Override
    protected void onDestroy() {

        if (textToSpeech != null ) {
            textToSpeech.stop() ;
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}