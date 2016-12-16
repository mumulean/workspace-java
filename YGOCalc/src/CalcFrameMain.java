import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
//import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class CalcFrameMain extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4160960510158245192L;
	private JPanel contentPane;
	protected Player[] players;
	protected PlayerPanel[] panels;
	protected int startLP;
	private JButton btnDRAW;
	private JButton btnResetGame;
	private JPanel playerArea;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CalcFrameMain frame = new CalcFrameMain();
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
	public CalcFrameMain() {
		setTitle("Yu-Gi-Oh! Life Point Scoreboard");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				updateUI();
			}
		});

		scrollPane.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent arg0) {
				updateUI();
			}
		});
		
		scrollPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		playerArea = new JPanel();
		playerArea.setBackground(Color.BLACK);
		scrollPane.setViewportView(playerArea);
		playerArea.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel buttonPanel = new JPanel();
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerArea.removeAll();
				revalidate();
				//Open the new game dialog box
				players = newGame();
				//Should return the array of players when all is said and done
				
				if (players != null) {
					panels = new PlayerPanel[players.length];
					//Then make a player panel for each one and add it to the panel in the scroll pane.
					for (int i = 0; i < players.length; i++) {
						panels[i] = new PlayerPanel(players[i].name, players[i].getStartLP());
						playerArea.add(panels[i]);
					}
					revalidate();
					btnDRAW.setEnabled(true);
					btnResetGame.setEnabled(true);
				}
			}
		});
		buttonPanel.add(btnNewGame);
		
		btnDRAW = new JButton("DRAW");
		btnDRAW.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//iterate over player panels and call the surrender functions or whatever
				//so that 0 LP is a thing for all at the same time.
				//add DRAW function to playerPanel
				for (int i = 0; i < panels.length; i++) {
					panels[i].DRAW();
				}
				updateUI();
			}
		});
		btnDRAW.setEnabled(false);
		buttonPanel.add(btnDRAW);
		
		btnResetGame = new JButton("Reset Game");
		btnResetGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//set all players back into the game
				//add a reset function to playerPanel
				for (int i = 0; i < panels.length; i++) {
					panels[i].reset();
				}
				updateUI();
			}
		});
		btnResetGame.setEnabled(false);
		buttonPanel.add(btnResetGame);
	}

	public JButton getBtnDRAW() {
		return btnDRAW;
	}
	protected JButton getBtnResetGame() {
		return btnResetGame;
	}
	
	private void updateUI() {
		if (panels != null) {
		playerArea.updateUI();
		scrollPane.revalidate();
		
		for (int i = 0; i < panels.length; i++) {
			panels[i].revalidate();
			panels[i].updateUI();
		}
		}
	}
	
	private Player[] newGame() {
		NewGameDialog dialog = new NewGameDialog();
		dialog.setVisible(true);
		
		return dialog.getPlayers();
	}
	
}
