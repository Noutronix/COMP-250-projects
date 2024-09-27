package assignment3;

import java.awt.Color;

public class PerimeterGoal extends Goal{

	public PerimeterGoal(Color c) {
		super(c);
	}

	@Override
	public int score(Block board) {
		Color[][] flatArr = board.flatten();
		if (flatArr.length==1) {
			return 2;
		}
		
		int size = (int) Math.pow(2, board.getMaxDepth()-board.getLevel());
		int count = 0;
		for (int i=0; i<size; i++) {
			if (flatArr[0][i]==this.targetGoal) {
				count+=1;
			}
			if (flatArr[i][0]==this.targetGoal) {
				count+=1;
			}
			if (flatArr[size-1][i]==this.targetGoal) {
				count+=1;
			}
			if (flatArr[i][size-1]==this.targetGoal) {
				count+=1;
			}
		}
		return count;
	}

	@Override
	public String description() {
		return "Place the highest number of " + GameColors.colorToString(targetGoal) 
		+ " unit cells along the outer perimeter of the board. Corner cell count twice toward the final score!";
	}

}
