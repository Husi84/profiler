package mhu.playground.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mhu.playground.model.ProfileStatistic;

import org.jdesktop.swingx.JXTable;

public class Gui extends JFrame implements ActionListener {

	private static final String PROGRAMM_NAME = "Programm Parser";

	private static final long serialVersionUID = 8527661613228247098L;
	private JButton openButton;
	private JFileChooser fc;

	private ProfileLogParser profileLogParser;

	public Gui() {
		basicSetup();

		profileLogParser = new ProfileLogParser(new File("profile.txt"));

		JPanel panel = new JPanel();
		// getContentPane().add(panel);
		panel.setLayout(null);

		// // Create a file chooser
		// fc = new JFileChooser();
		//
		// // Create the open button. We use the image from the JLF
		// // Graphics Repository (but we extracted it from the jar).
		// openButton = new JButton("Open a File...");
		// openButton.setBounds(50, 60, 80, 30);
		// openButton.addActionListener(this);
		//
		// JButton quitButton = new JButton("Quit");
		// quitButton.setBounds(150, 60, 80, 30);
		//
		// quitButton.addActionListener(new ActionListener() {
		// @Override
		// public void actionPerformed(ActionEvent event) {
		// System.exit(0);
		// }
		// });
		//
		// panel.add(openButton);
		// panel.add(quitButton);

		JTable table = buildTable();
		JScrollPane scrollPane = new JScrollPane(table);

		// panel.add(scrollPane);
		getContentPane().add(scrollPane);
		this.pack();
	}

	private JTable buildTable() {
		String[] columnNames = { "Count", "min", "max", "total", "avg",
				"funktion" };

		List<Object[]> dataList = new ArrayList<Object[]>();

		for (String key : ProfileLogParser.getMap().keySet()) {
			ProfileStatistic profileStatistic = ProfileLogParser.getMap().get(
					key);

			Object[] aRow = { profileStatistic.getCounter(),
					String.format("%.6f", profileStatistic.getMinDuration()),
					String.format("%.6f", profileStatistic.getMaxDuration()),
					String.format("%.6f", profileStatistic.getTotalDuration()),
					String.format("%.6f", profileStatistic.avg()),
					key.split(" ")[2] };

			dataList.add(aRow);
		}

		Object[][] data = new Object[dataList.size()][columnNames.length];
		for (int i = 0; i < dataList.size(); i++) {
			data[i] = dataList.get(i);
		}

		JXTable table = new JXTable(data, columnNames);

		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		return table;
	}

	public void actionPerformed(ActionEvent e) {

		// Handle open button action.
		if (e.getSource() == openButton) {
			int returnVal = fc.showOpenDialog(Gui.this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				profileLogParser = new ProfileLogParser(file, new File(
						"testOutput.csv"));
			}
			// Handle save button action.
		}
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = Gui.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	private void basicSetup() {
		setTitle(PROGRAMM_NAME);
		setSize(300, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

}
