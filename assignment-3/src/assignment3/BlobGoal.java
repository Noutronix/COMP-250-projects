package assignment3;

import java.awt.Color;

public class BlobGoal extends Goal{

	public BlobGoal(Color c) {
		super(c);
	}

	@Override
	public int score(Block board) {
		Color[][] flatArr = board.flatten();
		boolean[][] visited = new boolean[flatArr.length][flatArr.length];
		int max = 0;
		for (int i=0; i<flatArr.length; i++) {
			for (int j=0; j<flatArr.length; j++) {
				int blobSize = this.undiscoveredBlobSize(i, j, flatArr, visited);
				if (blobSize>max) {
					max = blobSize;
				}
			}
		}
		return max;
	}

	@Override
	public String description() {
		return "Create the largest connected blob of " + GameColors.colorToString(targetGoal) 
		+ " blocks, anywhere within the block";
	}


	public int undiscoveredBlobSize(int i, int j, Color[][] unitCells, boolean[][] visited) {
		if (unitCells[i][j]!=this.targetGoal||visited[i][j]) {
			return 0;
		}
		else {
			visited[i][j] = true;
			int sum = 1;
			if (i!=0) {
				sum+=undiscoveredBlobSize(i-1, j, unitCells, visited);
			}
			if (j!=0) {
				sum+=undiscoveredBlobSize(i, j-1, unitCells, visited);
			}
			if (i+1!=unitCells.length) {
				sum+=undiscoveredBlobSize(i+1, j, unitCells, visited);
			}
			if (j+1!=unitCells.length) {
				sum+=undiscoveredBlobSize(i, j+1, unitCells, visited);
			}
			return sum;
		}
	}
}
