package com.senamoviles.alcayata.alcayata.MainFragments;


import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.senamoviles.alcayata.alcayata.R;

import static com.senamoviles.alcayata.alcayata.MainActivity.opcion;

/**
 * A simple {@link Fragment} subclass.
 */
public class AudioFragment extends DialogFragment implements  View.OnClickListener, Runnable {

    MediaPlayer mediaPlayer;
    ImageButton img_btn_play, img_btn_pause, img_btn_stop;
    SeekBar soundseekBar;
    TextView textview;
    Handler handler = new Handler();
    String paso;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_audio, container, false);

        img_btn_play = (ImageButton) view.findViewById(R.id.img_btn_play);
        img_btn_play.setOnClickListener(this);
        soundseekBar = (SeekBar) view.findViewById(R.id.soundSeekBar);

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
                    img_btn_play.setBackgroundResource(R.drawable.play);

                } else {
                    mediaPlayer.start();
                    img_btn_play.setBackgroundResource(R.drawable.pause);
                }

                break;
        }


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
        if(mediaPlayer.isPlaying()) {
            int mediaPlayer_position = mediaPlayer.getCurrentPosition();
            getActivity().getSharedPreferences("PLAY_PAUSE", Context.MODE_PRIVATE).edit().putInt("CHECK_PLAY_PAUSE", mediaPlayer_position).apply();
            mediaPlayer.pause();
        }
    }
}
