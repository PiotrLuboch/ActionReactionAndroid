package com.project.pluboch.actionreaction.reactions.activities;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.project.pluboch.actionreaction.R;

public class VolumeReaction extends AppCompatActivity {

    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_reaction);
        seekBar = (SeekBar) findViewById(R.id.barVolume);
        AudioManager am =
                (AudioManager) getSystemService(getApplicationContext().AUDIO_SERVICE);
        final int maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_RING);
        seekBar.setMax(maxVolume);
        final TextView volumeLevel = (TextView) findViewById(R.id.txtVolumeLevel);
        volumeLevel.setText("Volume level: 0/" + maxVolume);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                volumeLevel.setText("Volume level: " + progress + "/" + maxVolume);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //dummy
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //dummy
            }
        });
    }

    public void finishVolumeReaction(View view) {
        Intent intent = getIntent();
        intent.putExtra("volumeLevel", seekBar.getProgress());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
