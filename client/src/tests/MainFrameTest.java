package tests;

import rs.manhut.MainFrame;
import rs.manhut.entities.Party;

public class MainFrameTest {

    public static void main(String[] args){
        Party p = new Party();
        p.setEmail("testMail@mail.com");
        p.setFirstName("Tester");
        p.setLastName("Testerson");
        p.setId(new Long(1));
        p.setProfilePicture("toBeFixed");
        p.setPassword("password");
        MainFrame frame = new MainFrame(p,null);
    }
}
