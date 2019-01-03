package de.jacksitlab.tipprobot.data;

public class TeamStats {

	private final int teamId;
	private int games;
	private int wins;
	private int draws;
	private int looses;
	private int goals;
	private int goalsAgainst;

	public TeamStats(int id) {
		this.teamId = id;
		this.goals = 0;
		this.goalsAgainst = 0;
		this.games = 0;
		this.wins = 0;
		this.draws = 0;
		this.looses = 0;
	}

	public void incGames() {
		this.games++;
	}

	void incWins() {
		this.wins++;
	}

	public void incLooses() {
		this.looses++;
	}

	public void incDraws() {
		this.draws++;
	}

	public int getPoints() {
		return 3 * this.wins + this.draws;
	}
	public int getLooses() {
		return this.looses;
	}
	public void addGoals(int goals, int goalsAgainst) {
		this.goals += goals;
		this.goalsAgainst += goalsAgainst;
	}

	public int getId() {
		return this.teamId;
	}

	public int getGoalsMean() {
		return this.games <= 0 ? 0 : Math.round(this.goals / this.games);
	}

	public int getGoalsAgainstMean() {
		return this.games <= 0 ? 0 : Math.round(this.goalsAgainst / this.games);
	}

	public void clear() {
		this.games = 0;
		this.goals = 0;
		this.goalsAgainst = 0;
		this.wins = 0;
		this.draws = 0;
		this.looses = 0;
	}

	public void addMatch(Match match) {

		if (match.hasResult()) {
			this.incGames();
			if (match.homeTeam.getId() == this.teamId) {
				this.addGoals(match.getResult().getHomePoints(), match.getResult().getGuestPoints());
				if (match.getResult().homeTeamHasWon())
					this.incWins();
				else if(match.getResult().guestTeamHasWon())
					this.incLooses();
			} else {
				this.addGoals(match.getResult().getGuestPoints(), match.getResult().getHomePoints());
				if (match.getResult().homeTeamHasWon())
					this.incLooses();
				else if(match.getResult().guestTeamHasWon())
					this.incWins();
			}
			if (match.getResult().isADraw())
				this.incDraws();

		}
	}

	public float getPointsMean() {
		return this.games==0?0:((float)this.getPoints()/(float)this.games);
	}

}
