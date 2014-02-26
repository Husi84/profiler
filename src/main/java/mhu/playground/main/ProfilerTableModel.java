package mhu.playground.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import mhu.playground.model.ProfileStatistic;

public class ProfilerTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1068278267527297040L;

	private final List<Object[]> dataList = new ArrayList<Object[]>();;

	public ProfilerTableModel(HashMap<String, ProfileStatistic> map) {
		super();
		
		for (String key : map.keySet()) {
			ProfileStatistic profileStatistic = map.get(key);

			Object[] aRow = { key, profileStatistic.getCounter(),
					profileStatistic.getMinDuration(),
					profileStatistic.getMaxDuration(),
					profileStatistic.getTotalDuration(), profileStatistic.avg() };

			dataList.add(aRow);
		}

	}

	public int getRowCount() {
		return dataList.size();
	}

	public int getColumnCount() {
		return 6;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return dataList.get(rowIndex)[columnIndex];
	}

}
