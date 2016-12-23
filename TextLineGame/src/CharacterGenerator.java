import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridLayout;
//import java.awt.Dialog.ModalityType;

import javax.swing.JTextField;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.awt.event.ActionEvent;

public class CharacterGenerator extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 55343987932672737L;
	private final JPanel contentPanel = new JPanel();
	
	public boolean charOK = true;
	
	public String playerName;
	public Actor.Gender playerGender;
	
	public Player.ClassType playerClass;
	public HashMap<String, Integer> playerAttribs;
	private JTextField textField;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CharacterGenerator dialog = new CharacterGenerator();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CharacterGenerator() {
		//ensure that when the dialog box opens, the rest of the program stops
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModal(true);
		
		setTitle("Generate Character");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JLabel lblNewLabel = new JLabel("Create Your Character");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 24));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblNewLabel, BorderLayout.NORTH);
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new GridLayout(0, 2, 0, 0));
			{
				JLabel lblNewLabel_1 = new JLabel("Name:");
				lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
				lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
				panel.add(lblNewLabel_1);
			}
			{
				textField = new JTextField();
				panel.add(textField);
				textField.setColumns(20);
			}
			{
				JLabel lblNewLabel_2 = new JLabel("Gender:");
				lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
				lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
				panel.add(lblNewLabel_2);
			}
			{
				JPanel genderPanel = new JPanel();
				panel.add(genderPanel);
				ButtonGroup gendGroup = new ButtonGroup();
				genderPanel.setLayout(new GridLayout(0, 1, 0, 0));
				{
					JRadioButton rdbtnMale = new JRadioButton("Male");
					rdbtnMale.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							playerGender = Actor.Gender.MALE;
						}
					});
					rdbtnMale.setFont(new Font("Tahoma", Font.BOLD, 15));
					genderPanel.add(rdbtnMale);
					gendGroup.add(rdbtnMale);
				}
				{
					JRadioButton rdbtnFemale = new JRadioButton("Female");
					rdbtnFemale.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							playerGender = Actor.Gender.FEMALE;
						}
					});
					rdbtnFemale.setFont(new Font("Tahoma", Font.BOLD, 15));
					genderPanel.add(rdbtnFemale);
					gendGroup.add(rdbtnFemale);
				}
				{
					JRadioButton rdbtnNeither = new JRadioButton("Neither");
					rdbtnNeither.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							playerGender = Actor.Gender.NEITHER;
						}
					});
					rdbtnNeither.setFont(new Font("Tahoma", Font.BOLD, 15));
					genderPanel.add(rdbtnNeither);
					gendGroup.add(rdbtnNeither);
				}
				
				
			}
			{
				JLabel lblNewLabel_3 = new JLabel("Class:");
				lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
				lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
				panel.add(lblNewLabel_3);
			}
			{
				JPanel classPanel = new JPanel();
				panel.add(classPanel);
				ButtonGroup clsGroup = new ButtonGroup();
				classPanel.setLayout(new GridLayout(0, 2, 0, 0));
				{
					JRadioButton rdbtnBrawler = new JRadioButton("Brawler");
					rdbtnBrawler.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							playerClass = Player.ClassType.BRAWLER;
						}
					});
					rdbtnBrawler.setFont(new Font("Tahoma", Font.BOLD, 13));
					classPanel.add(rdbtnBrawler);
					clsGroup.add(rdbtnBrawler);
				}
				{
					JRadioButton rdbtnShooter = new JRadioButton("Shooter");
					rdbtnShooter.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							playerClass = Player.ClassType.SHOOTER;
						}
					});
					rdbtnShooter.setFont(new Font("Tahoma", Font.BOLD, 13));
					classPanel.add(rdbtnShooter);
					clsGroup.add(rdbtnShooter);
				}
				{
					JRadioButton rdbtnTank = new JRadioButton("Tank");
					rdbtnTank.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							playerClass = Player.ClassType.TANK;
						}
					});
					rdbtnTank.setFont(new Font("Tahoma", Font.BOLD, 13));
					classPanel.add(rdbtnTank);
					clsGroup.add(rdbtnTank);
				}
				{
					JRadioButton rdbtnThief = new JRadioButton("Thief");
					rdbtnThief.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							playerClass = Player.ClassType.THIEF;
						}
					});
					rdbtnThief.setFont(new Font("Tahoma", Font.BOLD, 13));
					classPanel.add(rdbtnThief);
					clsGroup.add(rdbtnThief);
				}
				{
					JRadioButton rdbtnBalanced = new JRadioButton("Balanced");
					rdbtnBalanced.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							playerClass = Player.ClassType.BALANCED;
						}
					});
					rdbtnBalanced.setFont(new Font("Tahoma", Font.BOLD, 13));
					classPanel.add(rdbtnBalanced);
					clsGroup.add(rdbtnBalanced);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						charOK = true;
						playerName = textField.getText();
						playerAttribs = Stats.getStartingBaseStats(playerClass);
						setVisible(false);
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

}
