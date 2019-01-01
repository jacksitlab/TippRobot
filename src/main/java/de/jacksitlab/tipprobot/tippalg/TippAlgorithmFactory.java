package de.jacksitlab.tipprobot.tippalg;

import de.jacksitlab.tipprobot.data.LigaTable;

public class TippAlgorithmFactory {

	public static final int ID_LIGABASED = 1;

	public static TippAlgorithm getInstance(int id, LigaTable lt) {
		switch (id) {
		case ID_LIGABASED:
			return new LigaTableBasedTippAlgorithm(lt);
		default:
			return null;
		}
	}

}
