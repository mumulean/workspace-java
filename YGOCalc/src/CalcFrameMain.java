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
	 * Class fields
	 */
	private static final long serialVersionUID = 4160960510158245192L; //so I don't have a warning for no reason
	private JPanel contentPane; //the content pane
	
	//parallel arrays for the players and panels
	protected Player[] players;
	protected PlayerPanel[] panels;
	
	//starting life points, which all players share
	protected int startLP;
	
	private JButton btnDRAW; //if a Duel manages to end in a draw, set all LP to 0
	private JButton btnResetGame; //reset current game state to how it was when it began
	private JPanel playerArea; //a place where the PlayerPanel array goes
	private JScrollPane scrollPane; //holds the playerArea

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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override //force the viewport to refresh itself (so the controls on each player panel don't "smudge")
			public void mouseEntered(MouseEvent arg0) {
				updateUI();
			}
		});

		scrollPane.addMouseWheelListener(new MouseWheelListener() {
			//same as above listener, except when the mouse wheel is moved.
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
		
		JButton btnNewGame = new JButton("New Game"); //button to cause a new dialog box to appear.
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//open the new game dialog box
				//if the players are confirmed, only then will the UI update
				if (newGame()) {
					playerArea.removeAll(); //remove the previous game completely
					revalidate(); //force UI to update
					//redeclare players and panels accordingly
					panels = new PlayerPanel[players.length];
					//For each player, initialize a PlayerPanel 
					//and add it to the panel in the scroll pane.
					for (int i = 0; i < players.length; i++) {
						panels[i] = new PlayerPanel(players[i].name, players[i].getStartLP());
						playerArea.add(panels[i]);
					}
					revalidate(); //force UI to update again
					btnDRAW.setEnabled(true); //set these two buttons to enabled
					btnResetGame.setEnabled(true);
				}
			}
		});
		buttonPanel.add(btnNewGame);
		
		btnDRAW = new JButton("DRAW");
		btnDRAW.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//iterate over player panels and call the DRAW function
				//so that 0 LP is a thing for all at the same time.
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
				for (int i = 0; i < panels.length; i++) {
					panels[i].reset();
				}
				updateUI();
			}
		});
		btnResetGame.setEnabled(false);
		buttonPanel.add(btnResetGame);
	}
	
	private void updateUI() {
		if (panels != null) { //make sure there are panels to refresh
			//refresh the view on request.
			playerArea.updateUI();
			scrollPane.revalidate();
			//same for each of these.
			for (int i = 0; i < panels.length; i++) {
				panels[i].revalidate();
				panels[i].updateUI();
			}
		}
	}
	
	private boolean newGame() {
		//create the NewGameDialog box
		NewGameDialog dialog = new NewGameDialog();
		dialog.setVisible(true); //show the dialog box
		
		//return its value once the user is done.
		if (dialog.confirmed) { //if the user confirms their load, then alter the players array
			players = dialog.getPlayers();
		}
		return dialog.confirmed; //return the confirmed value regardless
	}
	
}
