package tests;

import rs.manhut.MainFrame;
import rs.manhut.entities.Party;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;

public class MainFrameTest {

    public static void main(String[] args){
        Party p = new Party();
        p.setEmail("testMail@mail.com");
        p.setFirstName("Tester");
        p.setLastName("Testerson");
        p.setId(new Long(1));
        p.setProfilePicture("toBeFixed");
        p.setPassword("password");
        Properties properties = new Properties();
        properties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");

        InitialContext ctx = null;
        try {
            ctx = new InitialContext(properties);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        MainFrame frame = new MainFrame(p,ctx);
    }
}
