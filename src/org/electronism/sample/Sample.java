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

import org.electronism.helpers.ByteHelper;


/**
 * Sample Class
 * Use to load a sample (wav, aif, etc...)
 * 
 * @author Vincent Buzzano <vincent.buzzano@gmail.com>
 *
 */
public class Sample {	
	
	/**
	 * On trouve d'abord la mention « RIFF » dans les 4 premiers octets du fichier ($52, $49, $42, $42 en hexadécimal. Ce sont les codes ASCII des lettres R,I,F,F)
	 */
//	String tag1; // "RIFF" 4 byte

	/**
	 * On trouve ensuite la taille TOTALE du fichier codée sous la forme d'un entier long (4 octets, donc)
	 */
//	int size;

	/**
	 * On trouve ensuite la mention « WAVE» soit 4 caractères ce qui nous donne en hexadécimal : $57,$41,$56,$45
	 */
//	String format; //  "WAVE" 4 byte

	AudioFormat _format;
	
	/**
	 * Indicateur de zone : « fmt  » soit 4 caractères (attention, le dernier caractère est un espace) ce qui nous donne en hexadécimal : $66,$6D,$74,$20. « fmt » est l'abréviation de « format ». Cet indicateur nous prévient que les informations qui vont suivre concernent le format du son (comme vous allez le voir)
	 */
//	String formatZone = "fmt "; // "fmt " 4 byte
	
	/**
	 * Taille de la structure WAVEFORMAT sous la forme d'un entier long (soit 4 octets). La structure WAVEFORMAT comporte 16 octets. On trouve donc, logiquement, la valeur 16 (ou $10 en hexadécimal) stockée à cet endroit.
	 */
//	int lgdef; // Taille de la structure WAVEFORMAT

	/**
	 * ce champ contient un code correspondant au format exact de codage des données. Pour les fichiers de type PCM, ce champ contiendra la valeur 1
	 */
//	short wFormatTag;
	
	/**
	 * nombre de canaux. On aura la valeur 1 pour les sons mono, 2 pour les sons stéréo, et éventuellement plus pour les sons moins standards.
	 */
//	short nChannel;
	int nChannel;
	
	/**
	 * nombre d'échantillons par seconde
	 */
//	int nSamplesPerSec;
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
	 *  contient la taille totale (en octets) d'un échantillon. Cette valeur fait également double emploi avec les autres valeurs enregistrées mais vous devez aussi la compléter correctement pour être certains que votre fichier sera compatible avec tous les programmes de son. Elle dépend :
	 *
	 * •  du nombre d'octets par échantillon
 	 * •  du nombre de canaux
	 *
	 * Elle se calcul comme suit :
	 * nBlockAlign = nBitsPerSample/8* nChannels*
	 * 
	 * (nBitsPerSample est le nombre de BITS par seconde, un octet comporte 8 bits) 
	 */
//	int nBlockAlign;
	
	/**
	 * contient le nombre de bits par échantillon (voir note concernant l'amplitude)
	 */
	int nBitsPerSample;
	
	/**
	 * Indicateur de zone : «data» soit 4 caractères ce qui nous donne en hexadécimal : $64,$61,$74,$61. Cet indicateur nous prévient que les informations qui vont suivre sont les données proprement dites.
	 */
//	String dataZone = "data";
	
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
	public Sample(File file) 
		throws IOException, UnsupportedAudioFileException
	{
		this(new FileInputStream(file));
	}
	
	/**
	 * Constructor
	 * @param is
	 * @throws IOException
	 * @throws UnsupportedAudioFileException 
	 */
	public Sample(InputStream is) 
		throws IOException, UnsupportedAudioFileException
	{
		
		AudioInputStream ais = AudioSystem.getAudioInputStream(
				new BufferedInputStream(is));
		
		_format = ais.getFormat();

//		byte[] header = new byte[44];
//		is.read(header, 0, 44);
		
//		this.tag1 = ByteHelper.byte2String(header, 0, 4);
//		this.size = ByteHelper.byte2int(header, 4);
		
//		this.format = ByteHelper.byte2String(header, 8, 4);
		
//		this.formatZone = ByteHelper.byte2String(header, 12, 4);
//		this.lgdef = ByteHelper.byte2int(header, 16);
//		this.wFormatTag = ByteHelper.byte2short(header, 20);
//		this.nChannel = ByteHelper.byte2short(header, 22);
		this.nChannel = _format.getChannels();
//		this.nSamplesPerSec = ByteHelper.byte2int(header,24);
		this.nSamplesPerSec = _format.getSampleRate();
//		this.nAvgBytesPerSec = ByteHelper.byte2int(header,28);

		 nAvgBytesPerSec = (int) (nSamplesPerSec* nBitsPerSample/8* nChannel);
		
		//		this.nBlockAlign = ByteHelper.byte2short(header, 30);
//		this.nBitsPerSample = ByteHelper.byte2short(header, 32);
		this.nBitsPerSample = _format.getSampleSizeInBits();
		
		
//		this.dataZone = ByteHelper.byte2String(header, 36, 4);				
//		int dataSize = ByteHelper.byte2int(header, 40);
		
		
		
//		byte[] d = new byte[(int)dataSize];
//		is.read(d);
		
		byte[] d = new byte[(int)(ais.getFrameLength() * _format.getFrameSize())];
		ais.read(d);
		data = new SampleData(d);
		
		points = new ArrayList<SamplePoint>();
		
		int count = 0;
		for(int i = 0; i < d.length; i++)
		{
			try {
				SamplePoint point = new SamplePoint(this, i);
				points.add(point);
				count++;

			} catch(ArrayIndexOutOfBoundsException e)
			{
				break;
			}
		
		}
				
		updateTime();
		
//		is.close();
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
	 * @return the tag1
	 */
/*	public String getTag1() {
		return tag1;
	}
*/

	/**
	 * @param tag1 the tag1 to set
	 */
/*	public void setTag1(String tag1) {
		this.tag1 = tag1;
	}
*/

	/**
	 * @return the size
	 */
//	public int getSize() {
//		return size;
//	}


	/**
	 * @param size the size to set
	 */
//	public void setSize(int size) {
//		this.size = size;
//	}


	/**
	 * @return the format
	 */
//	public String getFormat() {
//		return format;
//	}


	/**
	 * @param format the format to set
	 */
//	public void setFormat(String format) {
//		this.format = format;
//	}


	/**
	 * @return the formatZone
	 */
//	public String getFormatZone() {
//		return formatZone;
//	}


	/**
	 * @param formatZone the formatZone to set
	 */
//	public void setFormatZone(String formatZone) {
//		this.formatZone = formatZone;
//	}


	/**
	 * @return the lgdef
	 */
//	public int getLgdef() {
//		return lgdef;
//	}


	/**
	 * @param lgdef the lgdef to set
	 */
//	public void setLgdef(int lgdef) {
//		this.lgdef = lgdef;
//	}


	/**
	 * @return the wFormatTag
	 */
//	public short getWFormatTag() {
//		return wFormatTag;
//	}


	/**
	 * @param formatTag the wFormatTag to set
	 */
//	public void setWFormatTag(short formatTag) {
//		wFormatTag = formatTag;
//	}


	/**
	 * @return the nChannel
	 */
	public int getNChannel() {
		return _format.getChannels();
	}

//	/**
//	 * @param channel the nChannel to set
//	 */
//	public void setNChannel(short channel) {
//		nChannel = channel;
//	}


	/**
	 * @return the nSamplesPerSec
	 */
	public float getNSamplesPerSec() {
		return nSamplesPerSec;
	}


//	/**
//	 * @param samplesPerSec the nSamplesPerSec to set
//	 */
//	public void setNSamplesPerSec(int samplesPerSec) {
//		nSamplesPerSec = samplesPerSec;
//	}


	/**
	 * @return the nAvgBytesPerSec
	 */
//	public int getNAvgBytesPerSec() {
//		return nAvgBytesPerSec;
//	}


//	/**
//	 * @param avgBytesPerSec the nAvgBytesPerSec to set
//	 */
//	public void setNAvgBytesPerSec(int avgBytesPerSec) {
//		nAvgBytesPerSec = avgBytesPerSec;
//	}


	/**
	 * @return the nBlockAlign
	 */
/*	public int getNBlockAlign() {
		return nBlockAlign;
	}
*/

	/**
	 * @param blockAlign the nBlockAlign to set
	 */
//	public void setNBlockAlign(int blockAlign) {
//		nBlockAlign = blockAlign;
//	}


	/**
	 * @return the nBitsPerSample
	 */
	public int getNBitsPerSample() {
//		return nBitsPerSample;
		return _format.getSampleSizeInBits();
	}
	

	/**
	 * @param bitsPerSample the nBitsPerSample to set
	 */
	public void setNBitsPerSample(int bitsPerSample) {
		nBitsPerSample = bitsPerSample;
	}


	/**
	 * @return the dataZone
	 */
/*	public String getDataZone() {
		return dataZone;
	}
*/

	/**
	 * @param dataZone the dataZone to set
	 */
/*	public void setDataZone(String dataZone) {
		this.dataZone = dataZone;
	}
*/
	/**
	 * @return the time
	 */
	public float getTime() {
		return time;
	}


	/**
	 * @param time the time to set
	 */
//	public void setTime(float time) {
//		time = time;
//	}


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

