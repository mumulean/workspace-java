import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TLGFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1033962784015929402L;
	private JPanel contentPane;
	private JTextField cmdField;
	private JTextArea txtCommands;
	private Game game;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TLGFrame frame = new TLGFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TLGFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		txtCommands = new JTextArea();
		txtCommands.setWrapStyleWord(true);
		txtCommands.setLineWrap(true);
		txtCommands.setEditable(false);
		txtCommands.setColumns(80);
		scrollPane.setViewportView(txtCommands);
		
		JPanel southPanel = new JPanel();
		contentPane.add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblCmdLabel = new JLabel("Enter command: >");
		southPanel.add(lblCmdLabel, BorderLayout.WEST);
		
		cmdField = new JTextField();
		cmdField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String temp = cmdField.getText();
				cmdField.setText("");
				//here would be where we'd grab the results of player's
				//action as a String and place it into the textArea
				try {
					String ret = game.parseCommand(temp);
					txtCommands.append(ret);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					txtCommands.append("Error!");
				}
				
			}
		});
		southPanel.add(cmdField, BorderLayout.CENTER);
		cmdField.setColumns(100);
		
		JButton btnCommand = new JButton("Input Command");
		btnCommand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String temp = cmdField.getText();
				cmdField.setText("");
				try {
					String ret = game.parseCommand(temp);
					txtCommands.append(ret);
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
					txtCommands.append("Error!");
				}
			}
		});
		southPanel.add(btnCommand, BorderLayout.EAST);
		Main();
	}
	
	public void Main() {
		try {
			game = new Game();
			txtCommands.append(game.doLook());
		}
		catch (Exception e) {
			txtCommands.append("An error has occurred!");
			System.exit(ERROR);
		}
	}

}
