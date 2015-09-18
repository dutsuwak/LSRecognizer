package dataAccess;


/**
 * Interface que permite obtener valores constantes en el programa.
 * De esta forma si se necesita cambiar algun valor de estos en todo el programa se realiza directamente
 * desde esta clase y su valor cambiara en todo el programa.
 * El uso de esta clase facilita el orden y la legibilidad del código.
 * 
 * @author Fabian A. Solano Madriz
 */
public interface Constants {
	public static final int WIDTH = Integer.parseInt(ReadProperties.file.getSetting("width"));
	public static final int HEIGHT = Integer.parseInt(ReadProperties.file.getSetting("height"));
	public static final int WIDTH_chat = Integer.parseInt(ReadProperties.file.getSetting("width_chat"));
	public static final int HEIGHT_chat = Integer.parseInt(ReadProperties.file.getSetting("height_chat"));
	public static final String PC_IP = ReadProperties.file.getSetting("local_ip");
	public static final String PC_ID = ReadProperties.file.getSetting("pc_id");
	
	
}
