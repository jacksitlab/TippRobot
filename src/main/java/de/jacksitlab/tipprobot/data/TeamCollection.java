package de.jacksitlab.tipprobot.data;

import java.util.ArrayList;

public class TeamCollection extends ArrayList<Team> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3874852597344448570L;

	public Team getById(int id) {
		for (Team t : this) {
			if (t.getId() == id)
				return t;
		}
		return null;
	}

}
