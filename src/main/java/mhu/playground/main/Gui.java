package mhu.playground.main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import mhu.playground.model.ProfileStatistic;

import org.jdesktop.swingx.JXTable;

public class Gui extends JFrame implements ActionListener {

	private static final String PROGRAMM_NAME = "Programm Parser";

	private static final long serialVersionUID = 8527661613228247098L;
	private JButton openButton;
	private JFileChooser fc = new JFileChooser();

	private ProfileLogParser profileLogParser;

	private JTable buildTable;

	public static void main(String[] args) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {

		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (UnsupportedLookAndFeelException e) {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Gui gui = new Gui();
				gui.setSize(1024, 768);
				gui.setLocation(0, 0);
				gui.setVisible(true);
			}
		});
	}

	public Gui() {
		basicSetup();
		buildMenu();

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

		buildTable = buildTable();

		getContentPane().add(new JScrollPane(buildTable));
		this.pack();
	}

	private void buildMenu() {
		JMenuBar jMenuBar = new JMenuBar();
		JMenu jMenu = new JMenu("File");
		final JMenuItem openMenuItem = new JMenuItem("Open");
		final JMenuItem saveMenuItem = new JMenuItem("Save");
		final JMenuItem quitMenuItem = new JMenuItem("Quit");

		openMenuItem
				.addActionListener(new OpenFileActionListener(openMenuItem));

		saveMenuItem
				.addActionListener(new SaveFileActionListener(saveMenuItem));

		quitMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		jMenu.add(openMenuItem);
		jMenu.add(saveMenuItem);
		jMenu.add(quitMenuItem);

		jMenuBar.add(jMenu);

		this.setJMenuBar(jMenuBar);
	}

	private JTable buildTable() {
		String[] columnNames = { "Count #", "min [s]", "max [s]", "total [s]",
				"avg [s]", "funktion" };

		List<Object[]> dataList = new ArrayList<Object[]>();

		for (String key : profileLogParser.getMap().keySet()) {
			ProfileStatistic profileStatistic = profileLogParser.getMap().get(
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
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private final class SaveFileActionListener implements ActionListener {
		private final JMenuItem saveMenuItem;

		private SaveFileActionListener(JMenuItem saveMenuItem) {
			this.saveMenuItem = saveMenuItem;
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == saveMenuItem) {
				int returnVal = fc.showSaveDialog(Gui.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					try {
						profileLogParser.writeOutputFile(file);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}

		}
	}

	private final class OpenFileActionListener implements ActionListener {
		private final JMenuItem openMenuItem;

		private OpenFileActionListener(JMenuItem openMenuItem) {
			this.openMenuItem = openMenuItem;
		}

		public void actionPerformed(final ActionEvent e) {
			if (e.getSource() == openMenuItem) {
				final int returnVal = fc.showOpenDialog(Gui.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					final File file = fc.getSelectedFile();
					Gui.this.getContentPane().removeAll();
					final Dimension size = Gui.this.getSize();
					profileLogParser = new ProfileLogParser(file);
					buildTable = buildTable();

					buildTable.repaint();
					Gui.this.getContentPane().add(new JScrollPane(buildTable));
					Gui.this.repaint();
					Gui.this.pack();
					Gui.this.setSize(size);
				}
				// Handle save button action.
			}

		}
	}
}
