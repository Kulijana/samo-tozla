package rs.manhut;

import javax.swing.JFrame;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class ClientApp extends JFrame {

	public static void main(String[] args) {
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex)
		{
		}
		JFrame frame = new ClientApp();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(640, 480);
		frame.setTitle("DVD EE");
		frame.setVisible(true);
	}

}
