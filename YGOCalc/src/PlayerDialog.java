import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JProgressBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PlayerDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2650786186996776303L;
	private final JPanel contentPanel = new JPanel();
	JProgressBar hpBar;
	JLabel lblName;
	JSpinner customValSpinner;
	private Player p;
	private boolean keepThis = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			PlayerDialog dialog = new PlayerDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public PlayerDialog() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			lblName = new JLabel("Player");
			lblName.setHorizontalAlignment(SwingConstants.CENTER);
			lblName.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 30));
			contentPanel.add(lblName, BorderLayout.NORTH);
		}
		{
			JPanel changePanel = new JPanel();
			contentPanel.add(changePanel, BorderLayout.SOUTH);
			changePanel.setLayout(new GridLayout(3, 4, 0, 0));
			{
				JButton btnMin1000 = new JButton("-1000");
				btnMin1000.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						p.modLPByValue(-1000);
						updateUI();
					}
				});
				changePanel.add(btnMin1000);
			}
			{
				JButton btnMin500 = new JButton("-500");
				btnMin500.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						p.modLPByValue(-500);
						updateUI();
					}
				});
				changePanel.add(btnMin500);
			}
			{
				JButton btnMin100 = new JButton("-100");
				btnMin100.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						p.modLPByValue(-100);
						updateUI();
					}
				});
				changePanel.add(btnMin100);
			}
			{
				JButton btnMin50 = new JButton("-50");
				btnMin50.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						p.modLPByValue(-50);
						updateUI();
					}
				});
				changePanel.add(btnMin50);
			}
			{
				JButton btnPlus1000 = new JButton("+1000");
				btnPlus1000.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						p.modLPByValue(1000);
						updateUI();
					}
				});
				changePanel.add(btnPlus1000);
			}
			{
				JButton btnPlus500 = new JButton("+500");
				btnPlus500.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						p.modLPByValue(500);
						updateUI();
					}
				});
				changePanel.add(btnPlus500);
			}
			{
				JButton btnPlus100 = new JButton("+100");
				btnPlus100.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						p.modLPByValue(100);
						updateUI();
					}
				});
				changePanel.add(btnPlus100);
			}
			{
				JButton btnPlus50 = new JButton("+50");
				btnPlus50.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						p.modLPByValue(50);
						updateUI();
					}
				});
				changePanel.add(btnPlus50);
			}
			{
				JButton btnCustom = new JButton("Use Value");
				btnCustom.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						p.modLPByValue((Integer)customValSpinner.getValue());
						updateUI();
					}
				});
				btnCustom.setToolTipText("Uses the spinner on the right to decide a custom value (positive or negative) to add to the player's LP.");
				changePanel.add(btnCustom);
			}
			{
				customValSpinner = new JSpinner();
				customValSpinner.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(10)));
				changePanel.add(customValSpinner);
			}
			{
				JButton btnSetTo = new JButton("Set LP To");
				btnSetTo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int temp = (Integer)customValSpinner.getValue();
						if (temp >= 0) {
							p.setLP(temp);
							updateUI();
						}
						else {
							//show error
						}
					}
				});
				btnSetTo.setToolTipText("Uses the spinner on the left to set the player's LP to a (positive) value (or 0).");
				changePanel.add(btnSetTo);
			}
			{
				JButton btnHalve = new JButton("Halve LP");
				btnHalve.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						p.halveLP();
						updateUI();
					}
				});
				changePanel.add(btnHalve);
			}
		}
		{
			hpBar = new JProgressBar();
			hpBar.setFont(new Font("Arial Black", Font.PLAIN, 50));
			hpBar.setStringPainted(true);
			hpBar.setBackground(new Color(0, 0, 0));
			hpBar.setForeground(new Color(0, 255, 0));
			hpBar.setMaximum(8000);
			contentPanel.add(hpBar, BorderLayout.CENTER);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						keepThis = true;
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
						keepThis = false;
						setVisible(false);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public void setPlayerValues(String name, int curLp, int startLP){
		setTitle(name);
		p = new Player(name, startLP);
		hpBar.setMaximum(startLP);
		p.setLP(curLp);
		lblName.setText(name);
		updateUI();
		
	}
	
	public Player getPlayerValues() {
		return p;
	}
	
	public boolean changeOK() {
		return keepThis;
	}

	private void updateUI() {
		if (p != null) {
			double percent = p.getPercent();
			if (percent == 1.0 && p.getLP() > p.getStartLP()) {
				hpBar.setForeground(new Color(0, 128, 255));
			}
			else if (percent >= 0.67 && percent <= 1.0) {
				hpBar.setForeground(new Color(0, 255, 0));
			}
			else if (percent >= 0.33 && percent < 0.67) {
				hpBar.setForeground(new Color(255, 255, 0));
			}
			else if (percent > 0.0 && percent < 0.33) {
				hpBar.setForeground(new Color(255, 0, 0));
			}
			else {
				hpBar.setForeground(new Color(0, 0, 0));
			}
			hpBar.setValue(p.getLP());
			hpBar.setString(p.getLPString());
			revalidate();
		}
	}
}
