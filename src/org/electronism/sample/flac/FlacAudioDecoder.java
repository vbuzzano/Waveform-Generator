package org.electronism.sample.flac;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.electronism.sample.AudioDecoder;
import org.electronism.sample.AudioStandardizer;
import org.kc7bfi.jflac.sound.spi.FlacAudioFileReader;
import org.kc7bfi.jflac.sound.spi.FlacFormatConversionProvider;
 
public class FlacAudioDecoder implements AudioDecoder { 
 
  private FlacProperties flacFile = new FlacProperties();
 
  private final AudioStandardizer audioStandardizer; 
 
  public FlacAudioDecoder(AudioStandardizer audioStandardizer) { 
    this.audioStandardizer = audioStandardizer; 
  } 
 
  @Override 
  public AudioInputStream decode(File inputFile) throws UnsupportedAudioFileException, IOException { 
 
    AudioInputStream flacAis = null; 
    AudioInputStream result = null; 
 
    FlacAudioFileReader fafr = new FlacAudioFileReader(); 
    flacAis = fafr.getAudioInputStream(inputFile); 
 
    AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, flacAis.getFormat().getSampleRate(), 16, flacAis.getFormat().getChannels(), 
        flacAis.getFormat().getChannels() * 2, flacAis.getFormat().getSampleRate(), false); 
 
    FlacFormatConversionProvider flacReader = new FlacFormatConversionProvider(); 
    result = flacReader.getAudioInputStream(decodedFormat, flacAis); 
 
    return audioStandardizer.standardize(result); 
  } 
 
  @Override 
  public boolean isAbleToDecode(File file) { 
    return flacFile.isOfThisType(file); 
  }
}
