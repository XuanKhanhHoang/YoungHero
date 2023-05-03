package story;

import main.GamePanel;
import objects.Obj_Armor_IronAmor;
import objects.Obj_GreenHerb;
import objects.Obj_Weapon_RedSword;

public class HandleStory {
	GamePanel gp;
	public String dialogStory[][];
	public boolean npcUpdate = false;
	public int turn, numDialog;

	public HandleStory(GamePanel gp) {
		this.gp = gp;
		dialogStory = new String[10][20];
		setDialogStory();
		turn = 0;
		numDialog = 0;
	}

	private void setDialogStory() {
		int i = 0, turn = 0;
		dialogStory[turn][i] = "In one day ...";
		i++;
		dialogStory[turn][i] = "Old Man: I can hear some thing , what is this !!!";
		i++;
		dialogStory[turn][i] = "Old Man: That's a boy !\n Who put him to here ?";
		i++;
		dialogStory[turn][i] = "Old Man: Don't worry boy ! I'll take care you!\n From now on you name is Kekashi";
		i++;

		turn++;
		i = 0;// t1
		dialogStory[turn][i] = "Happy days went on like this";
		i++;
		dialogStory[turn][i] = "Happy days went on like this";
		i++;
		dialogStory[turn][i] = "Until";
		i++;

		turn++;
		i = 0;// t2
		dialogStory[turn][i] = "When he was 17 years old\nA surprise event happen";
		i++;
		dialogStory[turn][i] = "This day," + "\nhis grandfather wanna him " + "\nto find a special grass";
		i++;
		dialogStory[turn][i] = "Old man: Hey Boy , I need some special grass " + "\n      and I wanna you to find it"
				+ "\n      Could you find it for me!" + "\n       It's placed in the North";
		i++;
		dialogStory[turn][i] = "Me: Of course , GrandFather!" + "\n     I'll go now !";
		i++;
		dialogStory[turn][i] = "OldMan: Go home early!";
		i++;

		turn++;
		i = 0;// t3
		dialogStory[turn][i] = "Press: W to  go up" + "\n	D to go right" + "\n	S to go down" + "\n	A to go left";
		i++;
		dialogStory[turn][i] = "Move to item to get it ";
		i++;

		turn++;
		i = 0;// t4
		dialogStory[turn][i] = "I found it \n" + "I should go home now";
		i++;
		dialogStory[turn][i] = "It's so silent !\n" + "I should go home faster";
		i++;
		dialogStory[turn][i] = "Me: GrandFather!\n" + "\n       Are you OK ?" + "\n       What happened ?";
		i++;
		dialogStory[turn][i] = "Old Man: Demons Army...Attacked our Vilage\n" + "..And they killed...all villager";
		i++;
		dialogStory[turn][i] = "Old Man: I'll die..My  boy..You have to...\n" + "have a good life";
		i++;
		dialogStory[turn][i] = "Old Man: I'll give you..all the coin..and\n" + "..this shield and this sword..\n";
		i++;
		dialogStory[turn][i] = "Old Man: Go north (up) then east (right)..\n"
				+ "There is Mushi base..There will safe...\n"
				+ "and It give you all skill ...you need \n..to protect yoursefl";
		i++;
		dialogStory[turn][i] = "Old Man: GoodBye...My boy!...";
		i++;
		dialogStory[turn][i] = "Me: Noooo!\nGrandFather !";
		i++;
		dialogStory[turn][i] = "Me: I swear to kill the demons to avenge you !";
		i++;

		turn++;
		i = 0;// t5
		dialogStory[turn][i] = "According to him, I went to the Mushi base";
		i++;
		dialogStory[turn][i] = " Here, I was taught knowledge of swordsmanship\n"
				+ ", elemental skills,Which made me stronger.";
		i++;
		dialogStory[turn][i] = "And 2 years later ...";
		i++;
		dialogStory[turn][i] = "I was finally allowed to leave the base";
		i++;
		dialogStory[turn][i] = "I decided to back to my villge\n" + "Because after the events of that year ";
		i++;
		dialogStory[turn][i] = "A lot of monsters appeared \n" + "in the north outside the village."
				+ "\nThe road there was also deserted";
		i++;
		dialogStory[turn][i] = "I will pass through this place \n" + "to practice as well as train my swordsmanship";
		i++;
		dialogStory[turn][i] = "Me: I can do it!";
		i++;
		dialogStory[turn][i] = "Press enter to attack";
		i++;
		dialogStory[turn][i] = "Press 1 to cast skill";
		i++;
		dialogStory[turn][i] = "Press Space to use shield \n(Block damage under its defense value)";
		i++;
		dialogStory[turn][i] = " Press ESC to open Menu Setting ";
		i++;
		dialogStory[turn][i] = " Press C to open Character Sscreen " + "\nand inventory";
		i++;
		dialogStory[turn][i] = " Go West (left) then South (down) to vilage";
		i++;
		turn++;
		i = 0;
		// turn=6
		dialogStory[turn][i] = "OldMan: Are you Kekashi ?" + "\nDo you remember me?"
				+ "I'm a sword maker,\nMy name is Nagashita";
		i++;
		dialogStory[turn][i] = "Me: Yes ,that's me\nNow,I'm a swordman \n" + "And I'm here to protect everyone";
		i++;
		dialogStory[turn][i] = "OldMan: How lucky you are here!";
		i++;
		dialogStory[turn][i] = "OldMan: Many people in the village have been \n" + "infected with Masakida influenza.";
		i++;
		dialogStory[turn][i] = "OldMan: There is an herb used to treat this and"
				+ "\n it grows in the north of the village\n" + "but there are a lot of monsters.";
		i++;
		dialogStory[turn][i] = "OldMan: Can you go there and get it for me?\n" + "I'll give you new Sword and\n"
				+ "When you return I will give you a new armor.";
		i++;
		dialogStory[turn][i] = "Me: It's not a problem!";
		i++;
		dialogStory[turn][i] = "OldMan: You should go to the shop \n" + "next door to buy some necessary items,\n"
				+ " I will give you 400 coins";
		i++;
		dialogStory[turn][i] = "Me:  I think I should go buy some items \n" + "and then stop by my old house "
				+ "\nand go on a quest!";
		i++;
		dialogStory[turn][i] = "Me: That's nostalgic!";
		i++;
		dialogStory[turn][i] = "Your respawn point has just been placed here";
		i++;
		// turn=7
		turn++;
		i = 0;
		dialogStory[turn][i] = "Me: Hmm! " + "\n What's happened ?";
		i++;
		dialogStory[turn][i] = "Him: Who want to get my herb ?";
		i++;
		dialogStory[turn][i] = "Me:Who are you ?";
		i++;
		dialogStory[turn][i] = "Him: I'm Fire Devil General !" + "\n Do you want my herb ?" + "Its cost is your life !";
		i++;
		dialogStory[turn][i] = "Me: Fire Devil General !" + "You killed my grandfather !";
		i++;
		dialogStory[turn][i] = "Me: I swear to kill you to avenge my grandfather !";
		i++;
		dialogStory[turn][i] = "Me: You'll die !";
		i++;
		dialogStory[turn][i] = "Me: I did it !\n GrandFather, You see that!\nI did it";
		i++;
		// turn=8
		turn++;
		i = 0;
		dialogStory[turn][i] = "How will his journey continue?";
		i++;
		dialogStory[turn][i] = "Will he give up the adventure or...";
		i++;
		dialogStory[turn][i] = "Will he continue on the path of slaying demons?";
		i++;
		dialogStory[turn][i] = "Looking forward to our official version !";
		i++;
		dialogStory[turn][i] = "Thank you for playing this game!";
		i++;

	}

	public void handleStoryAction(int turn, int numDialog) {
		if (numDialog < 15 && gp.handleStory.dialogStory[turn][numDialog + 1] != null) {
			gp.handleStory.numDialog++;
		} else {
			gp.handleStory.turn++;
			gp.handleStory.numDialog = 0;
		}

		if (turn == 0 && numDialog == 1) {
			gp.handleStory.npcUpdate = true;
			gp.gameState=gp.playState;
		} else if (turn == 0 && numDialog == 0) {
			gp.handleStory.npcUpdate = true;
			gp.gameState = gp.playState;
		} else if (turn == 0 && numDialog == 3) {
			gp.appearTransSub = false;
			npcUpdate = false;
			gp.gameState = gp.transitionSubState;
			int i = 0;
			while (gp.npc[0][i].name != "GrandFather Player") {
				i++;
			}
			gp.npc[0][i].direction = "down";
			i = 0;
			while (gp.object[0][i].name != "StoryImage_Child") {
				i++;
			}
			gp.object[0][i] = null;
			gp.exeption = 1;
		} else if (turn == 1 && numDialog == 0) {
			gp.appearTransSub = true;
			gp.gameState = gp.transitionSubState;
			gp.exeption = 2;
		} else if (turn == 1 && numDialog == 1) {
			gp.exeption = 3;
			gp.appearTransSub = false;
			gp.gameState = gp.transitionSubState;
		} else if (turn == 1 && numDialog == 2) {
			gp.exeption = 4;
			gp.appearTransSub = false;
			gp.gameState = gp.transitionSubState;
		} else if (turn == 2 && numDialog == 4) {
			gp.exeption = 0;
			gp.gameState = gp.transitionSubState;
			gp.appearTransSub = true;
			gp.player.worldX = 34 * gp.TitleSize;
			gp.player.worldY = 21 * gp.TitleSize - 2;
		} else if (turn == 3 && numDialog == 1) {
			gp.gameState = gp.playState;
			gp.player.handleMove = true;
			gp.player.nowQuest = "\nFind a Special Grass";
		} else if (turn == 4 && numDialog == 0) {
			gp.exeption = 6;
			gp.appearTransSub = false;
			gp.gameState = gp.transitionSubState;
			gp.player.direction = "down";
		} else if (turn == 4 && numDialog == 1) {
			gp.exeption = 7;
			gp.appearTransSub = false;
			gp.gameState = gp.transitionSubState;

		} else if (turn == 4 && numDialog == 9) {
			gp.exeption = 8;
			gp.appearTransSub = false;
			gp.gameState = gp.transitionSubState;

		} else if (turn == 5 && numDialog == 3) {
			gp.exeption = 0;
			gp.player.respawnCol = 48;
			gp.player.respawnRow = 13;
			gp.player.setRespawnPosition();
			gp.player.direction = "left";
		} else if (turn == 5 && numDialog == 13) {
			gp.player.handleMove = true;
			gp.player.canAttack = true;
			gp.player.maxMana = 2;
			gp.player.level=1;
			gp.gameState = gp.playState;
			gp.player.mana = gp.player.maxMana;

			gp.assetSetter.setMonster();

			gp.player.inventory.clear();
			gp.player.setPlayerValue();
			gp.player.nowQuest = "\nGo to the Village (go down)";
			gp.player.getPlayerDefenceImage();
			gp.assetSetter.setNPC();
		} else if (turn == 6 && numDialog == 8) {
			gp.gameState = gp.playState;
			gp.player.coin += 400;
			gp.player.inventory.add(new Obj_Weapon_RedSword(gp));
			int i = 0;
			while (gp.npc[0][i].name != "Nagashita") {
				i++;
			}
			gp.npc[0][i].turnInterract++;
			gp.player.nowQuest = "\nVisit your old Home";
		} else if (turn == 6 && numDialog == 10) {
			gp.gameState = gp.playState;
			gp.player.nowQuest = "\nFind an herb \nWhich grows in the \nnorth of the village";
			gp.player.respawnCol = 39;
			gp.player.respawnRow = 44;
			int i = 0;
			while (gp.object[0][i] != null)
				i++;
			gp.object[0][i] = new Obj_GreenHerb(gp);
			gp.object[0][i].worldX = 5 * gp.TitleSize;
			gp.object[0][i].worldY = 4 * gp.TitleSize;
			gp.assetSetter.setMonster();
			i++;
			gp.object[0][i] = new Obj_Armor_IronAmor(gp);
			gp.object[0][i].worldX = 21 * gp.TitleSize;
			gp.object[0][i].worldY = 7 * gp.TitleSize;

		} else if (turn == 7 && numDialog == 6) {
			gp.gameState = gp.playState;
		} else if (turn == 7 && numDialog == 7) {
			gp.gameState = gp.transitionSubState;
			gp.exeption = 8;
			gp.appearTransSub = false;
		} else if (turn == 8 && numDialog == 4) {
			gp.gameState = gp.titleState;
			gp.ui.titleScreenState = 2;
			return;
		}
	}

}
