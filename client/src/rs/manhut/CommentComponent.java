package rs.manhut;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.StrokeBorder;

public class CommentComponent extends JPanel {
	
	
	public CommentComponent() {
		this.setLayout(new GridBagLayout());
		
		
		GridBagConstraints c = new GridBagConstraints();
		
		this.setBorder(new StrokeBorder(new BasicStroke(1), Color.BLACK));
		
		JLabel authorLabel = new JLabel("AUTHOR");
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weightx = 0.5;
		c.weighty = 0.1;
		
		this.add(authorLabel, c);
		
		
		c = new GridBagConstraints();
		JTextArea commentLabel = new JTextArea("This is a \n multiline \n comment");
		commentLabel.setEditable(false);
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.weightx = 0.5;
		c.weighty = 0.4;
		
		this.add(commentLabel, c);
		
		c = new GridBagConstraints();
		JLabel dateTimeLabel = new JLabel("13/08/2014 14:55");
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.weighty = 0.1;
		
		this.add(dateTimeLabel, c);
		
		c = new GridBagConstraints();
		JButton button = new JButton("Reply");
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 1;
		c.gridy = 2;
		c.weightx = 0.1;
		c.weighty = 0.1;
		
		this.add(button, c);
	}
}
