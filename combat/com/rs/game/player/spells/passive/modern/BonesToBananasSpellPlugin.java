package com.rs.game.player.spells.passive.modern;

import java.util.Arrays;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.constants.Animations;
import com.rs.constants.Graphic;
import com.rs.constants.ItemNames;
import com.rs.constants.Sounds;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.spells.passive.PassiveSpellListener;
import com.rs.game.player.spells.passive.PassiveSpellSignature;

import skills.Skills;

@PassiveSpellSignature(spellButton = 33, spellLevelRequirement = 15, spellbookId = 0, experience = 25)
public class BonesToBananasSpellPlugin implements PassiveSpellListener {

	private static final int[] BONES = new int[] {526, 532};
	
	@Override
	public boolean canExecute(Player player) {
		if (!hasBones(player)) {
			player.getPackets().sendGameMessage("You aren't holding any bones!");
			return false;
		}
		return hasBones(player);
	}

	@Override
	public void execute(Player player) {
		if (hasBones(player)) {
			player.getAudioManager().sendSound(Sounds.BONES_TO);
			player.setNextAnimation(Animations.ITEM_SPELL_CONVERTING);
			player.setNextGraphics(Graphic.BONES_TO_SPELL);
			player.getSkills().addExperience(Skills.MAGIC, 25);
		}
		Arrays.stream(BONES).filter(bone -> player.getInventory().containsAny(bone)).forEach(bone -> {
			int amount = player.getInventory().getAmountOf(bone);
			player.getInventory().deleteItem(new Item(bone, amount));
			player.getInventory().addItem(new Item(ItemNames.BANANA_18199, amount));
			player.getDetails().getStatistics()
			.addStatistic(ItemDefinitions.getItemDefinitions(bone).getName() + "_To_Banana")
			.addStatistic("To_Banana_Spell");
		});
	}

	@Override
	public Item[] runes() {
		return new Item[] {
				new Item(ItemNames.EARTH_RUNE_557, 2),
				new Item(ItemNames.WATER_RUNE_555, 2),
				new Item(ItemNames.NATURE_RUNE_561, 1)
		};
	}
	
	/**
	 * Checks if the player has bones.
	 * @param player the player.
	 * @return {@code True} if so.
	 */
	private boolean hasBones(Player player) {
		return Arrays.stream(BONES).anyMatch(bone -> player.getInventory().containsAny(bone));
	}
}