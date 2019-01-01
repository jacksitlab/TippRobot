package de.jacksitlab.tipprobot.tippalg;

import de.jacksitlab.tipprobot.data.LigaTable;

public abstract class BaseTippAlgorithm implements TippAlgorithm
{
	protected final LigaTable table;
	public BaseTippAlgorithm(LigaTable lt)
	{
		super();
		this.table = lt;
	}
}
