package rs.manhut;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JFrame;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class ClientApp extends JFrame {

	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

    	try {
			Properties properties = new Properties();
			properties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
			InitialContext ctx = new InitialContext(properties);
			JFrame frame = new LoginFrame(ctx);
		} catch (NamingException ne) {
    		ne.printStackTrace();
    	}
	}
}
