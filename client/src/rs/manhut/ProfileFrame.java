package rs.manhut;

import rs.manhut.entities.Listing;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class ProfileFrame extends JFrame {

    private final String username;
    private final String nameSurname;
    private Image avatar;

    private List<Listing> listings;

    //TODO put the regular categories, or come up with a fancier solution
    private JTextField category1;
    private JTextField category2;

    public ProfileFrame(String username, String nameSurname, Image avatar, List<Listing> listings){
        this.username = username;
        this.nameSurname = nameSurname;
        this.avatar = avatar;
        this.listings = listings;

        this.setSize(1920,1080);

        this.setLayout(new BorderLayout());

        this.add(northPanel(), BorderLayout.NORTH);

        JScrollPane pane = new JScrollPane();
        pane.setViewportView(centralPanel());
        this.add(pane,BorderLayout.CENTER);

        this.add(southPanel(),BorderLayout.SOUTH);


        this.setVisible(true);
    }

    private JPanel northPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel avatarLabel = new JLabel(new ImageIcon(avatar));
        panel.add(avatarLabel, BorderLayout.WEST);
        JLabel label = new JLabel(nameSurname);
        panel.add(new JLabel(nameSurname));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO return to the main page
            }
        });
        panel.add(backButton, BorderLayout.EAST);
        panel.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 3), username));
        return panel;
    }


    private JPanel centralPanel(){
        //TODO add ListingPanels for each listing
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        Image image = null;
        try {
            image = ImageIO.read(new File("./Testing.jpg"));

        }catch(Exception ex){
            ex.printStackTrace();
        }
        //panel.add(new JLabel(new ImageIcon(image)));
        for(Listing listing : listings){
            panel.add(new MinimizedListingPanel(image, 404.00));
        }
        for(int i = 0;i < 14;i++){
            GridBagConstraints c =new GridBagConstraints();
            c.gridx = i%6;
            c.gridy = i/6;
            c.anchor = GridBagConstraints.NORTH;
            c.insets = new Insets(10,10,10,10);
            panel.add(new MinimizedListingPanel(image, 404.00),c);
        }
        panel.setVisible(true);
        return panel;
    }

    private JPanel southPanel(){
        JPanel panel = new JPanel();
        JButton button = new JButton("Add a new listing");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO actually do something useful with this
                CreateListingFrame newListing = new CreateListingFrame(null,null);
            }
        });
        panel.add(button);
        return panel;
    }
}
