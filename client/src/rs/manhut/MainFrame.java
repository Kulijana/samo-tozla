package rs.manhut;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private final String username;
    private final String nameSurname;
    private Image avatar;

    //TODO put the regular categories, or come up with a fancier solution
    private JTextField category1;
    private JTextField category2;

    public MainFrame(String username, String nameSurname, Image avatar){
        this.username = username;
        this.nameSurname = nameSurname;
        this.avatar = avatar;

        this.setSize(1920,1080);

        this.setLayout(new BorderLayout());

        this.add(northPanel(), BorderLayout.NORTH);

        this.add(westPanel(), BorderLayout.WEST);


        this.setVisible(true);
    }

    private JPanel northPanel(){
        JPanel panel = new JPanel();
        JLabel avatarLabel = new JLabel(new ImageIcon(avatar));
        panel.add(avatarLabel);
        panel.add(new JLabel(nameSurname));
        return panel;
    }

    private JPanel westPanel(){
        //TODO add something to indicate what field fits what category
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        category1 = new JTextField();
        category2 = new JTextField();

        panel.add(category1);
        panel.add(category2);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO this is the button that triggers the search by categories
            }
        });
        panel.add(searchButton);
        return panel;
    }
}
