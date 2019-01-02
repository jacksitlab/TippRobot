package de.jacksitlab.tipprobot.tippalg;

import de.jacksitlab.tipprobot.data.Match;
import de.jacksitlab.tipprobot.data.MatchTipp;
import de.jacksitlab.tipprobot.data.TippValidationResults;

public interface TippAlgorithm {

	public MatchTipp getTipp(Match m);

	public TippValidationResults validate();
}