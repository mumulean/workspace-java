import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;
import java.awt.Font;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PlayerPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4718972507793552246L;
	private JProgressBar lpBar;
	private JLabel lblName;
	private Player p;

	/**
	 * Create the panel.
	 */
	public PlayerPanel() {
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setLayout(new BorderLayout(0, 0));
		
		lblName = new JLabel("Player");
		lblName.setBackground(Color.WHITE);
		lblName.setFont(new Font("Arial", Font.PLAIN, 36));
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblName, BorderLayout.NORTH);
		
		lpBar = new JProgressBar();
		lpBar.setBackground(Color.BLACK);
		lpBar.setForeground(Color.GREEN);
		lpBar.setMaximum(8000);
		lpBar.setValue(8000);
		lpBar.setStringPainted(true);
		lpBar.setFont(new Font("Arial Black", Font.PLAIN, 40));
		add(lpBar, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new GridLayout(1, 2, 0, 0));
		
		JButton btnModLP = new JButton("Modify LP");
		btnModLP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//call the function to do the PlayerDialog shebang
				doDialog();
			}
		});
		btnModLP.setToolTipText("Opens the dialog to modify this player's LP.");
		buttonPanel.add(btnModLP);
		
		JButton btnSurrender = new JButton("Surrender");
		btnSurrender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//set player's LP to 0
				p.setLP(0);
				lpBar.setValue(0);
			}
		});
		btnSurrender.setToolTipText("Sets this player's LP to zero.");
		buttonPanel.add(btnSurrender);

	}
	
	public PlayerPanel(String name, int start) {
		setPlayer(name, start);
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setLayout(new BorderLayout(0, 0));
		
		lblName = new JLabel(name);
		lblName.setFont(new Font("Arial", Font.PLAIN, 36));
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblName, BorderLayout.NORTH);
		
		lpBar = new JProgressBar();
		lpBar.setBackground(Color.BLACK);
		lpBar.setForeground(Color.GREEN);
		lpBar.setMaximum(start);
		lpBar.setValue(start);
		lpBar.setStringPainted(true);
		lpBar.setString(Integer.toString(start));
		lpBar.setFont(new Font("Arial Black", Font.PLAIN, 40));
		add(lpBar, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new GridLayout(1, 2, 0, 0));
		
		JButton btnModLP = new JButton("Modify LP");
		btnModLP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//call the function to do the PlayerDialog shebang
				doDialog();
				updateUI();
			}
		});
		btnModLP.setToolTipText("Opens the dialog to modify this player's LP.");
		buttonPanel.add(btnModLP);
		
		JButton btnSurrender = new JButton("Surrender");
		btnSurrender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DRAW();
			}
		});
		btnSurrender.setToolTipText("Sets this player's LP to zero.");
		buttonPanel.add(btnSurrender);
		
		
	}

	public JProgressBar getLpBar() {
		return lpBar;
	}
	public JLabel getLblName() {
		return lblName;
	}
	
	public void setPlayer(String name, int start) {
		p = new Player(name, start);
	}
	
	public void DRAW() {
		p.setLP(0);
		lpBar.setValue(0);
		updateUI();
	}
	
	public void reset() {
		p.setLP(lpBar.getMaximum());
		lpBar.setValue(p.getStartLP());
	}
	
	public void updateUI() {
		if (p != null) {
			double percent = p.getPercent();
			if (percent == 1.0 && p.getLP() > p.getStartLP()) {
				lpBar.setForeground(new Color(0, 128, 255));
			}
			else if (percent >= 0.67 && percent <= 1.0) {
				lpBar.setForeground(new Color(0, 255, 0));
			}
			else if (percent >= 0.33 && percent < 0.67) {
				lpBar.setForeground(new Color(255, 255, 0));
			}
			else if (percent > 0.0 && percent < 0.33) {
				lpBar.setForeground(new Color(255, 0, 0));
			}
			else {
				lpBar.setForeground(new Color(0, 0, 0));
			}
			lpBar.setValue(p.getLP());
			lpBar.setString(p.getLPString());
		}
	}
	
	private void doDialog() {
		PlayerDialog dialog = new PlayerDialog();
		dialog.setPlayerValues(p.getName(), p.getLP(), p.getStartLP());
		dialog.setVisible(true);
		
		if (dialog.changeOK()) {
			Player temp = dialog.getPlayerValues();
			p.setLP(temp.getLP());
		}
	}

}
