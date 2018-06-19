package tests;

import rs.manhut.CreateListingFrame;
import rs.manhut.entities.Party;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class CreateListingFrameTest {

    public static void main(String[] args){
        try {
            Properties properties = new Properties();
            properties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            InitialContext ctx = new InitialContext(properties);
            Party party = new Party();
            CreateListingFrame frame = new CreateListingFrame(party,ctx);
//            frame.setVisible(true);
        } catch (NamingException ne) {
            ne.printStackTrace();
        }
    }
}
