package tests;

import rs.manhut.RegistrationFrame;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.*;

public class RegistrationFrameTest {

    public static void main(String[] args){
    	try {
    		JFrame frame = new RegistrationFrame(new InitialContext());
    	} catch (NamingException ne) {
    		ne.printStackTrace();
    	}
    }
}
