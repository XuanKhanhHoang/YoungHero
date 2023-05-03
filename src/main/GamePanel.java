package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import ai.PathFinder;
import entity.Entity;
import entity.Player;
import entity.Skill;
import entity.StoryCharacter_GrandFatherPlayer;
import monster.Boss_FireDevilGeneral;
import objects.Obj_GreenHerb;
import objects.StoryInamge_PlayerChild;
import story.HandleStory;
import tile.TileManager;
import tile_interactive.Tile_interactive;

public class GamePanel extends JPanel implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// GAME DEFAULT SETTING
	public final int TitleSize = 48;
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenHeghit = maxScreenRow * TitleSize;
	public final int screenWidth = maxScreenCol * TitleSize;
	private final int FPS = 60;
	public int maxWorldCol;
	public int maxWorldRow;
	public int worldWidth;
	public int worldHeight;
	public final int maxMap = 4;
	public int currentMap = 0;

	// GAME STATE
	public int gameState;
	public final int playState = 1;
	public final int dialogState = 2;
	public final int titleState = 3;
	public final int characterState = 4;
	public final int optionState = 5;
	public final int gameOverState = 6;
	public final int tradeState = 8;
	public final int storyState = 9;
	public final int transitionSubState = 10;
	public final int gameFinishedState = 11;

	// GAME THREAD
	private Thread gameThread;

	// HANDLER

	public TileManager tileManager = new TileManager(this);
	public KeyHandler keyHandler = new KeyHandler(this);
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter assetSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	public EventHandler eventHandler = new EventHandler(this);
	public Sound music = new Sound();
	public Sound singleSound = new Sound();
	public Config config = new Config(this);
	public PathFinder pFinder = new PathFinder(this);
	public HandleStory handleStory = new HandleStory(this);

	// CREATE ENTITYS
	public Player player = new Player(this, keyHandler);
	public Entity object[][] = new Entity[maxMap][20];
	public Entity npc[][] = new Entity[maxMap][15];
	public Entity monster[][] = new Entity[maxMap][20];
	public Tile_interactive tileInteractive[][] = new Tile_interactive[maxMap][20];
	public ArrayList<Entity> entityList = new ArrayList<>();
	public Entity[][] projectileList = new Skill[maxMap][20];

	public int counter1 = 50, counter = 0;

	public int exeption = 0;
	public int turnStory = 1;

	public boolean appearTransSub = true;

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeghit));
		this.setDoubleBuffered(true);
		setUpGame();
		this.addKeyListener(keyHandler);
		startGamePanel();
		this.setFocusable(true);
	}

	public void setUpGame() {
		gameState = titleState;
	}

	public void startGamePanel() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void retry() {
		player.setRespawnPosition();
		player.restoreLifeAndMana();
		assetSetter.setMonster();
		assetSetter.setObject();
		Entity a= new Obj_GreenHerb(this);
		int i=0;
		while( player.inventory.get(i).name!=a.name ) {
			if(i+1>=player.inventory.size() ) {
				break;
			}
			if(player.inventory.get(i).name==a.name) {
				player.inventory.remove(i);
				break;
			}
			i++;
		}
		if(this.handleStory.turn==7 && this.handleStory.numDialog==7) {
			this.handleStory.numDialog=0;
		}
	}

	public void restart() {
		resetAllData();
		player.setDefaultValue();
		assetSetter.setObject();
		assetSetter.setNPC();
		currentMap = 0;

		assetSetter.setInteractiveTile();
	}

	@Override
	public void run() {
		double drawInterval = 1000000000 / FPS; // 1bil nanoSec = 1s
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
//		long test=0;//FOR PERFORMANCE TESTING
//
//		long timer=0;//FOR PERFORMANCE TESTING
//		int cout=0;//FOR PERFORMANCE TESTING

		while (gameThread != null) {

			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
//			timer+=currentTime- lastTime;//FOR PERFORMANCE TESTING
			lastTime = currentTime;

//			 CHECKING PERFORMANCE
			if (delta >= 1) {
//				test=System.nanoTime();//FOR PERFORMANCE TESTING
				update();
				repaint();
				delta--;
//				if(cout%60==0)System.out.println(System.nanoTime()-test);//FOR PERFORMANCE TESTING
//				cout++;//FOR PERFORMANCE TESTING
			}

//			if(timer>1000000000) {//FOR PERFORMANCE TESTING
//				System.out.print("FPS"+cout+"\n");//FOR PERFORMANCE TESTING
//				cout=0; //FOR PERFORMANCE TESTING
//				timer=0;//FOR PERFORMANCE TESTING
//			}
		}

	}

	public void update() {
		if (handleStory != null)
			if (gameState == playState || handleStory.npcUpdate) {
				player.update();
				for (int i = 0; i < npc[1].length; i++) {
					if (npc[currentMap][i] != null)
						npc[currentMap][i].update();
				}
				for (int i = 0; i < monster[1].length; i++) {
					if (monster[currentMap][i] != null) {
						if (monster[currentMap][i].alive && monster[currentMap][i].dying == false) {
							monster[currentMap][i].update();
						}
						if (!monster[currentMap][i].alive) {
							monster[currentMap][i].checkDrop();
							monster[currentMap][i] = null;
						}
					}
				}
				for (int i = 0; i < tileInteractive[1].length; i++) {
					if (tileInteractive[currentMap][i] != null) {
						tileInteractive[currentMap][i].update();
					}
				}
				for (int i = 0; i < projectileList[1].length; i++) {
					if (projectileList[currentMap][i] != null) {
						if (projectileList[currentMap][i].alive) {
							projectileList[currentMap][i].update();
						}
						if (!projectileList[currentMap][i].alive) {
							projectileList[currentMap][i] = null;
						}
					}
				}
			} 
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		if (gameState == titleState) {
			ui.draw(g2);
		} else {
			// DRAW TILE
			tileManager.draw(g2);
			for (int i = 0; i < tileInteractive[1].length; i++) {
				if (tileInteractive[currentMap][i] != null) {
					tileInteractive[currentMap][i].draw(g2);
				}
			}
			// ADD ALL ENTITYS TO LIST
			entityList.add(player);
			for (int i = 0; i < npc[1].length; i++) {
				if (npc[currentMap][i] != null)
					entityList.add(npc[currentMap][i]);
			}

			for (int i = 0; i < object[1].length; i++) {
				if (object[currentMap][i] != null)
					entityList.add(object[currentMap][i]);
			}
			for (int i = 0; i < monster[1].length; i++) {
				if (monster[currentMap][i] != null) {
					entityList.add(monster[currentMap][i]);
				}
			}
			for (int i = 0; i < projectileList.length; i++) {
				if (projectileList[currentMap][i] != null) {
					entityList.add(projectileList[currentMap][i]);
				}
			}
			// SORT LIST BY WORLD_Y
			Collections.sort(entityList, new Comparator<Entity>() {
				@Override
				public int compare(Entity e1, Entity e2) {
					return Integer.compare(e1.worldY, e2.worldY);
				}
			});
			// DRAW LIST
			for (int i = 0; i < entityList.size(); i++) {
				entityList.get(i).draw(g2);
			}
			// CLEAR LIST

			// DRAW UI
			ui.draw(g2);
			entityList.clear();
		}
		if ((gameState == transitionSubState || exeption == 8) && this.gameState != titleState) {
			transitionAnimation(appearTransSub, g2, exeption);
		}
		g2.dispose();
	}

	public void transitionAnimation(boolean appear, Graphics2D g2, int exeption) {
		if (appear) {
			if (counter1 > 0)
				counter1--;
			if (exeption != 8) {
				g2.setColor(new Color(0, 0, 0, counter1 * 5));
				g2.fillRect(0, 0, screenWidth, screenHeghit);
			}
			if (counter1 == 0) {
				counter1 = 50;
				if (exeption == 1) {
					int l = 0;
					while (object[0][l] != null)
						l++;
					object[0][l] = new StoryInamge_PlayerChild(this);
					object[0][l].worldX = 37 * TitleSize;
					object[0][l].worldY = 45 * TitleSize;
				} else if (exeption == 2) {
					int i = 0;
					while (object[0][i].name != "StoryInamge_PlayerChild")
						i++;
					((StoryInamge_PlayerChild) object[0][i]).change();
					object[0][i].worldX = 40 * TitleSize;
					object[0][i].worldY = 45 * TitleSize;
				} else if (exeption == 3) {
					int i = 0;
					while (object[0][i].name != "StoryInamge_PlayerChild")
						i++;
					object[0][i] = null;
				} else if (exeption == 5) {
					player.setRespawnPosition();
					;
				} else if (exeption == 4) {
					player.getPlayerImage();
					player.setRespawnPosition();
					player.direction = "up";
				} else if (exeption == 6) {
					player.worldX = 22 * TitleSize;
					player.worldY = 39 * TitleSize;
				} else if (exeption == 8) {
					g2.setColor(Color.black);
					g2.fillRect(0, 0, screenWidth, screenHeghit);
					counter1 = 50;
					ui.draw(g2);
				} else if (exeption == 7) {
					int i = 0;
					player.setRespawnPosition();
					player.direction = "up";
					while (npc[0][i].name != "GrandFather Player") {
						i++;
					}
					((StoryCharacter_GrandFatherPlayer) npc[0][i]).changeDieImage();
				} else if (exeption == 9) {
					int i = 0;
					while (monster[0][i] != null)
						i++;
					this.monster[0][i] = new Boss_FireDevilGeneral(this);
					this.monster[0][i].worldX = 10 * this.TitleSize;
					this.monster[0][i].worldY = 4 * this.TitleSize;
					this.monster[0][i].direction = "left";
					player.worldX = 6 * TitleSize;
					player.worldY = 4 * TitleSize;
					player.direction = "right";

				}

				gameState = storyState;
			}
		} else {

			if (counter < 50)
				counter++;
			if (exeption != 8) {
				g2.setColor(new Color(0, 0, 0, counter * 5));
				g2.fillRect(0, 0, screenWidth, screenHeghit);
			}
			if (counter == 50) {
				counter = 0;
				if (exeption == 1) {
					int l = 0;
					while (object[0][l] != null)
						l++;
					object[0][l] = new StoryInamge_PlayerChild(this);
					object[0][l].worldX = 37 * TitleSize;
					object[0][l].worldY = 45 * TitleSize;
				} else if (exeption == 2) {
					int i = 0;
					while (object[0][i].name != "StoryInamge_PlayerChild")
						i++;
					((StoryInamge_PlayerChild) object[0][i]).change();
					object[0][i].worldX = 40 * TitleSize;
					object[0][i].worldY = 45 * TitleSize;
				} else if (exeption == 3) {
					int i = 0;
					while (object[0][i].name != "StoryInamge_PlayerChild")
						i++;
					object[0][i] = null;
				} else if (exeption == 4) {
					player.getPlayerImage();
					player.setRespawnPosition();
					player.direction = "up";
				} else if (exeption == 5) {
					player.setRespawnPosition();
					;
				} else if (exeption == 6) {
					player.worldX = 22 * TitleSize;
					player.worldY = 39 * TitleSize;
				} else if (exeption == 7) {
					int i = 0;
					player.setRespawnPosition();
					player.direction = "up";
					while (npc[0][i].name != "GrandFather Player") {
						i++;
					}
					((StoryCharacter_GrandFatherPlayer) npc[0][i]).changeDieImage();
				} else if (exeption == 8) {
					g2.setColor(Color.black);
					g2.fillRect(0, 0, screenWidth, screenHeghit);
					counter = 50;
					ui.draw(g2);
				} else if (exeption == 9) {
					int i = 0;
					while (monster[0][i] != null)
						i++;
					this.monster[0][i] = new Boss_FireDevilGeneral(this);
					this.monster[0][i].worldX = 10 * this.TitleSize;
					this.monster[0][i].worldY = 4 * this.TitleSize;
					this.monster[0][i].direction = "left";
					player.worldX = 6 * TitleSize;
					player.worldY = 4 * TitleSize;
					player.direction = "right";

				}
				gameState = storyState;
			}
		}

	}

	public void resetAllData() {
		tileManager = null;
		assetSetter = null;
		ui = null;
		eventHandler = null;
		handleStory = null;
		exeption = 0;
		counter = 0;
		counter1 = 50;
		appearTransSub = true;

		// CREATE ENTITYS
		player = null;
		object = null;
		npc = null;
		monster = null;
		tileInteractive = null;

		tileManager = new TileManager(this);
		assetSetter = new AssetSetter(this);
		ui = new UI(this);
		eventHandler = new EventHandler(this);
		handleStory = new HandleStory(this);

		// CREATE ENTITYS
		player = new Player(this, keyHandler);
		object = new Entity[maxMap][20];
		npc = new Entity[maxMap][15];
		monster = new Entity[maxMap][20];
		tileInteractive = new Tile_interactive[maxMap][20];
	}

	public void playMusic(int i, boolean isLoop) {
		if (isLoop) {
			music.setSound(i);
			music.playSound();
			music.loop();
		} else {
			music.setSound(i);
			music.playSound();
		}
	}

	public void stopMusic() {
		music.stop();
	}

	public void playSingleSound(int i) {
		singleSound.setSound(i);
		singleSound.playSound();
	}

	public void stopSingleSound(int i) {
		singleSound.stop();
	}
}
