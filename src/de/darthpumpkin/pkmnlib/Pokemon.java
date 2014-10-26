package de.darthpumpkin.pkmnlib;

import java.io.Serializable;

/**
 * 
 * @author dominik
 * 
 */

@SuppressWarnings("serial")
public class Pokemon implements Serializable, ItemContainer {

	public enum StatusProblem {
		// TODO check if there is an order for status problems in the db and if
		// so, is it different from this one?
		BURN, FREEZE, PARALYSIS, POISON, BAD_POISON
	}

	public enum Stat {
		HP(0), ATK(1), DEF(2), SPATK(3), SPDEF(4), SPEED(5), ACCURACY(6), EVASION(
				7);
		private int index;

		Stat(int index) {
			this.index = index;
		}
		public int i() {
			return index;
		}
	}

	private int[] baseStats;
	private int[] currentStats; // those you would see in the game
	private int[] temporaryStatModifiers; // from -6 to 6; reset after
											// switch-out
	private int level;
	private Type[] types;
	private StatusProblem statusProblem;	//null if there is none

	/**
	 * 
	 * @return true if it can be used in a battle. False if not (fainted, egg,
	 *         etc.)
	 */
	public boolean isUsable() {
		// TODO Auto-generated method stub
		return false;
	}

	public int getAbility() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void remove(Item item) {
		// TODO Auto-generated method stub

	}

	@Override
	public void attach(Item item) {
		// TODO Auto-generated method stub

	}

	public int[] getBaseStats() {
		return baseStats;
	}

	public void setBaseStats(int[] baseStats) {
		this.baseStats = baseStats;
	}

	/**
	 * those you would see in the game when cheking the poke's info
	 * 
	 * @return
	 */
	public int[] getCurrentStats() {
		return currentStats;
	}

	/**
	 * those you would see in the game when cheking the poke's info
	 * 
	 * @return
	 */
	public void setCurrentStats(int[] currentStats) {
		this.currentStats = currentStats;
	}

	public int[] getTemporaryStatModifiers() {
		return temporaryStatModifiers;
	}

	public void setTemporaryStatModifiers(int[] temporaryStatModifiers) {
		this.temporaryStatModifiers = temporaryStatModifiers;
	}

	/**
	 * the actual value of the stat right wihtin the battle, respecting all
	 * temporary changes including +2 from swords dance etc.
	 * 
	 * @param stat
	 *            the stat you want to have computed
	 * @return
	 */
	public float getCurrent(Stat stat) {
		int c = currentStats[stat.i()];
		int t = temporaryStatModifiers[stat.i()];
		if (t >= 0) {
			return c * (2 + t) / 2;
		}
		return c * 2 / (2 - t);
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Type[] getTypes() {
		return types;
	}

	public void setTypes(Type[] types) {
		this.types = types;
	}

	/**
	 * 
	 * @return the status problem or null if there is none
	 */
	public StatusProblem getStatusProblem() {
		return statusProblem;
	}

	/**
	 * use null if the status problem is cured
	 * @param statusProblem
	 */
	public void setStatusProblem(StatusProblem statusProblem) {
		this.statusProblem = statusProblem;
	}

	/**
	 * subtracts damage from this pkmn's current hp. Set to 0 if damage > hp
	 * @param damage damage to be inflicted
	 */
	public void applyDamage(int damage) {
		int hpStat = this.currentStats[Stat.HP.i()];
		hpStat -= damage;
		if (hpStat < 0) {
			hpStat = 0;
		}
	}

}