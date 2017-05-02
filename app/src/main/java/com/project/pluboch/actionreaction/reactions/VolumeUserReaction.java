package com.project.pluboch.actionreaction.reactions;

import android.media.AudioManager;

/**
 * Created by Piotr on 2017-04-30.
 */

public class VolumeUserReaction extends AbstractUserReaction {
    private int volumeLevel;
    private AudioManager audioManager;


    public VolumeUserReaction(int volumeLevel, AudioManager audioManager) {
        super(UserReactionType.VOLUME);
        this.volumeLevel = volumeLevel;
        this.audioManager = audioManager;
        this.paramNumber = 1;
    }

    @Override
    public void performReaction() {
        audioManager.setStreamVolume(AudioManager.STREAM_RING,volumeLevel,0);
    }

    @Override
    public String dbParamsRepresentation() {
        return Integer.toString(volumeLevel);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("\n").append("Selected volume level: ").append(volumeLevel);

        return sb.toString();
    }
}
