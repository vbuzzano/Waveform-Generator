package org.electronism.sample;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.electronism.sample.aac.AacAudioDecoder;
import org.electronism.sample.flac.FlacAudioDecoder;
import org.electronism.sample.gui.WaveformGenerator;
import org.electronism.sample.mp3.MP3AudioDecoder;
import org.electronism.sample.ogg.OggAudioDecoder;
import org.electronism.sample.wave.WaveAudioDecoder;

public class Generator {

    private List<AudioDecoder> decoders;

    private AudioStandardizer audioStandardizer = new SampleHelper();

    public Generator(){
        prepareAudioDecoders();
    }

    public AudioFormat getFormat(File soundFile) throws UnsupportedAudioFileException, IOException {
        AudioDecoder decoder = getDecoderFor(soundFile);
        if (decoder == null)
            throw new Error("No decoder found for file " + soundFile.getAbsolutePath());
        
        return decoder.getAudioFormat(soundFile);
    }
    

    public String getAudioContainer(File soundFile) throws UnsupportedAudioFileException, IOException {
        AudioDecoder decoder = getDecoderFor(soundFile);
        if (decoder == null)
            throw new Error("No decoder found for file " + soundFile.getAbsolutePath());
        
        return decoder.getAudioContainer();
    }
    
    /**
     * Generate a waveform image file for a sound
     * @param file
     * @param options
     * @param image
     * @return
     * @throws IOException 
     * @throws UnsupportedAudioFileException 
     */
    public File generate(File soundFile, WaveformOption options, File imageFile) throws UnsupportedAudioFileException, IOException {
        BufferedImage image = generateImage(soundFile, options);
        ImageIO.write(image, "png", imageFile);
        return imageFile;
    }
    
    /**
     * Generate an waveform for a sound
     * @param soundFile
     * @param options
     * @return
     * @throws UnsupportedAudioFileException
     * @throws IOException
     */
    public BufferedImage generateImage(File soundFile, WaveformOption options) throws UnsupportedAudioFileException, IOException {
        Sample sample = loadStandardizedSample(soundFile);
        return generateImage(sample, options);
    }
    
    /**
     * Generate an waveform for a sound
     * @param sample
     * @param options
     * @return
     * @throws UnsupportedAudioFileException
     * @throws IOException
     */
    public BufferedImage generateImage(Sample sample, WaveformOption options) throws UnsupportedAudioFileException, IOException {
        BufferedImage image = new BufferedImage(options.getWidth(), options.getHeight(), BufferedImage.TYPE_INT_ARGB);
        WaveformGenerator waveformGenerator = new WaveformGenerator(sample, options.getWidth(), options.getHeight());
        waveformGenerator.setOptions(options);     
        waveformGenerator.draw(image.getGraphics());
        return image;
    }

    /**
     * Generate a sample for a sound
     * @param soundFile
     * @return
     * @throws UnsupportedAudioFileException
     * @throws IOException
     */
    public Sample loadSample(File soundFile) throws UnsupportedAudioFileException, IOException {
        AudioDecoder decoder = getDecoderFor(soundFile);
        if (decoder == null)
            throw new Error("No decoder found for file " + soundFile.getAbsolutePath());
        
        AudioInputStream ais = decoder.decode(soundFile);
        return new Sample(ais);
    }
    
    /**
     * Generate a standardized sample for a sound
     * @param soundFile
     * @return
     * @throws UnsupportedAudioFileException
     * @throws IOException
     */
    public Sample loadStandardizedSample(File soundFile) throws UnsupportedAudioFileException, IOException {
        AudioDecoder decoder = getDecoderFor(soundFile);
        if (decoder == null)
            throw new Error("No decoder found for file " + soundFile.getAbsolutePath());
        
        AudioInputStream ais = decoder.decodeAndStandardize(soundFile);
        return new Sample(ais);
    }


    /**
     * Find decoder for a sound
     * @param soundFile
     * @return
     */
    private AudioDecoder getDecoderFor(File soundFile) {
        for (AudioDecoder decoder : decoders) {
            if (decoder.isAbleToDecode(soundFile))
                return decoder;
        }
        return null;
    }

    /**
     * Standardize an audio input stream
     * @param ais
     * @return
     */

    public AudioInputStream standardize(AudioInputStream ais) {
        return audioStandardizer.standardize(ais);
    }

    /**
     * Prepare decoders
     */
    private void prepareAudioDecoders() {
        decoders = new ArrayList<AudioDecoder>();
        

        decoders.add(new AacAudioDecoder(audioStandardizer));
        decoders.add(new WaveAudioDecoder(audioStandardizer));
        decoders.add(new FlacAudioDecoder(audioStandardizer));
        decoders.add(new MP3AudioDecoder(audioStandardizer));
        decoders.add(new OggAudioDecoder(audioStandardizer));

    } 
}
