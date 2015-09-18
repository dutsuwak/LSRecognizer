package src;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
 
public class TestMic {
	
	protected static int calculateRMSLevel(byte[] audioData){ // audioData might be buffered data read from a data line
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
 
	public static void main(String[] args) {
		AudioFormat format = new AudioFormat(44100, 16, 2, true, true);
 
		DataLine.Info targetInfo = new DataLine.Info(TargetDataLine.class, format);
		DataLine.Info sourceInfo = new DataLine.Info(SourceDataLine.class, format);
 
		try {
			TargetDataLine targetLine = (TargetDataLine) AudioSystem.getLine(targetInfo);
			targetLine.open(format);
			targetLine.start();
			
			SourceDataLine sourceLine = (SourceDataLine) AudioSystem.getLine(sourceInfo);
			sourceLine.open(format);
			sourceLine.start();
 
			int numBytesRead;
			byte[] targetData = new byte[targetLine.getBufferSize() / 5];
 
			while (true) {
				numBytesRead = targetLine.read(targetData, 0, targetData.length);
				System.out.println(calculateRMSLevel(targetData));
				if (numBytesRead == -1)	break;
				
				sourceLine.write(targetData, 0, numBytesRead);
			}
		}
		catch (Exception e) {
			System.err.println(e);
		}
	}
 
}