package org.electronism.sample.ogg;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.electronism.sample.AudioDecoder;
import org.electronism.sample.AudioStandardizer;
import org.tritonus.sampled.convert.jorbis.JorbisFormatConversionProvider;
import org.tritonus.sampled.file.jorbis.JorbisAudioFileReader;

/**
 * @author nw
 */
public class OggAudioDecoder implements AudioDecoder {

    private OggProperties     oggFile = new OggProperties();
    private AudioStandardizer audioStandardizer;

    public OggAudioDecoder(AudioStandardizer audioStandardizer) {
        this.audioStandardizer = audioStandardizer;
    }

    @Override
    public AudioInputStream decodeAndStandardize(File inputFile)
            throws UnsupportedAudioFileException, IOException
    {
        return standardize(decode(inputFile));
    }

    @Override
    public AudioInputStream decode(File inputFile)
            throws UnsupportedAudioFileException, IOException
    {

        AudioInputStream oggAis = null;
        AudioInputStream result = null;

        JorbisAudioFileReader reader = new JorbisAudioFileReader();
        oggAis = reader.getAudioInputStream(inputFile);

        AudioFormat decodedFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED, oggAis.getFormat()
                        .getSampleRate(), 16, oggAis.getFormat().getChannels(),
                oggAis.getFormat().getChannels() * 2, oggAis.getFormat()
                        .getSampleRate(), false);

        JorbisFormatConversionProvider oggReader = new JorbisFormatConversionProvider();
        result = oggReader.getAudioInputStream(decodedFormat, oggAis);

        return result;
    }

    @Override
    public boolean isAbleToDecode(File file) {
        return oggFile.isOfThisType(file);
    }

    @Override
    public AudioInputStream standardize(AudioInputStream ais) {
        return audioStandardizer.standardize(ais);
    }

    @Override
    public AudioFormat getAudioFormat(File inputFile)
            throws UnsupportedAudioFileException, IOException
    {
        AudioInputStream ais = null;

        JorbisAudioFileReader reader = new JorbisAudioFileReader();
        ais = reader.getAudioInputStream(inputFile);

        AudioFormat format = ais.getFormat();
        ais.close();

        return format;
    }

    @Override
    public String getAudioContainer() {
        return "Ogg";
    }
}