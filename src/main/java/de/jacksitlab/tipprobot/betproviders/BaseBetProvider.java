package de.jacksitlab.tipprobot.betproviders;

import de.jacksitlab.tipprobot.data.MatchBetQuoteCollection;

public abstract class BaseBetProvider {

	public abstract MatchBetQuoteCollection getBets(int liga,int gameday);
}
