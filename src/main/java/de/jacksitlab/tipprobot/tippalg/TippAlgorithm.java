package de.jacksitlab.tipprobot.tippalg;

import de.jacksitlab.tipprobot.data.Match;
import de.jacksitlab.tipprobot.data.MatchTipp;

public interface TippAlgorithm {

	public MatchTipp getTipp(Match m);
}