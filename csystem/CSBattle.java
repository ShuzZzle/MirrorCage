package csystem;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import component.AttackRate;
import component.Damage;
import component.Defense;
import component.Health;
import component.Hero;
import component.Info;
import component.Monster;
import component.Position;
import component.Range;
import component.View;
import core.BookOfKings;
import entity.Entity;

/**
 * Battle system
 * Perform the battle of the hero and the monster
 * 
 * Used components:
 *   Position
 *   View
 *   Range
 *   AttackRate
 *   Health
 *   Damage
 *   Defense
 *   Seek
 *   Flee
 *   Pursuit
 *   Evade
 * 
 * @author Bengt, Marlo, Alexander, Niclas
 */
public class CSBattle extends CSystem {
	
	/* Attributes */
	private static CSBattle csBattle;
	private Entity hero;
	private ArrayList <Entity> monsters;

	/**
	 * Private Constructor
	 */
	private CSBattle(){
		
		hero = getHero();
		monsters = new ArrayList<>();
	}
	
	/**
	 * Get the static instance
	 * @return
	 */
	public static CSBattle getInstance(){
		
		if(csBattle == null)
			 csBattle = new CSBattle();
		
		return  csBattle;
	}

	/**
	 * Update method
	 */
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		
		/* Check if the current chunk exists */
		if(getCurrentChunk() == null) return;
		
		/* Clear the old monster list and get the current monsters in the stage */
		monsters.clear();
		monsters = getAllMonsters(getCurrentChunk().getAllEntities());
		
		/* Loop through the entities to perform the battle */
		for(Entity monster : monsters){		
			
			performBattleOfHero(hero, monster, delta);
			performBattleOfMonster(hero, monster, delta);
		}
	
	}

	/**
	 * Unused render method
	 */
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		
		// Nothing to do here	
	}
	
	/**
	 * Perform the battle of the hero
	 * @param hero
	 * @param monster
	 */
	private void performBattleOfHero(Entity hero, Entity monster, int delta){
			
		/* Get the view of the hero and check
		 * if the monster is inside the view perspectives */
		View view = hero.get(View.class);
		if(view == null) return;
		if(!view.seenEntities.contains(monster)) return;
		
		/* If the monster is inside the radius, check if the
		 * monster is inside the heros range too! */
		Range range = hero.get(Range.class);
		Position position = hero.get(Position.class);
		if(range == null) return;
		
		float distance = position.vector.distance(monster.get(Position.class).vector); // get the distance
		if(distance > range.value) return;
		
		/* Update the elapsed time for the attack rate and perform
		 * an attack, if the left mouse button is pressed */
		AttackRate heroAttackRate = hero.get(AttackRate.class);
		heroAttackRate.elapsedTime += 0.001f * delta;
		if(heroAttackRate.elapsedTime < heroAttackRate.rateTime) return; // check for the time
		heroAttackRate.elapsedTime -= heroAttackRate.rateTime;
		
		Input input = BookOfKings.game.getInput();
		if(!input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) return; // check for the mouse press
		hero.call("onAttack", monster);
		
		/* Calculate the damage */
		Damage damage = hero.get(Damage.class);
		Health monsterHealth =  monster.get(Health.class);
		Defense monsterDefense = monster.get(Defense.class);
		float dmg = calculateDamage(damage, monsterDefense);
		if(dmg == 0) return;
		else monster.call("onDamage", hero);
		
		/* Calculate the new live */
		monsterHealth.currentHp = calculateLive(monsterHealth.currentHp, dmg);
		Info info = monster.get(Info.class);
		System.out.println("INFO: Current "+ info.name + " health: " + monsterHealth.currentHp + "...");
	}
	
	/**
	 * Perform the battle of the monster
	 * @param hero2
	 * @param monster
	 */
	private void performBattleOfMonster(Entity hero, Entity monster, int delta) {
		
		/* Firstly check if we have got a monster entity */
		if(monster.get(Monster.class) == null) return;
		
		/* Get the view component to check if the entity has seen the hero */
		View view = monster.get(View.class);
		if(view == null) return;
		if(!checkForHero(hero, view.seenEntities)) return;
		
		/* Now we know, that the entity has seen the hero. 
		 * We perform a normal battle next. 
		 * First we have to get all required components */
		Position position = monster.get(Position.class);
		Health health = monster.get(Health.class);
		Damage damage = monster.get(Damage.class);
		Defense defense = monster.get(Defense.class);
		Range range = monster.get(Range.class);
		AttackRate attackRate = monster.get(AttackRate.class);
		
		if(health == null || damage == null || defense == null 
		   || range == null || attackRate == null) return;
		
		/* Check if the hero is in the range of the monster */
		float distance = position.vector.distance(hero.get(Position.class).vector);
		
		if(distance > range.value){ // if the distance is bigger, move to the hero
						
			return;
		}
		
		/* Perform the attack rate */
		attackRate.elapsedTime += 0.001f * delta;
		if(attackRate.elapsedTime < attackRate.rateTime) return;
		attackRate.elapsedTime -= attackRate.rateTime;
		monster.call("onAttack", hero);
		
		/* Calculate the damage */
		Health heroHealth =  hero.get(Health.class);
		Defense heroDefense = hero.get(Defense.class);
		float dmg = calculateDamage(damage, heroDefense);
		if(dmg == 0) return;
		else hero.call("onDamage", monster);
		
		/* Calculate the new live */
		heroHealth.currentHp = calculateLive(heroHealth.currentHp, dmg);
		System.out.println("INFO: Current hero health: " + heroHealth.currentHp + "...");
	}
	
	/**
	 * Calculate the damage of the fight
	 * @param attackersDamage
	 * @param targetsDefense
	 * @return
	 */
	private float calculateDamage(Damage attackersDamage, Defense targetsDefense){
		
		/* Check if we have got the required components */
		if(attackersDamage == null || targetsDefense == null) return 0;
		
		/* Calculate the new damage */
		float dmg = attackersDamage.value * attackersDamage.multiplier - targetsDefense.value * targetsDefense.multiplier;
		if(dmg < 1) dmg = 1;
		
		/* Return the calculated damage */
		return dmg;
	}
	
	/**
	 * Calculate the new live
	 * @param currentHp (target)
	 * @param dmg (attacker)
	 */
	private float calculateLive(float currentHp, float dmg){
		
		/* Calculate the new hp */
		currentHp -= dmg;		
		if(currentHp < 0)
			currentHp = 0;
		
		return currentHp;
	}
	
	/**
	 * Get the hero entity
	 * @return
	 */
	private Entity getHero(){
		
		return getComponentManager().getAllEntities(Hero.class).get(0);
	}
	
	/**
	 * Check if the list of entities contains the hero
	 * @param hero
	 * @param entities
	 * @return
	 */
	private boolean checkForHero(Entity hero, ArrayList <Entity> entities){
		
		return entities.contains(hero);
	}
	
	/**
	 * Determine all monsters of the given entities
	 * @param entities
	 * @return
	 */
	public ArrayList<Entity> getAllMonsters(ArrayList <Entity> entities){
		
		ArrayList<Entity> monsters = new ArrayList<>();
		
		for(Entity entity : entities)
			if(entity.get(Monster.class) != null)
				monsters.add(entity);
		
		return monsters;
	}

}