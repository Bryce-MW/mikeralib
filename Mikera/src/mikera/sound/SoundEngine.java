package mikera.sound;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import javax.sound.sampled.*;

import mikera.image.ImageUtils;

public class SoundEngine {

	private static HashMap<String,Sample> samples=new HashMap<String,Sample>();
	
	public static final AudioFormat STEREO_FORMAT=new AudioFormat(
			AudioFormat.Encoding.PCM_SIGNED,
			AudioSystem.NOT_SPECIFIED, // no specific sample rate
			16, // 16=buit encoding
			2, // 2 channel stereo
			2,
			AudioSystem.NOT_SPECIFIED, // no specific sample rate
			true // big endian
			);
	
	
	public static Sample getSound (String url) {
		Sample c=samples.get(url);
		return c;
	}
	
	public static Sample sampleFromClip(Clip c) {
		Sample s=new Sample();
		return s;
		
	}
	
	public static Sample loadSample(String url) {
		Sample c=getSound(url);
		if (c!=null) return c;
		
	    Clip clip = loadClip(url);
	    Sample sample=sampleFromClip(clip);

	    samples.put(url, sample);
	    return sample;
	}
	
	public static Clip loadClip(String url) {
		try {
			URL soundURL = SoundEngine.class.getResource(url);	
			if (soundURL==null) throw new Error("File not found: "+url);
			AudioInputStream stream = AudioSystem.getAudioInputStream(soundURL);
			
	        AudioFormat format = stream.getFormat();

	        DataLine.Info info = new DataLine.Info(
	        		Clip.class, 
	        		format, 
	        		((int)stream.getFrameLength()*format.getFrameSize()));
	        
	        Clip clip = (Clip) AudioSystem.getLine(info);
	        clip.open(stream);
	        
	        return clip;
		} catch (Exception e) {	
			throw new Error(e);
		} 
	}
	
	public static void main(String[] args) {
		Clip c=loadClip("/mikera/sound/Thud.wav");
		try {
			c.start();
			Thread.sleep(100);
			c.start();
			Thread.sleep(c.getMicrosecondLength()/1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}