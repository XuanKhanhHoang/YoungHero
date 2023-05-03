package ai;

import java.util.ArrayList;

import main.GamePanel;

public class PathFinder {
	GamePanel gp;
	Node node[][];
	ArrayList<Node> openList = new ArrayList<>();
	public ArrayList<Node> pathList = new ArrayList<>();
	Node startNode, goalNode, currentNode;
	boolean goalReached = false;
	int step = 0;

	public PathFinder(GamePanel gp) {
		this.gp = gp;
		initNodes();
	}

	public void initNodes() {
		node = new Node[gp.maxWorldCol][gp.maxWorldRow];
		int col = 0, row = 0;
		while (row < gp.maxWorldRow) {
			node[col][row] = new Node(col, row);

			col++;
			if (col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}
	}

	public void resetNodes() {
		int col = 0, row = 0;
		while (row < gp.maxWorldRow) {
			node[col][row].open = false;
			node[col][row].checked = false;
			node[col][row].solid = false;
			col++;
			if (col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}
		openList.clear();
		pathList.clear();
		goalReached = false;
		step = 0;
	}

	public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
		resetNodes();
		startNode = node[startCol][startRow];
		currentNode = startNode;
		goalNode = node[goalCol][goalRow];
		openList.add(currentNode);

		int col = 0, row = 0;
		int tileNum = 0;
		int itCol, itRow;
		while (row < gp.maxWorldRow) {
			tileNum = gp.tileManager.mapTileNum[gp.currentMap][col][row];
			if (gp.tileManager.tile[tileNum].collision) {
				node[col][row].solid = true;
			}
			for (int i = 0; i < gp.tileInteractive[1].length; i++) {
				if (gp.tileInteractive[gp.currentMap][i] != null
						&& !gp.tileInteractive[gp.currentMap][i].destructible) {
					itCol = gp.tileInteractive[gp.currentMap][i].worldX / gp.TitleSize;
					itRow = gp.tileInteractive[gp.currentMap][i].worldY / gp.TitleSize;
					node[itCol][itRow].solid = true;
				}
			}
			getCost(node[col][row]);
			col++;
			if (col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}

	}

	public void getCost(Node node) {
		int xDistance = Math.abs(goalNode.col - node.col);
		int yDistance = Math.abs(goalNode.row - node.row);
		node.hCost = xDistance + yDistance;

		yDistance = Math.abs(startNode.row - node.row);
		xDistance = Math.abs(startNode.col - node.col);
		node.gCost = xDistance + yDistance;

		node.fCost = node.hCost + node.gCost;
	}

	public boolean seach() {
		int bsNodeCost = 999;
		while (!goalReached && step < 500) {
			int col = currentNode.col;
			int row = currentNode.row;
			currentNode.checked = true;
			openList.remove(currentNode);

			if (row - 1 >= 0)
				openNode(node[col][row - 1]);
			if (col - 1 >= 0)
				openNode(node[col - 1][row]);
			if (row + 1 < gp.maxWorldRow)
				openNode(node[col][row + 1]);
			if (col + 1 < gp.maxWorldCol)
				openNode(node[col + 1][row]);
			int bsNodeIndex = 0;
			for (int i = 0; i < openList.size(); i++) {
				if (openList.get(i).fCost < bsNodeCost) {
					bsNodeIndex = i;
					bsNodeCost = openList.get(i).fCost;
				} else if (openList.get(i).fCost == bsNodeCost) {
					if (openList.get(i).gCost < openList.get(bsNodeIndex).gCost) {
						bsNodeIndex = i;
					}
				}
			}
			if (currentNode == goalNode) {
				goalReached = true;
				trackthePath();
			}
			if (openList.size() == 0) {
				break;
			}
			currentNode = openList.get(bsNodeIndex);
			step++;
		}
		return goalReached;
	}

	private void trackthePath() {
		Node crNode = goalNode;
		while (crNode != startNode) {
			pathList.add(0, crNode);
			crNode = crNode.parent;
		}
	}

	public void openNode(Node node) {
		if (!node.open && !node.checked && !node.solid) {
			node.open = true;
			node.parent = currentNode;
			openList.add(node);
		}
	}

}
