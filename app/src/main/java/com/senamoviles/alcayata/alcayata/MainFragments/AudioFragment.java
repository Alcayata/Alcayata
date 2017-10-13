package com.senamoviles.alcayata.alcayata.MainFragments;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.senamoviles.alcayata.alcayata.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AudioFragment extends Fragment implements  View.OnClickListener, Runnable{

    MediaPlayer mediaPlayer;
    ImageButton img_btn_play, img_btn_pause, img_btn_stop;
    SeekBar soundseekBar;
    TextView textview;
    Handler handler = new Handler();


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
        img_btn_stop = (ImageButton) view.findViewById(R.id.img_btn_stop);
        img_btn_stop.setOnClickListener(this);
        soundseekBar = (SeekBar) view.findViewById(R.id.soundSeekBar);
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.proyecto_voces_semana_santa);


        this.run();

        handler = new Handler();

        soundseekBar.setMax(mediaPlayer.getDuration());

        soundseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean input) {

                seekBar.setMax(mediaPlayer.getDuration()/1000);

                if (input){
                    mediaPlayer.seekTo(progress*1000);
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
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    img_btn_play.setBackgroundResource(R.drawable.play);

                }else {
                    mediaPlayer.start();
                    img_btn_play.setBackgroundResource(R.drawable.pause);
                }

                break;

            case R.id.img_btn_stop:

                mediaPlayer.stop();
                mediaPlayer = MediaPlayer.create(getActivity(), R.raw.proyecto_voces_semana_santa);

                break;
        }


    }

    @Override
    public void run() {
        if(mediaPlayer != null){
            int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
            soundseekBar.setProgress(mCurrentPosition);
        }
        handler.postDelayed(this, 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }
}
