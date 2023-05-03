package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	public boolean upPressed, downPressed, leftPressed, rightPressed;
	public boolean enterPressed = false;
	private GamePanel gp;
	public boolean shotKeyPressed;

	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		// Title State
		if (gp.gameState == gp.titleState) {
			titleState(code);
		}
		// Play State
		else if (gp.gameState == gp.playState) {
			playState(code);
		}
		// Dialog State
		else if (gp.gameState == gp.dialogState) {
			dialogState(code);
		}
		// Character State
		else if (gp.gameState == gp.characterState) {
			characterState(code);
		}
		// Option State
		else if (gp.gameState == gp.optionState) {
			optionState(code);
		}
		//GameOver State 
		else if (gp.gameState == gp.gameOverState) {
			gameOverState(code);
		}
		//TradeState 
		else if (gp.gameState == gp.tradeState) {
			tradeState(code);
		}
		//StoryState
		else if (gp.gameState == gp.storyState) {
			storyState(code);
		}
	}

	private void storyState(int code) {
		int turn = gp.handleStory.turn;
		int numDialog = gp.handleStory.numDialog;
		if (code == KeyEvent.VK_ENTER) {
			if (!gp.handleStory.npcUpdate) {
				if (turn < 10) {
					gp.handleStory.handleStoryAction(turn, numDialog);
				}
			}
		}

	}

	private void tradeState(int code) {
		if (code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			gp.keyHandler.enterPressed = false;
			if (gp.ui.commandNum < 0) {
				gp.ui.commandNum = 2;
			}
		} else if (code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			gp.keyHandler.enterPressed = false;
			if (gp.ui.commandNum > 2) {
				gp.ui.commandNum = 0;
			}
		} else if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
		if (gp.ui.subCommandNum == 1) {
			npcInventory(code);
			if (code == KeyEvent.VK_ESCAPE) {
				gp.ui.subCommandNum = 0;
			}
		} else if (gp.ui.subCommandNum == 2) {
			playerInventory(code);
			if (code == KeyEvent.VK_ESCAPE) {
				gp.ui.subCommandNum = 0;
			}
		}
	}

	private void gameOverState(int code) {
		if (code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			gp.keyHandler.enterPressed = false;
			if (gp.ui.commandNum < 0) {
				gp.ui.commandNum = 1;
			}
		} else if (code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			gp.keyHandler.enterPressed = false;
			if (gp.ui.commandNum > 1) {
				gp.ui.commandNum = 0;
			}
		} else if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
	}

	private void optionState(int code) {
		if (code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.playState;
		} else if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		} else if (code == KeyEvent.VK_W) {
			gp.ui.subOptionSubNum--;
			if (gp.ui.subOptionSubNum < 0 && gp.ui.subOptionNum == 0) {
				gp.ui.subOptionSubNum = 4;
			} else if (gp.ui.subOptionSubNum < 0 && gp.ui.subOptionNum == 2) {
				gp.ui.subOptionSubNum = 1;
			}
		} else if (code == KeyEvent.VK_S) {
			gp.ui.subOptionSubNum++;
			if (gp.ui.subOptionSubNum > 5 && gp.ui.subOptionNum == 0) {
				gp.ui.subOptionSubNum = 0;
			} else if (gp.ui.subOptionSubNum > 1 && gp.ui.subOptionNum == 2) {
				gp.ui.subOptionSubNum = 0;
			}
		} else if (code == KeyEvent.VK_A) {
			if (gp.ui.subOptionNum == 0 && gp.ui.subOptionSubNum == 0 && gp.music.volumeScale > 0) {
				gp.music.volumeScale--;
				gp.music.checkVolume();
				gp.config.saveConfig();
			} else if (gp.ui.subOptionNum == 0 && gp.ui.subOptionSubNum == 1 && gp.singleSound.volumeScale > 0) {
				gp.singleSound.volumeScale--;
				gp.config.saveConfig();
			}
		} else if (code == KeyEvent.VK_D) {
			if (gp.ui.subOptionNum == 0 && gp.ui.subOptionSubNum == 0 && gp.music.volumeScale < 5) {
				gp.music.volumeScale++;
				gp.music.checkVolume();
				gp.config.saveConfig();
			} else if (gp.ui.subOptionNum == 0 && gp.ui.subOptionSubNum == 1 && gp.singleSound.volumeScale < 5) {
				gp.singleSound.volumeScale++;
				gp.config.saveConfig();
			}
		}
	}

	private void titleState(int code) {
		if (gp.ui.titleScreenState == 0) {
			if (code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0) {
					gp.ui.commandNum = 3;
				}
			} else if (code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if (gp.ui.commandNum > 3) {
					gp.ui.commandNum = 0;
				}
			} else if (code == KeyEvent.VK_ENTER) {
				if (gp.ui.commandNum == 0) {
					gp.restart();
					gp.gameState = gp.transitionSubState;
					gp.playMusic(0, true);
					gp.player.invincible = false;
					gp.keyHandler.enterPressed = false;
					gp.player.direction = "down";
				} else if (gp.ui.commandNum == 1) {

				} else if (gp.ui.commandNum == 2) {
					gp.ui.titleScreenState = 1;

				} else if (gp.ui.commandNum == 3) {
					System.exit(0);
				}
			}
		} 
		else if (gp.ui.titleScreenState == 1) {
			if (code == KeyEvent.VK_ENTER) {
				gp.ui.titleScreenState = 0;
			}
		}
		else if (gp.ui.titleScreenState == 2) {
			if (code == KeyEvent.VK_ENTER) {
				gp.ui.titleScreenState = 0;
			}
		}
	}


	private void dialogState(int code) {
		if (code == KeyEvent.VK_ENTER) {
			gp.gameState = gp.playState;
		}
	}

	private void playerInventory(int code) {
		if (code == KeyEvent.VK_W) {
			if (gp.ui.playerSlotRow != 0) {
				gp.ui.playerSlotRow--;
			}
		} else if (code == KeyEvent.VK_S) {
			if (gp.ui.playerSlotRow != 3) {
				gp.ui.playerSlotRow++;
			}
		} else if (code == KeyEvent.VK_A) {
			if (gp.ui.playerSlotCol != 0) {
				gp.ui.playerSlotCol--;
			}
		} else if (code == KeyEvent.VK_D) {
			if (gp.ui.playerSlotCol != 4) {
				gp.ui.playerSlotCol++;
			}
		}
	}

	private void npcInventory(int code) {
		if (code == KeyEvent.VK_W) {
			if (gp.ui.npcSlotRow != 0) {
				gp.ui.npcSlotRow--;
			}
		} else if (code == KeyEvent.VK_S) {
			if (gp.ui.npcSlotRow != 3) {
				gp.ui.npcSlotRow++;
			}
		} else if (code == KeyEvent.VK_A) {
			if (gp.ui.npcSlotCol != 0) {
				gp.ui.npcSlotCol--;
			}
		} else if (code == KeyEvent.VK_D) {
			if (gp.ui.npcSlotCol != 4) {
				gp.ui.npcSlotCol++;
			}
		}
	}

	private void characterState(int code) {
		if (code == KeyEvent.VK_C) {
			gp.gameState = gp.playState;
		} else if (code == KeyEvent.VK_ENTER) {
			gp.player.selectItem();
		} else
			playerInventory(code);

	}

	private void playState(int code) {
		if (code == KeyEvent.VK_W) {
			upPressed = true;
		} else if (code == KeyEvent.VK_S) {
			downPressed = true;
		} else if (code == KeyEvent.VK_A) {
			leftPressed = true;
		} else if (code == KeyEvent.VK_D) {
			rightPressed = true;
		} else if (code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.optionState;
		} else if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		} else if (code == KeyEvent.VK_C) {
			gp.gameState = gp.characterState;
		} else if (code == KeyEvent.VK_1) {
			shotKeyPressed = true;
		}  else if (code == KeyEvent.VK_SPACE) {
			if (gp.player.currentShield != null)
				gp.player.defencing = true;
		}
		else if(code == KeyEvent.VK_L) {
			gp.player.exp+=9999;
		}
//		else if(code == KeyEvent.VK_N) {
//			
//			int entityLeftWorldX = gp.player.worldX + gp.player.solidArea.x;
//			int entityRightWorldX = gp.player.worldX + gp.player.solidArea.x + gp.player.solidArea.width;
//			int entityTopWorldY = gp.player.worldY + gp.player.solidArea.y;
//			int entityBottomWorldY = gp.player.worldY + gp.player.solidArea.y+ gp.player.solidArea.height;
//			
//			int entityLeftCol = entityLeftWorldX/gp.TitleSize;
//			int entityRightCol = entityRightWorldX/gp.TitleSize;
//			int entityTopRow = entityTopWorldY/gp.TitleSize;
//			int entityBottomRow = entityBottomWorldY/gp.TitleSize;
//			
//			entityLeftCol= (entityLeftWorldX-gp.player.speed)/gp.TitleSize;
//			System.out.println(entityLeftCol+"\n  "+(entityLeftWorldX)/gp.TitleSize);
//		}
		else if(code == KeyEvent.VK_B) {
			System.out.println(gp.handleStory.turn+" "+gp.handleStory.numDialog);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_W) {
			upPressed = false;
		} else if (code == KeyEvent.VK_S) {
			downPressed = false;
		} else if (code == KeyEvent.VK_A) {
			leftPressed = false;
		} else if (code == KeyEvent.VK_D) {
			rightPressed = false;
		} else if (code == KeyEvent.VK_1) {
			shotKeyPressed = false;
		} else if (code == KeyEvent.VK_SPACE) {
			gp.player.defencing = false;
			if (gp.player.currentShield != null) {
				if (gp.player.currentArmor != null)
					gp.player.defense = gp.player.getDefense(false);
			}
		}
	}

}
