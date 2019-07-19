package football.focus.footfragments.fixtures;

public class Fixture
{

    private String day;
    private String date;
    private String opponent;
    private String score;
    private String scoreO;
    private String homeAway;
    private int opponentLogo;

    public Fixture(String day, String date, String opponent, String score, String scoreO, String homeAway, int opponentLogo) {
        this.day = day;
        this.date = date;
        this.opponent = opponent;
        this.score = score;
        this.scoreO = scoreO;
        this.opponentLogo = opponentLogo;
        this.homeAway = homeAway;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScoreO() {
        return scoreO;
    }

    public void setScoreO(String scoreO) {
        this.scoreO = scoreO;
    }

    public String getHomeAway() { return homeAway; }

    public void setHomeAway(String homeAway) { this.homeAway = homeAway; }

    public int getOpponentLogo() { return opponentLogo; }

    public void setOpponentLogo(int opponentLogo) { this.opponentLogo = opponentLogo; }
}
