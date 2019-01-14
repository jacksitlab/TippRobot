package de.jacksitlab.tipprobot.betproviders;

import de.jacksitlab.tipprobot.data.MatchBetQuoteCollection;

public interface BaseBetProvider {

	public MatchBetQuoteCollection getBets(int liga,int gameday);
}
