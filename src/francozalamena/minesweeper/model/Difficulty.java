package francozalamena.minesweeper.model;

public enum Difficulty {
	
	EASY(0.1), MEDIUM(0.2), HARD(0.3);
	
	private double percentageOfBombs;
	
	private Difficulty(double percentageOfBombs) {
		this.setPercentageOfBombs(percentageOfBombs);
	}

	public double getPercentageOfBombs() {
		return percentageOfBombs;
	}

	public void setPercentageOfBombs(double percentageOfBombs) {
		this.percentageOfBombs = percentageOfBombs;
	}
}
