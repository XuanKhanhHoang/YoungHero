package main;

import entity.NPC_OldMan01;
import entity.NPC_Trader01;
import entity.StoryCharacter_GrandFatherPlayer;
import monster.Monster_Skeleton01;
import monster.Monster_Slime01;
import objects.Obj_GreenHerb;
import objects.Obj_SpecialGrass;
import objects.StoryImage_Child;
import tile_interactive.InteractiveTile_Tree01;

public class AssetSetter {
	GamePanel gp;

	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}

	public void setObject() {
		int i = 0, mapNum = 0;

		if (gp.handleStory.turn < 6) {
			gp.object[mapNum][i] = new Obj_SpecialGrass(gp);
			gp.object[mapNum][i].worldX = 45 * gp.TitleSize;
			gp.object[mapNum][i].worldY = 22 * gp.TitleSize;
			i++;
			gp.object[mapNum][i] = new StoryImage_Child(gp);
			gp.object[mapNum][i].worldX = 40 * gp.TitleSize;
			gp.object[mapNum][i].worldY = 43 * gp.TitleSize;
			i++;
		}
		if (gp.handleStory.turn == 7) {
			gp.object[0][i] = new Obj_GreenHerb(gp);
			gp.object[0][i].worldX = 5 * gp.TitleSize;
			gp.object[0][i].worldY = 4 * gp.TitleSize;
		}
		mapNum++;
		i = 0;
	}

	public void setNPC() {
		int i = 0, mapNum = 0;
		if (gp.handleStory.turn == 0) {
			gp.npc[mapNum][i] = new StoryCharacter_GrandFatherPlayer(gp, 34, 45);
			gp.npc[mapNum][i].worldX = 34 * gp.TitleSize;
			gp.npc[mapNum][i].worldY = 45 * gp.TitleSize;
			i++;
		}
		if (gp.handleStory.turn >= 5) {
			gp.npc[mapNum][i] = new NPC_Trader01(gp);
			gp.npc[mapNum][i].worldX = 10 * gp.TitleSize;
			gp.npc[mapNum][i].worldY = 39 * gp.TitleSize;
			i++;
			gp.npc[mapNum][i] = new NPC_OldMan01(gp);
			gp.npc[mapNum][i].worldX = 17 * gp.TitleSize;
			gp.npc[mapNum][i].worldY = 39 * gp.TitleSize;
			i++;
		}

		i = 0;
		mapNum++;
//		gp.npc[mapNum][i] = new NPC_OldMan01(gp);
//		gp.npc[mapNum][i].worldX=17*gp.TitleSize;
//		gp.npc[mapNum][i].worldY=39*gp.TitleSize;
//		i++;

	}

	public void setMonster() {
		int i = 0, mapNum = 0;
		gp.monster[mapNum][i] = new Monster_Slime01(gp);
		gp.monster[mapNum][i].worldX = 41 * gp.TitleSize;
		gp.monster[mapNum][i].worldY = 11 * gp.TitleSize;
		i++;
		gp.monster[mapNum][i] = new Monster_Slime01(gp);
		gp.monster[mapNum][i].worldX = 44 * gp.TitleSize;
		gp.monster[mapNum][i].worldY = 20 * gp.TitleSize;
		i++;
		gp.monster[mapNum][i] = new Monster_Slime01(gp);
		gp.monster[mapNum][i].worldX = 31 * gp.TitleSize;
		gp.monster[mapNum][i].worldY = 17 * gp.TitleSize;
		i++;
		gp.monster[mapNum][i] = new Monster_Slime01(gp);
		gp.monster[mapNum][i].worldX = 43 * gp.TitleSize;
		gp.monster[mapNum][i].worldY = 16 * gp.TitleSize;
		i++;
		if (gp.handleStory.turn >= 7) {
			gp.monster[mapNum][i] = new Monster_Skeleton01(gp);
			gp.monster[mapNum][i].worldX = 6 * gp.TitleSize;
			gp.monster[mapNum][i].worldY = 5 * gp.TitleSize;
			i++;
			gp.monster[mapNum][i] = new Monster_Skeleton01(gp);
			gp.monster[mapNum][i].worldX = 14 * gp.TitleSize;
			gp.monster[mapNum][i].worldY = 5 * gp.TitleSize;
			i++;
			gp.monster[mapNum][i] = new Monster_Skeleton01(gp);
			gp.monster[mapNum][i].worldX = 36 * gp.TitleSize;
			gp.monster[mapNum][i].worldY = 5 * gp.TitleSize;
			i++;
		}

	}

	public void setInteractiveTile() {
		int mapNum = 0, i = 0;
//		if(gp.turnStory==1) {
		gp.tileInteractive[mapNum][i] = new InteractiveTile_Tree01(gp);
		gp.tileInteractive[mapNum][i].worldX = 22 * gp.TitleSize;
		gp.tileInteractive[mapNum][i].worldY = 23 * gp.TitleSize;
		i++;
		gp.tileInteractive[mapNum][i] = new InteractiveTile_Tree01(gp);
		gp.tileInteractive[mapNum][i].worldX = 22 * gp.TitleSize;
		gp.tileInteractive[mapNum][i].worldY = 24 * gp.TitleSize;
		i++;
		gp.tileInteractive[mapNum][i] = new InteractiveTile_Tree01(gp);
		gp.tileInteractive[mapNum][i].worldX = 23 * gp.TitleSize;
		gp.tileInteractive[mapNum][i].worldY = 11 * gp.TitleSize;
		i++;
		gp.tileInteractive[mapNum][i] = new InteractiveTile_Tree01(gp);
		gp.tileInteractive[mapNum][i].worldX = 23 * gp.TitleSize;
		gp.tileInteractive[mapNum][i].worldY = 10 * gp.TitleSize;
		i++;
//		}
//		else if(gp.turnStory!=1) {
//			
//		}
	}
}
