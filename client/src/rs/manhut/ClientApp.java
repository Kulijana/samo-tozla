package rs.manhut;

import javax.swing.JFrame;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class ClientApp extends JFrame {

	public static void main(String[] args) {
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		JFrame frame = new RegistrationFrame();
	}

}
