package org.electronism.sample;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import org.tritonus.sampled.convert.SampleRateConversionProvider;

/**
 * Some static methods for easing the handling of sound data.
 * 
 * @author nw
 */
public class SampleHelper implements AudioStandardizer {

  public SampleHelper() {
  }
  
  

  public byte[] getByteArray(AudioInputStream inputStream) throws IOException {

    byte[] result = new byte[(int) getStreamSizeInBytes(inputStream)];

    // Choose a buffer of 100 KB
    byte[] buffer = new byte[102400];

    int len;
    int writtenBytes = 0;
    while ((len = inputStream.read(buffer)) > 0) {

      System.arraycopy(buffer, 0, result, writtenBytes, len);

      writtenBytes += len;
    }

    return result;
  }

  public long getStreamSizeInBytes(AudioInputStream inputStream) {

      // Calculate length of wav input stream
      AudioFormat af = inputStream.getFormat();

      long frameLength = inputStream.getFrameLength();
      long byteLength;

      int frameSize = af.getFrameSize();

      byteLength = frameLength * frameSize;

      return byteLength;
  }


  /*
   * The code below is taken from
   * http://www.jsresources.org/examples/AudioConverter.java.html
   */
  @Override
  public AudioInputStream standardize(AudioInputStream stream) {
//    addDebugMessage("Original format: " + stream.getFormat());

    if (stream.getFormat().getChannels() != TARGET_AUDIO_FORMAT.getChannels()) {
      stream = convertChannels(TARGET_AUDIO_FORMAT.getChannels(), stream);
    }

    boolean bDoConvertSampleSize = (stream.getFormat().getSampleSizeInBits() != TARGET_AUDIO_FORMAT.getSampleSizeInBits());
    boolean bDoConvertEndianness = (stream.getFormat().isBigEndian() != TARGET_AUDIO_FORMAT.isBigEndian());

    if (bDoConvertSampleSize || bDoConvertEndianness) {
      stream = convertSampleSizeAndEndianness(TARGET_AUDIO_FORMAT.getSampleSizeInBits(), TARGET_AUDIO_FORMAT.isBigEndian(), stream);
    }

    if (!equals(stream.getFormat().getSampleRate(), TARGET_AUDIO_FORMAT.getSampleRate())) {
      stream = convertSampleRate(TARGET_AUDIO_FORMAT.getSampleRate(), stream);
    }

//  addDebugMessage("Converted format: " + stream.getFormat());
    return stream;
  }

  private AudioInputStream convertChannels(int nChannels, AudioInputStream sourceStream) {
    AudioFormat sourceFormat = sourceStream.getFormat();

    AudioFormat targetFormat = new AudioFormat(sourceFormat.getEncoding(), sourceFormat.getSampleRate(), sourceFormat.getSampleSizeInBits(), nChannels,
        calculateFrameSize(nChannels, sourceFormat.getSampleSizeInBits()), sourceFormat.getFrameRate(), sourceFormat.isBigEndian());

    return AudioSystem.getAudioInputStream(targetFormat, sourceStream);
  }

  private AudioInputStream convertSampleSizeAndEndianness(int nSampleSizeInBits, boolean bBigEndian, AudioInputStream sourceStream) {
    AudioFormat sourceFormat = sourceStream.getFormat();

    AudioFormat targetFormat = new AudioFormat(sourceFormat.getEncoding(), sourceFormat.getSampleRate(), nSampleSizeInBits, sourceFormat.getChannels(),
        calculateFrameSize(sourceFormat.getChannels(), nSampleSizeInBits), sourceFormat.getFrameRate(), bBigEndian);

    return AudioSystem.getAudioInputStream(targetFormat, sourceStream);
  }

  private AudioInputStream convertSampleRate(float fSampleRate, AudioInputStream sourceStream) {
    AudioFormat sourceFormat = sourceStream.getFormat();

    AudioFormat targetFormat = new AudioFormat(sourceFormat.getEncoding(), fSampleRate, sourceFormat.getSampleSizeInBits(), sourceFormat.getChannels(),
        sourceFormat.getFrameSize(), fSampleRate, sourceFormat.isBigEndian());

    SampleRateConversionProvider sampleRateConversionProvider = new SampleRateConversionProvider();
    if (sampleRateConversionProvider.isConversionSupported(targetFormat, sourceFormat)) {
      return sampleRateConversionProvider.getAudioInputStream(targetFormat, sourceStream);
    }

    return sourceStream;
  }

  private int calculateFrameSize(int nChannels, int nSampleSizeInBits) {
    return ((nSampleSizeInBits + 7) / 8) * nChannels;
  }

  private boolean equals(float f1, float f2) {
    return (Math.abs(f1 - f2) < 1E-9F);
  }

}