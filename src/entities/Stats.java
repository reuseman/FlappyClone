package entities;

import utils.Saver;

/**
 * Created by alex on 27/06/17.
 */
public class Stats {

    private Saver saver;
    private int currentScore;
    private int currentMatches;
    private int bestScore;
    private int totalScore;
    private int totalJumps;
    private int totalMatches;
    private int totalDeaths;

    public Stats() {
        String temp;
        this.currentScore = 0;
        this.currentMatches = 1;
        this.saver = new Saver("/flappy.save");

        if ((temp = this.saver.getValue("BEST_SCORE")) != null)
            this.bestScore = Integer.parseInt(temp);
        else {
            this.bestScore = 0;
            this.saver.writeLine("BEST_SCORE", "" + 0);
        }
        if ((temp = this.saver.getValue("TOTAL_SCORE")) != null)
            this.totalScore = Integer.parseInt(temp);
        else {
            this.totalScore = 0;
            this.saver.writeLine("TOTAL_SCORE", "" + 0);
        }
        if ((temp = this.saver.getValue("TOTAL_JUMPS")) != null)
            this.totalJumps = Integer.parseInt(temp);
        else {
            this.totalJumps = 0;
            this.saver.writeLine("TOTAL_JUMPS", "" + 0);
        }
        if ((temp = this.saver.getValue("TOTAL_MATCHES")) != null)
            this.totalMatches = Integer.parseInt(temp);
        else {
            this.totalMatches = 0;
            this.saver.writeLine("TOTAL_MATCHES", "" + 0);
        }
        if ((temp = this.saver.getValue("TOTAL_DEATHS")) != null)
            this.totalDeaths = Integer.parseInt(temp);
        else {
            this.totalDeaths = 0;
            this.saver.writeLine("TOTAL_DEATHS", "" + 0);
        }
    }

    public void save() {
        this.saver.writeLine("TOTAL_MATCHES", "" + this.totalMatches);
        this.saver.writeLine("TOTAL_DEATHS", "" + this.totalDeaths);
        this.saver.writeLine("TOTAL_JUMPS", "" + this.totalJumps);
        this.saver.writeLine("TOTAL_SCORE", "" + this.totalScore);
        if (this.currentScore > this.bestScore) {
            this.bestScore = this.currentScore;
            this.saver.writeLine("BEST_SCORE", "" + this.currentScore);
        }
    }

    public int getCurrentScore() {
        return this.currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public void addCurrentScore(int currentScore) {
        this.currentScore += currentScore;
    }

    public int getCurrentMatches() {
        return this.currentMatches;
    }

    public void setCurrentMatches(int currentMatches) {
        this.currentMatches = currentMatches;
    }

    public void addCurrentMatches(int currentMatches) {
        this.currentMatches += currentMatches;
    }

    public int getBestScore() {
        return this.bestScore;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    public int getTotalScore() {
        return this.totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public void addTotalScore(int totalScore) {
        this.totalScore += totalScore;
    }

    public int getTotalJumps() {
        return this.totalJumps;
    }

    public void setTotalJumps(int totalJumps) {
        this.totalJumps = totalJumps;
    }

    public void addTotalJumps(int totalJumps) {
        this.totalJumps += totalJumps;
    }

    public int getTotalMatches() {
        return this.totalMatches;
    }

    public void setTotalMatches(int totalMatches) {
        this.totalMatches = totalMatches;
    }

    public void addTotalMatches(int totalMatches) {
        this.totalMatches += totalMatches;
    }

    public int getTotalDeaths() {
        return this.totalDeaths;
    }

    public void setTotalDeaths(int totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public void addTotalDeaths(int totalDeaths) {
        this.totalDeaths += totalDeaths;
    }
}
