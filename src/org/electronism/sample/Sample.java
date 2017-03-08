package org.electronism.sample;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.commons.io.IOUtils;


/**
 * Sample Class
 * Use to load a sample (wav, aif, etc...)
 * 
 * @author Vincent Buzzano <vincent.buzzano@gmail.com>
 *
 */
public class Sample {	

	/**
	 * Format
	 */
	AudioFormat _format;
	
	/**
	 * nombre de canaux. On aura la valeur 1 pour les sons mono, 2 pour les sons stéréo, et éventuellement plus pour les sons moins standards.
	 */
	int nChannel;
	
	/**
	 * nombre d'échantillons par seconde
	 */
	float nSamplesPerSec;

	/**
	 * nombre d'octets par secondes. Cette valeur fait double emploi avec les autres valeurs enregistrées mais vous devez la compléter correctement pour être certains que votre fichier sera compatible avec tous les programmes de son. Le nombre d'octets par seconde dépend :
	 * 
	 * •  du nombre d'échantillons pas seconde
	 * •  du nombre d'octets par échantillon
	 * •  du nombre de canaux
	 * 
	 * Il se calcul comme suit :
	 * nAvgBytesPerSec= nSamplesPerSec* nBitsPerSample/8* nChannels
	 * 
	 * (nBitsPerSample est le nombre de BITS par seconde, un octet comporte 8 bits) 
	 */
	int nAvgBytesPerSec;
	
	/**
	 * contient le nombre de bits par échantillon (voir note concernant l'amplitude)
	 */
	int nBitsPerSample;
	
	/**
	 * Durée = dataSize / nAvgBytesPerSec
	 */
	float time;
	
	/**
	 * Wave data
	 */
	SampleData data;
	
	/**
	 * Sample Point list
	 */
	private List<SamplePoint> points;
	
	/**
	 * Constructor
	 * @param file
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 */
	public Sample(File file) throws IOException, UnsupportedAudioFileException {
		this(new FileInputStream(file));
	}
	
	/**
	 * Constructor
	 * @param is
	 * @throws IOException
	 * @throws UnsupportedAudioFileException 
	 */
	public Sample(InputStream is) throws IOException, UnsupportedAudioFileException {
	    this(AudioSystem.getAudioInputStream(new BufferedInputStream(is)));		
	}
	
	public Sample(AudioInputStream ais) throws IOException {

		_format = ais.getFormat();

		this.nChannel = _format.getChannels();
		this.nSamplesPerSec = _format.getSampleRate();
		this.nBitsPerSample = _format.getSampleSizeInBits();
		this.nAvgBytesPerSec = (int) (nSamplesPerSec* nBitsPerSample/8* nChannel);

		byte d[] = IOUtils.toByteArray(ais);

        data = new SampleData(d);

		points = new ArrayList<SamplePoint>();
		
		for(int i = 0; i < data.dataSize; i++)
		{
			try {
				SamplePoint point = new SamplePoint(this, i);
				points.add(point);

			} catch(ArrayIndexOutOfBoundsException e)
			{
				break;
			}
		
		}
				
		updateTime();
		
		ais.close();
	}


	/**
	 * Met a jour la durée
	 */
	public void updateTime()
	{
		this.time = (float)data.getDataSize()/(float)this.nAvgBytesPerSec;
	}
	
	
	public String toString()
	{
		String string = "";
//		string += "ZONE       : " + this.tag1 + "\n";
//		string += "SIZE      : " + this.size + "\n";
//		string += "FORMAT     : " + this.format + "\n";
		string += "ENCODING            : " + _format.getEncoding() + "\n";
		string += "CHANNELS            : " + _format.getChannels() + "\n";
		string += "SAMPLE RATE         : " + _format.getSampleRate()+ "\n";
		string += "FRAME RATE          : " + _format.getFrameRate() + "\n";
		string += "FRAME SIZE          : " + _format.getFrameSize() + "\n";
		string += "SAMPLE SIZE IN BITS : " + _format.getSampleSizeInBits() + "\n";


		
//		string += "ZONE       : " + this.formatZone + "\n";
//		string += "LGDEF      : " + this.lgdef + "\n";
//		string += "FORMAT     : " + this.wFormatTag + "\n";
//		string += "NBCANAUX   : " + this.nChannel + "\n";
//		string += "FREQ       : " + this.nSamplesPerSec + "\n";
//		string += "BYTEPERSEC : " + this.nAvgBytesPerSec + "\n";
//		string += "NBRBYTE    : " + this.nBlockAlign + "\n";
//		string += "BNBITS     : " + this.nBitsPerSample + "\n";

//		string += "ZONE       : " + this.dataZone + "\n";
//		string += "DATA SIZE  : " + this.data.getDataSize() + "\n";
//		string += "TIME (sec) : " + this.time + "\n";
		return string;
	}


	/**
	 * @return the nChannel
	 */
	public int getNChannel() {
		return _format.getChannels();
	}

	/**
	 * @return the nSamplesPerSec
	 */
	public float getNSamplesPerSec() {
		return nSamplesPerSec;
	}

	/**
	 * @return get encoding
	 */
	public AudioFormat.Encoding getEncoding() {
		return _format.getEncoding();
	}


	/**
	 * @return the nBitsPerSample
	 */
	public int getNBitsPerSample() {
		return _format.getSampleSizeInBits();
	}
	

	/**
	 * @param bitsPerSample the nBitsPerSample to set
	 */
	public void setNBitsPerSample(int bitsPerSample) {
		nBitsPerSample = bitsPerSample;
	}


	/**
	 * @return the time
	 */
	public float getTime() {
		return time;
	}

	/**
	 * @return the data
	 */
	public SampleData getData() {
		return data;
	}


	/**
	 * @param data the data to set
	 */
	public void setData(SampleData data) {
		this.data = data;
	}

	/**
	 * Get a wave point
	 * @param offset
	 * @return
	 */
	public SamplePoint getPoint(int offset)
	{
		SamplePoint point = null;
		
		point = new SamplePoint(this, offset);
		
		return point;
		
	}

	/**
	 * Get a Y ratio for a specific maximum Height
	 * @param defHeight
	 */
	public static double getYRatio(Sample wave, int maxHeight) {
		long maxPoint = 1;
		
		switch (wave.getNBitsPerSample()) {
			case 8: // 8 bits
				maxPoint = 128*2;	
				break;
	
			case 16: // 16 bits
				maxPoint = 32767*2;
				break;
	
			case 24: // 24 bits
				maxPoint = 8388607*2;
				break;
	
			case 32: // 32 bits
				maxPoint = 2147483647*2;
				break;
	
			default:
				System.err.println("need to define maxPoint for " + (wave.getNBitsPerSample()*8) + " bit");
				break;
		}
		return (double)maxHeight/maxPoint;
	}
	
	/** 
	 * Get Number of point in the data
	 * @return
	 */
	public int getNbOfPoints() {
		float nb = getData().getDataSize()
				/ getNChannel()
				/ getNBitsPerSample();
		return (int)Math.ceil(nb);
	}
}

