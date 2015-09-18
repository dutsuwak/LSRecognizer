package src;

import GUI.MainWindow;
import audioManage.MicrophoneListener;

public class test {
	
	public static void main(String[] args) {
		System.out.println("Starts");
		MainWindow m = new MainWindow();
		/*MicrophoneListener newMic = new MicrophoneListener(44100, 16, 2);
		newMic.run();
		while(true){
			System.out.println(newMic.getSoundLevel());
		}*/
	}
}
