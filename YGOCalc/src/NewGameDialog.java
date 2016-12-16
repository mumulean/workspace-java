import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class NewGameDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8442611739349919581L;
	private final JPanel contentPanel = new JPanel();
	private JSpinner startingLP;
	private JSpinner numOfPlayers;
	private JTextField textField1;
	private JTextField textField2;
	private JTextField textField3;
	private JTextField textField4;
	private JTextField textField5;
	private JTextField textField6;
	private JTextField textField7;
	private JTextField textField8;
	
	private Player[] retVal = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			NewGameDialog dialog = new NewGameDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public NewGameDialog() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModal(true);
		setTitle("New Game");
		setBounds(100, 100, 459, 479);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel westPanel = new JPanel();
			westPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
			contentPanel.add(westPanel, BorderLayout.WEST);
			westPanel.setLayout(new GridLayout(2, 2, 0, 0));
			{
				JLabel lblPlayers = new JLabel("# of Players:");
				westPanel.add(lblPlayers);
			}
			{
				numOfPlayers = new JSpinner();
				numOfPlayers.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {
						//update the text fields accordingly.
						int val = (Integer)numOfPlayers.getValue();
						tempDisableFields();
						switch (val)  {
							case 8:
								textField8.setEnabled(true);
							case 7:
								textField7.setEnabled(true);
							case 6:
								textField6.setEnabled(true);
							case 5:
								textField5.setEnabled(true);
							case 4:
								textField4.setEnabled(true);
							case 3:
								textField3.setEnabled(true);
							default:
								textField2.setEnabled(true);
								textField1.setEnabled(true);
						}
					}
				});
				numOfPlayers.setModel(new SpinnerNumberModel(2, 2, 8, 1));
				westPanel.add(numOfPlayers);
			}
			{
				JLabel lblStartingLP = new JLabel("Starting LP:");
				westPanel.add(lblStartingLP);
			}
			{
				startingLP = new JSpinner();
				startingLP.setModel(new SpinnerNumberModel(8000, 2000, 16000, 1000));
				westPanel.add(startingLP);
			}
		}
		{
			JPanel centerPanel = new JPanel();
			centerPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
			contentPanel.add(centerPanel, BorderLayout.CENTER);
			centerPanel.setLayout(new GridLayout(8, 2, 0, 0));
			{
				JLabel lblP1 = new JLabel("Player 1's name:");
				centerPanel.add(lblP1);
			}
			{
				textField1 = new JTextField();
				centerPanel.add(textField1);
				textField1.setColumns(16);
			}
			{
				JLabel lblP2 = new JLabel("Player 2's name:");
				centerPanel.add(lblP2);
			}
			{
				textField2 = new JTextField();
				centerPanel.add(textField2);
				textField2.setColumns(16);
			}
			{
				JLabel lblP3 = new JLabel("Player 3's name:");
				centerPanel.add(lblP3);
			}
			{
				textField3 = new JTextField();
				textField3.setEnabled(false);
				centerPanel.add(textField3);
				textField3.setColumns(16);
			}
			{
				JLabel lblP4 = new JLabel("Player 4's name:");
				centerPanel.add(lblP4);
			}
			{
				textField4 = new JTextField();
				textField4.setEnabled(false);
				centerPanel.add(textField4);
				textField4.setColumns(16);
			}
			{
				JLabel lblP5 = new JLabel("Player 5's name:");
				centerPanel.add(lblP5);
			}
			{
				textField5 = new JTextField();
				textField5.setEnabled(false);
				textField5.setColumns(16);
				centerPanel.add(textField5);
			}
			{
				JLabel lblP6 = new JLabel("Player 6's name:");
				centerPanel.add(lblP6);
			}
			{
				textField6 = new JTextField();
				textField6.setEnabled(false);
				textField6.setColumns(16);
				centerPanel.add(textField6);
			}
			{
				JLabel lblP7 = new JLabel("Player 7's name:");
				centerPanel.add(lblP7);
			}
			{
				textField7 = new JTextField();
				textField7.setEnabled(false);
				textField7.setColumns(16);
				centerPanel.add(textField7);
			}
			{
				JLabel lblP8 = new JLabel("Player 8's name:");
				centerPanel.add(lblP8);
			}
			{
				textField8 = new JTextField();
				textField8.setEnabled(false);
				centerPanel.add(textField8);
				textField8.setColumns(16);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						//Return an array of players
						String[] names = readFields();
						int start = (Integer)startingLP.getValue();
						retVal = new Player[names.length];
						for (int i = 0; i < names.length; i++) {
							retVal[i] = new Player(names[i], start);
						}
						
						setVisible(false);
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						retVal = null;
						setVisible(false);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public JSpinner getStartingLP() {
		return startingLP;
	}
	public JSpinner getNumOfPlayers() {
		return numOfPlayers;
	}
	
	public Player[] getPlayers() {
		return retVal;
	}
	
	private void tempDisableFields() {
		textField1.setEnabled(false);
		textField2.setEnabled(false);
		textField3.setEnabled(false);
		textField4.setEnabled(false);
		textField5.setEnabled(false);
		textField6.setEnabled(false);
		textField7.setEnabled(false);
		textField8.setEnabled(false);
	}
	
	private String[] readFields() {
		//update the text fields accordingly.
		int val = (Integer)numOfPlayers.getValue();
		String[] returnValue = new String[val];
		
		switch (val) {
			case 3:
				returnValue[0] = textField1.getText();
				returnValue[1] = textField2.getText();
				returnValue[2] = textField3.getText();
				break;
			case 4:
				returnValue[0] = textField1.getText();
				returnValue[1] = textField2.getText();
				returnValue[2] = textField3.getText();
				returnValue[3] = textField4.getText();
				break;
			case 5:
				returnValue[0] = textField1.getText();
				returnValue[1] = textField2.getText();
				returnValue[2] = textField3.getText();
				returnValue[3] = textField4.getText();
				returnValue[4] = textField5.getText();
				break;
			case 6:
				returnValue[0] = textField1.getText();
				returnValue[1] = textField2.getText();
				returnValue[2] = textField3.getText();
				returnValue[3] = textField4.getText();
				returnValue[4] = textField5.getText();
				returnValue[5] = textField6.getText();
				break;
			case 7:
				returnValue[0] = textField1.getText();
				returnValue[1] = textField2.getText();
				returnValue[2] = textField3.getText();
				returnValue[3] = textField4.getText();
				returnValue[4] = textField5.getText();
				returnValue[5] = textField6.getText();
				returnValue[6] = textField7.getText();
				break;
			case 8:
				returnValue[0] = textField1.getText();
				returnValue[1] = textField2.getText();
				returnValue[2] = textField3.getText();
				returnValue[3] = textField4.getText();
				returnValue[4] = textField5.getText();
				returnValue[5] = textField6.getText();
				returnValue[6] = textField7.getText();
				returnValue[7] = textField8.getText();
				break;
			default:
				returnValue[0] = textField1.getText();
				returnValue[1] = textField2.getText();
				break;
		}
		
		return returnValue;
	}
}
