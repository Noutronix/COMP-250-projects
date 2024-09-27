package assignment3;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;

public class Block {
	private int xCoord;
	private int yCoord;
	private int size; // height/width of the square
	private int level; // the root (outer most block) is at level 0
	private int maxDepth;
	private Color color;

	private Block[] children; // {UR, UL, LL, LR}

	public static Random gen = new Random(2);

	/*
	 * These two constructors are here for testing purposes.
	 */
	public Block() {
	}

	public static void main(String[] args) {
		Block blockDepth2 = new Block(0, 2);
		blockDepth2.updateSizeAndPosition(4, 0, 0);
		blockDepth2.printColoredBlock();

	}

	public Block(int x, int y, int size, int lvl, int maxD, Color c, Block[] subBlocks) {
		this.xCoord = x;
		this.yCoord = y;
		this.size = size;
		this.level = lvl;
		this.maxDepth = maxD;
		this.color = c;
		this.children = subBlocks;
	}

	/*
	 * Creates a random block given its level and a max depth.
	 * 
	 * xCoord, yCoord, size, and highlighted should not be initialized (i.e. they
	 * will all be initialized by default)
	 */
	public Block(int lvl, int maxDepth) {
		// works
		this.maxDepth = maxDepth;
		this.level = lvl;

		if (lvl < maxDepth && Math.exp(-0.25 * lvl) > gen.nextDouble()) { // subdivide
			this.children = new Block[4];
			for (int i = 0; i < 4; i++) {
				this.children[i] = new Block(lvl + 1, maxDepth);
			}
		}

		else { // don't subdivide
			this.children = new Block[0];
			this.color = GameColors.BLOCK_COLORS[gen.nextInt(4)];
		}

	}
	
	private Block copy() {
		Block[] newChildren = new Block[this.children.length];
		
		Block b = new Block(this.xCoord, 
				this.yCoord, 
				this.size, 
				this.level, 
				this.maxDepth,
				this.color,
				newChildren);
		for (int i=0; i<this.children.length; i++) {
			newChildren[i] = this.children[i].copy();
		}
		if (this.level==0) {
			b.updateSizeAndPosition((int)Math.pow(2, this.maxDepth), this.xCoord, this.yCoord);
		}
		return b;
		
	}

	/*
	 * Updates size and position for the block and all of its sub-blocks, while
	 * ensuring consistency between the attributes and the relationship of the
	 * blocks.
	 * 
	 * The size is the height and width of the block. (xCoord, yCoord) are the
	 * coordinates of the top left corner of the block.
	 */
	public void updateSizeAndPosition(int size, int xCoord, int yCoord) {
		// works

		// input validation
		if (size < 0) {
			throw new IllegalArgumentException();
		}

		double exponent = Math.log(size) / Math.log(2);
		if (Math.abs(Math.floor(exponent) - exponent) > 0.001 || exponent < this.maxDepth - this.level) {
			throw new IllegalArgumentException();
		}
		// initialization
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.size = size;
		if (this.children.length == 4) {
			this.children[0].updateSizeAndPosition(size / 2, xCoord + size / 2, yCoord);
			this.children[1].updateSizeAndPosition(size / 2, xCoord, yCoord);
			this.children[2].updateSizeAndPosition(size / 2, xCoord, yCoord + size / 2);
			this.children[3].updateSizeAndPosition(size / 2, xCoord + size / 2, yCoord + size / 2);
		}
	}

	/*
	 * Returns a List of blocks to be drawn to get a graphical representation of
	 * this block.
	 * 
	 * This includes, for each undivided Block: - one BlockToDraw in the color of
	 * the block - another one in the FRAME_COLOR and stroke thickness 3
	 * 
	 * Note that a stroke thickness equal to 0 indicates that the block should be
	 * filled with its color.
	 * 
	 * The order in which the blocks to draw appear in the list does NOT matter.
	 */
	public ArrayList<BlockToDraw> getBlocksToDraw() {
		if (this.children.length == 0) {
			ArrayList<BlockToDraw> arr = new ArrayList<BlockToDraw>();
			arr.add(new BlockToDraw(this.color, this.xCoord, this.yCoord, this.size, 0));
			arr.add(new BlockToDraw(GameColors.FRAME_COLOR, this.xCoord, this.yCoord, this.size, 3));
			return arr;
		} else {
			ArrayList<BlockToDraw> arr = new ArrayList<BlockToDraw>();
			for (Block child : this.children) {
				for (BlockToDraw draw : child.getBlocksToDraw()) {
					arr.add(draw);
				}
			}
			return arr;
		}

	}

	/*
	 * This method is provided and you should NOT modify it.
	 */
	public BlockToDraw getHighlightedFrame() {
		return new BlockToDraw(GameColors.HIGHLIGHT_COLOR, this.xCoord, this.yCoord, this.size, 5);
	}

	/*
	 * Return the Block within this Block that includes the given location and is at
	 * the given level. If the level specified is lower than the lowest block at the
	 * specified location, then return the block at the location with the closest
	 * level value.
	 * 
	 * The location is specified by its (x, y) coordinates. The lvl indicates the
	 * level of the desired Block. Note that if a Block includes the location (x,
	 * y), and that Block is subdivided, then one of its sub-Blocks will contain the
	 * location (x, y) too. This is why we need lvl to identify which Block should
	 * be returned.
	 * 
	 * Input validation: - this.level <= lvl <= maxDepth (if not throw exception) -
	 * if (x,y) is not within this Block, return null.
	 */
	public Block getSelectedBlock(int x, int y, int lvl) {
		// works
		if (this.level > lvl) {
			throw new IllegalArgumentException();
		}
		if (lvl > maxDepth) {
			throw new IllegalArgumentException();
		}

		if (this.level == lvl || this.children.length == 0) { // base case
			return this;
		} else { // inductive step
			for (Block child : this.children) {
				if (child.xCoord > x || child.xCoord + child.size - 1 < x) {
					continue;
				}
				if (child.yCoord > y || child.yCoord + child.size - 1 < y) {
					continue;
				}
				return child.getSelectedBlock(x, y, lvl);
			}
			return null;
		}
	}

	/*
	 * Swaps the child Blocks of this Block. If input is 1, swap vertically. If 0,
	 * swap horizontally. If this Block has no children, do nothing. The swap should
	 * be propagate, effectively implementing a reflection over the x-axis or over
	 * the y-axis.
	 * 
	 */
	public void reflect(int direction) {
		if (direction != 0 && direction != 1) {
			throw new IllegalArgumentException();
		}

		if (this.children.length == 0) { // useless
			return;
		}

		else {
			Block[] arr = new Block[4];
			for (int i = 0; i < 4; i++) {
				this.children[i].reflect(direction);
				arr[i] = this.children[i];
			}
			if (direction == 0) {
				this.children[0] = arr[3];
				this.children[3] = arr[0];
				this.children[1] = arr[2];
				this.children[2] = arr[1];
			} else {
				this.children[0] = arr[1];
				this.children[1] = arr[0];
				this.children[2] = arr[3];
				this.children[3] = arr[2];
			}
			this.updateSizeAndPosition(this.size, this.xCoord, this.yCoord);
		}
		
	}

	/*
	 * Rotate this Block and all its descendants. If the input is 1, rotate
	 * clockwise. If 0, rotate counterclockwise. If this Block has no children, do
	 * nothing.
	 */
	public void rotate(int direction) {
		// works
		if (direction != 0 && direction != 1) {
			throw new IllegalArgumentException();
		}

		if (this.children.length == 0) { // useless
			return;
		} else {
			Block[] arr = new Block[4];
			for (int i = 0; i < 4; i++) {
				this.children[i].rotate(direction);
				arr[i] = this.children[i];
			}

			if (direction == 1) {
				this.children[0] = arr[1];
				this.children[1] = arr[2];
				this.children[2] = arr[3];
				this.children[3] = arr[0];
			} else {
				this.children[0] = arr[3];
				this.children[1] = arr[0];
				this.children[2] = arr[1];
				this.children[3] = arr[2];
			}

		}
		this.updateSizeAndPosition(this.size, this.xCoord, this.yCoord);
	}

	/*
	 * Smash this Block.
	 * 
	 * If this Block can be smashed, randomly generate four new children Blocks for
	 * it. (If it already had children Blocks, discard them.) Ensure that the
	 * invariants of the Blocks remain satisfied.
	 * 
	 * A Block can be smashed iff it is not the top-level Block and it is not
	 * already at the level of the maximum depth.
	 * 
	 * Return True if this Block was smashed and False otherwise.
	 * 
	 */
	public boolean smash() {
		if (this.level == 0 || this.level == this.maxDepth) {
			return false;
		} else {
			this.children = new Block[4];
			for (int i = 0; i < 4; i++) {
				this.children[i] = new Block(this.level + 1, this.maxDepth);
			}
			this.updateSizeAndPosition(this.size, this.xCoord, this.yCoord);
			return true;
		}
	}

	/*
	 * Return a two-dimensional array representing this Block as rows and columns of
	 * unit cells.
	 * 
	 * Return and array arr where, arr[i] represents the unit cells in row i,
	 * arr[i][j] is the color of unit cell in row i and column j.
	 * 
	 * arr[0][0] is the color of the unit cell in the upper left corner of this
	 * Block.
	 */
	
	
	
	public Color[][] flatten() {
		return this.copy().flattenHelper();
	}
	
	private Color[][] flattenHelper(){
		if (this.children.length == 4) {
			Color[][] flatArr = new Color[this.size][this.size];
			for (int i = 0; i < 4; i++) {
				Block child = this.children[i];
				Color[][] flatArrChild = child.flatten();
				for (int x = 0; x < child.size; x++) {
					for (int y = 0; y < child.size; y++) {
						flatArr[child.yCoord + y - this.yCoord][child.xCoord + x - this.xCoord] = flatArrChild[y][x];
					}
				}
			}
			return flatArr;
		} else {
			Color[][] flatArr = new Color[this.size][this.size];
			for (int x = 0; x < this.size; x++) {
				for (int y = 0; y < this.size; y++) {
					flatArr[y][x] = this.color;
				}
			}
			return flatArr;
		}
	}

	// These two get methods have been provided. Do NOT modify them.
	public int getMaxDepth() {
		return this.maxDepth;
	}

	public int getLevel() {
		return this.level;
	}

	/*
	 * The next 5 methods are needed to get a text representation of a block. You
	 * can use them for debugging. You can modify these methods if you wish.
	 */
	public String toString() {
		return String.format("pos=(%d,%d), size=%d, level=%d", this.xCoord, this.yCoord, this.size, this.level);
	}

	public void printBlock() {
		this.printBlockIndented(0);
	}

	private void printBlockIndented(int indentation) {
		String indent = "";
		for (int i = 0; i < indentation; i++) {
			indent += "\t";
		}

		if (this.children.length == 0) {
			// it's a leaf. Print the color!
			String colorInfo = GameColors.colorToString(this.color) + ", ";
			System.out.println(indent + colorInfo + this);
		} else {
			System.out.println(indent + this);
			for (Block b : this.children)
				b.printBlockIndented(indentation + 1);
		}
	}

	private static void coloredPrint(String message, Color color) {
		System.out.print(GameColors.colorToANSIColor(color));
		System.out.print(message);
		System.out.print(GameColors.colorToANSIColor(Color.WHITE));
	}

	public void printColoredBlock() {
		Color[][] colorArray = this.flatten();
		for (Color[] colors : colorArray) {
			for (Color value : colors) {
				String colorName = GameColors.colorToString(value).toUpperCase();
				if (colorName.length() == 0) {
					colorName = "\u2588";
				} else {
					colorName = colorName.substring(0, 1);
				}
				coloredPrint(colorName, value);
			}
			System.out.println();
		}
	}

}
