package rs.manhut;

import rs.manhut.entities.Listing;
import rs.manhut.entities.Party;

import javax.imageio.ImageIO;
import javax.naming.InitialContext;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class MainFrame extends JFrame{

    private Party party;
    private InitialContext ctx;

    private JTextField nameField;
    private JComboBox materialBox;
    private JComboBox colorBox;
    private JComboBox typeBox;
    private List<Listing> listings;

    public MainFrame(Party party, InitialContext ctx){
        this.party = party;
        this.ctx = ctx;
        this.setSize(1920,1080);
        this.add(westPanel(), BorderLayout.WEST);
        this.add(northPanel(), BorderLayout.NORTH);
        JScrollPane pane = new JScrollPane();
        pane.setViewportView(centralPanel());
        this.add(pane, BorderLayout.CENTER);
        this.setVisible(true);
    }

    private JPanel northPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        //TODO put the picture from party
        Image image = null;
        try {
            image = ImageIO.read(new File("./Testing.jpg"));
        }catch(Exception ex){
            ex.printStackTrace();
        }
        JLabel avatarLabel = new JLabel(new ImageIcon(image));
        panel.add(avatarLabel, BorderLayout.WEST);
        panel.add(new JLabel(party.getFirstName()));
        JButton backButton = new JButton("Go to profile");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO return to the main page
            }
        });
        panel.add(backButton, BorderLayout.EAST);
        panel.setBorder(new LineBorder(Color.BLACK, 3));
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
//        for(Listing listing : listings){
//            panel.add(new MinimizedListingPanel(image, 404.00));
//        }
        for(int i = 0;i < 14;i++){
            GridBagConstraints c =new GridBagConstraints();
            c.gridx = i%6;
            c.gridy = i/6;
            c.anchor = GridBagConstraints.NORTH;
            c.insets = new Insets(10,10,10,10);
            panel.add(new MinimizedListingPanel(image, 403.00),c);
        }
        panel.setVisible(true);
        return panel;
    }

    private JPanel westPanel(){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c;

        c = generateConstraints(0,0);
        panel.add(new JLabel("Name"),c);

        c = generateConstraints(1,0);
        nameField = new JTextField();
        panel.add(nameField,c);

        c = generateConstraints(0, 1);
        panel.add(new JLabel("Material"), c);

        materialBox = new JComboBox<>();
        for(Listing.MaterialType material : Listing.MaterialType.values())
            materialBox.addItem(material.name());
        c = generateConstraints(1,1);
        panel.add(materialBox,c);

        c = generateConstraints(0, 2);
        panel.add(new JLabel("Color"),c);

        colorBox = new JComboBox();
        for(Listing.JewelryColor color : Listing.JewelryColor.values()) {
            colorBox.addItem(color.name());
        }
        c = generateConstraints(1, 2);
        panel.add(colorBox,c);

        c = generateConstraints(0,3);
        panel.add(new JLabel("Type"),c);

        typeBox = new JComboBox();
        for(Listing.JewelryType type : Listing.JewelryType.values())
            typeBox.addItem(type.name());
        c = generateConstraints(1, 3);
        panel.add(typeBox,c);

        JButton searchButton = new JButton("Search");
        c = generateConstraints(0, 4);
        c.gridwidth = 2;
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO Fire off a querry
                //this should get the value from the querry
                List<Listing> listings1= null;
                listings = listings1;
                //redraw();
                //the redraw method is still not done, it should just update the central part of the panel so it fits
                //the new listings


            }
        });
        panel.add(searchButton,c);

        panel.setBorder(BorderFactory.createLineBorder(Color.black));

        return panel;
    }

    private GridBagConstraints generateConstraints(int x, int y){
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = x;
        c.gridy = y;
        c.insets = new Insets(10,15,10,15);
        c.ipadx = 10;
        c.ipady = 10;
        c.fill = GridBagConstraints.HORIZONTAL;
        return c;
    }
}
