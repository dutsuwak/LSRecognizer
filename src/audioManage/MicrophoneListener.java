package audioManage;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class MicrophoneListener implements Runnable{
	
	private AudioFormat format;
	private TargetDataLine targetLine;
	private SourceDataLine sourceLine;
	private byte[] targetData;
	private boolean running = false;
	
	public MicrophoneListener(float sampleRate,int sampleSize,int channels){
		format = new AudioFormat(sampleRate, sampleSize, channels, true, true);
		 
		DataLine.Info targetInfo = new DataLine.Info(TargetDataLine.class, format);
		DataLine.Info sourceInfo = new DataLine.Info(SourceDataLine.class, format);
 
		try{
			targetLine = (TargetDataLine) AudioSystem.getLine(targetInfo);
			targetLine.open(format);
			targetLine.start();
			
			sourceLine = (SourceDataLine) AudioSystem.getLine(sourceInfo);
			sourceLine.open(format);
			sourceLine.start();
			
		}
		catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	
	private static int calculateRMSLevel(byte[] audioData){
	    long lSum = 0;
	    for(int i=0; i<audioData.length; i++)
	        lSum = lSum + audioData[i];

	    double dAvg = lSum / audioData.length;

	    double sumMeanSquare = 0d;
	    for(int j=0; j<audioData.length; j++)
	        sumMeanSquare = sumMeanSquare + Math.pow(audioData[j] - dAvg, 2d);

	    double averageMeanSquare = sumMeanSquare / audioData.length;
	    return (int)(Math.pow(averageMeanSquare,0.5d) + 0.5);
	}
	
	public int getSoundLevel(){
		try{
			return calculateRMSLevel(targetData);
		}
		catch(NullPointerException e){
			return 0;
			
		}
		
	}
	
	public void stop(){
		running = false;
	}
	

	@Override
	public void run() {
		int numBytesRead;
		targetData = new byte[targetLine.getBufferSize() / 5];
		running = true;
		while (running) {
			numBytesRead = targetLine.read(targetData, 0, targetData.length);
			//System.out.println(calculateRMSLevel(targetData));
			if (numBytesRead == -1)	break;
			
			sourceLine.write(targetData, 0, numBytesRead);
		}
	}

}
