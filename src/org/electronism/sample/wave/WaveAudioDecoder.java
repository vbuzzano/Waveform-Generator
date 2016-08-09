package org.electronism.sample.wave;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.electronism.sample.AudioDecoder;
import org.electronism.sample.AudioStandardizer;
import org.electronism.sample.Sample;
import org.tritonus.sampled.convert.PCM2PCMConversionProvider;
import org.tritonus.sampled.file.WaveAudioFileReader;


/**
 * @author nw
 */
public class WaveAudioDecoder implements AudioDecoder {

  private final WaveProperties waveFile = new WaveProperties();

  private final AudioStandardizer audioStandardizer;

  public WaveAudioDecoder(AudioStandardizer normalizer) {
    this.audioStandardizer = normalizer;
  }

  @Override
  public AudioInputStream decode(File inputFile) throws UnsupportedAudioFileException, IOException {

    AudioInputStream ais = null;
    AudioInputStream result = null;

    try {
        ais = new WaveAudioFileReader().getAudioInputStream(inputFile);

        AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, ais.getFormat().getSampleRate(), 16, ais.getFormat().getChannels(), ais
                .getFormat().getChannels() * 2, ais.getFormat().getSampleRate(), false);

        result = new PCM2PCMConversionProvider().getAudioInputStream(decodedFormat, ais);

    } catch(UnsupportedAudioFileException e) {
        // try default Audio System 
        ais = AudioSystem.getAudioInputStream(inputFile);
        AudioFormat baseFormat = ais.getFormat();
        AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 
                                                    baseFormat.getSampleRate(),
                                                    16,
                                                    baseFormat.getChannels(),
                                                    baseFormat.getChannels() * 2,
                                                    baseFormat.getSampleRate(),
                                                    false);
        result = AudioSystem.getAudioInputStream(decodedFormat, ais);
    }

    return audioStandardizer.standardize(result);
  }

  @Override
  public boolean isAbleToDecode(File file) {
    return waveFile.isOfThisType(file);
  }

}