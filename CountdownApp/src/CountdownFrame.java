import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import com.toedter.calendar.JCalendar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.beans.PropertyChangeEvent;

import javax.swing.Timer;

public class CountdownFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -713919736046162562L;
	private JPanel contentPane;
	private Timer countTimer;
	private String selectedFormat;
	private Calendar target;
	private long millisecCount;
	private JCalendar calendar;
	private JLabel lblCount;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CountdownFrame frame = new CountdownFrame();
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
	public CountdownFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		countTimer = new Timer(1, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Calendar now = Calendar.getInstance();
				millisecCount = Math.abs(target.getTimeInMillis() - now.getTimeInMillis());
				updateUI();
			}
		});
		
		lblCount = new JLabel("000 00:00:00.000");
		lblCount.setFont(new Font("Arial Black", Font.PLAIN, 36));
		lblCount.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblCount, BorderLayout.NORTH);
		
		calendar = new JCalendar();
		calendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				countTimer.stop();
				target = calendar.getCalendar();
				target.set(Calendar.HOUR, 0);
				target.set(Calendar.HOUR_OF_DAY, 0);
				target.set(Calendar.MINUTE, 0);
				target.set(Calendar.SECOND, 0);
				target.set(Calendar.MILLISECOND, 0);
				countTimer.start();
			}
		});
		calendar.getDayChooser().setDay(25);
		calendar.getMonthChooser().setMonth(11);
		contentPane.add(calendar, BorderLayout.SOUTH);
		target = calendar.getCalendar();
		target.set(Calendar.HOUR, 0);
		target.set(Calendar.HOUR_OF_DAY, 0);
		target.set(Calendar.MINUTE, 0);
		target.set(Calendar.SECOND, 0);
		target.set(Calendar.MILLISECOND, 0);
		JPanel formatPanel = new JPanel();
		contentPane.add(formatPanel, BorderLayout.CENTER);
		formatPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lbl1 = new JLabel("Selected Format:");
		formatPanel.add(lbl1);
		lbl1.setFont(new Font("Arial", Font.PLAIN, 18));
		
		JComboBox comboFormats = new JComboBox();
		comboFormats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectedFormat = (String)comboFormats.getSelectedItem();
			}
		});
		comboFormats.setFont(new Font("Arial", Font.ITALIC, 18));
		comboFormats.setModel(new DefaultComboBoxModel(new String[] {"ddd hh:mm:ss.msc", "yy/mm/dd hh:mm:ss", "mmm/dd hh:mm:ss", "www/dd hh:mm:ss", "hhhh:mm:ss.msc", "mmmmmm:ss.msc", "ssssssssss", "milliseconds"}));
		comboFormats.setSelectedIndex(0);
		formatPanel.add(comboFormats);
		countTimer.start();
	}
	
	public void updateUI() {
		lblCount.setText(getLabelString());
	}
	
	private String getLabelString() {
		String retVal;

		if (selectedFormat.equals("yy/mm/dd hh:mm:ss")) {
			long ss = millisecCount / 1000 % 60;
			long mm = millisecCount / 1000 / 60 % 60;
			long hh = millisecCount / 1000 / 60 / 60 % 24;
			long dd = millisecCount / 1000 / 60 / 60 / 24 % 30;
			long mo = millisecCount / 1000 / 60 / 60 / 24 / 30 % 12;
			long yy = millisecCount / 1000 / 60 / 60 / 24 / 30 / 12;
			
			retVal = String.format("%02d/%02d/%02d %02d:%02d:%02d", yy, mo, dd, hh, mm, ss);
		}
		else if (selectedFormat.equals("mmm/dd hh:mm:ss")) {
			long ss = millisecCount / 1000 % 60;
			long mm = millisecCount / 1000 / 60 % 60;
			long hh = millisecCount / 1000 / 60 / 60 % 24;
			long dd = millisecCount / 1000 / 60 / 60 / 24 % 30;
			long mmm = millisecCount / 1000 / 60 / 60 / 24 / 30;
			
			retVal = String.format("%03d/%02d %02d:%02d:%02d", mmm, dd, hh, mm, ss);
		}
		else if (selectedFormat.equals("www/dd hh:mm:ss")) {
			long ss = millisecCount / 1000 % 60;
			long mm = millisecCount / 1000 / 60 % 60;
			long hh = millisecCount / 1000 / 60 / 60 % 24;
			long dd = millisecCount / 1000 / 60 / 60 / 24 % 7;
			long www = millisecCount / 1000 / 60 / 60 / 24 / 7;
			
			retVal = String.format("%03d/%02d %02d:%02d:%02d", www, dd, hh, mm, ss);
			
		}
		else if (selectedFormat.equals("hhhh:mm:ss.msc")) {
			long msc = millisecCount % 1000;
			long ss = millisecCount / 1000 % 60;
			long mm = millisecCount / 1000 / 60 % 60;
			long hhhh = millisecCount / 1000 / 60 / 60;
			
			retVal = String.format("%04d:%02d:%02d:%03d", hhhh, mm, ss, msc);
		}
		else if (selectedFormat.equals("mmmmmm:ss.msc")) {
			long msc = millisecCount % 1000;
			long ss = millisecCount / 1000 % 60;
			long mmmm = millisecCount / 1000 / 60;
			
			retVal = String.format("%06d:%02d.%03d", mmmm, ss, msc);
		}
		else if (selectedFormat.equals("ssssssssss")) {
			long sssss = millisecCount / 1000;
			retVal = String.format("%10d", sssss);
		}
		else if (selectedFormat.equals("milliseconds")) {
			retVal = Long.toString(millisecCount);
		}
		else {
			//ddd hh:mm:ss.msc
			
			long msc = millisecCount % 1000;
			long ss = millisecCount / 1000 % 60;
			long mm = millisecCount / 1000 / 60 % 60;
			long hh = millisecCount / 1000 / 60 / 60 % 24;
			long ddd = millisecCount / 1000 / 60 / 60 / 24;
			
			retVal = String.format("%03d %02d:%02d:%02d.%03d", ddd, hh, mm, ss, msc);
		}
		
		return retVal;
	}

}
