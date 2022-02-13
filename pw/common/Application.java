package pw.common;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Application {
	private static double appver = 0.2;
	public static void Exit() {
		System.exit(0);
	}
	public static double getLibraryVersion() {
		return appver;
	}
	public static void OpenURL(String urlstr){
			Desktop desktop = java.awt.Desktop.getDesktop();
			try {
				//specify the protocol along with the URL
				URI url = new URI(urlstr);
				try {
					desktop.browse(url);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
