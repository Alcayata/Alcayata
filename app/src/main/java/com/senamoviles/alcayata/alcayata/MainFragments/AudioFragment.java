package com.senamoviles.alcayata.alcayata.MainFragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.senamoviles.alcayata.alcayata.R;

import static android.content.Context.SENSOR_SERVICE;
import static com.senamoviles.alcayata.alcayata.MainActivity.opcion;

/**
 * A simple {@link Fragment} subclass.
 */
public class AudioFragment extends DialogFragment implements  View.OnClickListener, Runnable, SensorEventListener {

    SensorManager sensorManager;
    Sensor sensor;
    AudioManager audioManager;
    MediaPlayer mediaPlayer;
    ImageButton img_btn_play, img_btn_pause, img_btn_stop;
    SeekBar soundseekBar;
    TextView textview;
    Handler handler = new Handler();
    String paso;
    boolean isHeadsetOn;
    BroadcastReceiver broadReceiver;
    Context context;
    IntentFilter intentFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);






    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_audio, container, false);

        context = getActivity().getApplicationContext();
        img_btn_play = (ImageButton) view.findViewById(R.id.img_btn_play);
        img_btn_play.setOnClickListener(this);
        soundseekBar = (SeekBar) view.findViewById(R.id.soundSeekBar);
        sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        broadReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(audioManager.ACTION_AUDIO_BECOMING_NOISY)) {
                    mediaPlayer.pause();
                }
            }
        };



        switch (opcion) {
            case "San Juan Evangelista":
                mediaPlayer = MediaPlayer.create(getContext(), R.raw.sabias_juan);
                break;
            case "El Crucifijo":
                mediaPlayer = MediaPlayer.create(getContext(), R.raw.sabias_crucifijo);
                break;
            case "Virgen de los Dolores":
                mediaPlayer = MediaPlayer.create(getContext(), R.raw.sabias_virgen);
                break;
            case "El Señor del Huerto":
                mediaPlayer = MediaPlayer.create(getContext(), R.raw.sabias_huerto);
                break;
        }


        this.run();

        handler = new Handler();

        soundseekBar.setMax(mediaPlayer.getDuration());

        soundseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean input) {

                seekBar.setMax(mediaPlayer.getDuration() / 1000);

                if (input) {
                    mediaPlayer.seekTo(progress * 1000);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return view;
    }






    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.img_btn_play:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();


                } else {
                    if(audioManager.isWiredHeadsetOn()){
                        mediaPlayer.start();
                        img_btn_play.setBackgroundResource(R.drawable.pause);
                    }
                    else{

                        //Toast.makeText(getActivity(), "Conecta los audifonos para reproducir el audio", Toast.LENGTH_SHORT).show();
                        Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.viewSnack),"Conecta los audifonos para reproducir el audio",Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }
                break;
        }
    }







    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
        audioManager.setMode(AudioManager.MODE_IN_CALL);

    }

    @Override
    public void run() {
        if (mediaPlayer != null) {
            int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
            soundseekBar.setProgress(mCurrentPosition);
        }
        handler.postDelayed(this, 1000);
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            // unregisterReceiver(mReciever);
            sensorManager.unregisterListener(this);
            context.unregisterReceiver(broadReceiver);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(mediaPlayer.isPlaying()) {
            int mediaPlayer_position = mediaPlayer.getCurrentPosition();
            getActivity().getSharedPreferences("PLAY_PAUSE", Context.MODE_PRIVATE).edit().putInt("CHECK_PLAY_PAUSE", mediaPlayer_position).apply();
            mediaPlayer.pause();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.e("distance", String.valueOf(event.values[0]));
        Log.e("MaximumRange", String.valueOf(sensor.getMaximumRange()));
        audioManager.setMode(AudioManager.MODE_IN_CALL);

        if (event.values[0] < sensor.getMaximumRange()) {
            //iv.setImageResource(R.drawable.near);
            audioManager.setSpeakerphoneOn(false);

        } else {
            //iv.setImageResource(R.drawable.far);
            audioManager.setSpeakerphoneOn(true);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {


    }

}
