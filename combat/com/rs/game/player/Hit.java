package com.rs.game.player;

import com.rs.game.Entity;

import lombok.Data;

/**
 * These actually need to be looked into once hp bars and such are fixed; we
 * technically don't use any hitmarks besides hit and miss (generic hit mask), so
 * cannon, etc.. have no special icons, such in this revision.
 * 
 * @author Dennis
 *
 */
@Data
public final class Hit {

	//new hitmarkers will be updated when a combat system is reworked.
	public static enum HitLook {

		MISSED(8), REGULAR_DAMAGE(3), MELEE_DAMAGE(0), RANGE_DAMAGE(1), MAGIC_DAMAGE(2), REFLECTED_DAMAGE(4),
		ABSORB_DAMAGE(5), POISON_DAMAGE(6), DESEASE_DAMAGE(7), HEALED_DAMAGE(9), CANNON_DAMAGE(13);

//		MISSED(1), 
//		REGULAR_DAMAGE(2), 
//		LARGE_DAMAGE(3), 
//		POISON_DAMAGE(4),
//		LARGE_POISON_DAMAGE(5),
//		POISON_LIKE_DAMAGE(6), //dung maybe?
//		LARGE_POISON_LIKE_DAMAGE(7), //dung maybe?
//		NPC_MISSED(8), //maybe this is for npcs? its similar to damage though. 
//		LARGE_NPC_MISSED(9), //maybe this is for npcs? its similar to damage though. 
//		HEAL_DUNG(10)//gotta be healed dung (cross shaped) 11 is same thing/size
		private int mark;

		private HitLook(int mark) {
			this.mark = mark;
		}

		public int getMark() {
			return mark;
		}
	}

	private Entity source;
	private HitLook look;
	private int damage;
	private boolean critical;
	private Hit soaking;
	private int delay;
	public Hit(Entity source, int damage, HitLook look) {
		this(source, damage, look, 0);
	}

	public Hit(Entity source, int damage, HitLook look, int delay) {
		this.source = source;
		this.damage = damage;
		this.look = look;
		this.delay = delay;
	}

	public boolean missed() {
		return damage == 0;
	}

	public boolean interactingWith(Player player, Entity victm) {
		return player == victm || player == source;
	}

	public int getMark(Player player, Entity victm) {
		if (look == HitLook.HEALED_DAMAGE)
			return look.getMark();
		if (missed()) {
			return HitLook.MISSED.getMark();
		}
		return 5;
	}
}