package org.electronism.sample;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;

public interface AudioDecoder {
    AudioInputStream decodeAndStandardize(File inputFile)
            throws UnsupportedAudioFileException, IOException;

    AudioInputStream decode(File inputFile)
            throws UnsupportedAudioFileException, IOException;

    AudioInputStream standardize(AudioInputStream ais);

    AudioFormat getAudioFormat(File inputFile) throws UnsupportedAudioFileException, IOException;
    
    boolean isAbleToDecode(File file);

    /**
     * Return file type
     * @param soundFile
     * @return (mp3, wav, ogg, aac, flac, ...)
     * @throws UnsupportedAudioFileException
     * @throws IOException
     */
    String getAudioContainer();
}