package engine.test;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import authoring.gamestate.Level;
import data.DataGameState;
import data.DataWrite;
import engine.Engine;
import engine.InternalEngine;
import engine.actions.Actions;
import engine.components.*;
import engine.components.Component;
import engine.components.Dimension;
import engine.setup.GameInitializer;
import engine.systems.InputHandler;
import javafx.scene.input.KeyCode;

public class TestGameState {

	private Map<Integer, Map<String, Component>> entities;
	private Map<Integer, Map<String, Component>> entities2;
	private Engine eng;
	private InputHandler ih;
	private Actions actions = new Actions();

	public TestGameState() throws FileNotFoundException {
		System.out.println("TestGameState");
		entities = new HashMap<>();
		entities2 = new HashMap<>();
		Sprite s = new Sprite(0,"Mario.png");
		Sprite s2 = new Sprite(1,"mario.png");
		Sprite s3 = new Sprite(2,"mario.png");
		Sprite s4 = new Sprite(3,"mario.png");
		Sprite s5 = new Sprite(3,"Mario.png");


		Position p = new Position(0, 100, 100);
		Dimension d = new Dimension(0, 100, 100);
		Velocity v = new Velocity(0, 0, 0);

		Acceleration a = new Acceleration(0, 0, 0);
		KeyInput k = new KeyInput(0);
		k.addCode( KeyCode.RIGHT, (Consumer & Serializable) (e) -> {
			v.setXVel(+150);
		});
		k.addCode(KeyCode.UP, (Consumer & Serializable)(e) ->
		{ 
			v.setYVel(-150);
		});
		k.addCode(KeyCode.DOWN,(Consumer & Serializable) (e) ->
		{ 
			v.setYVel(+150);
		});
		k.addCode(KeyCode.LEFT,(Consumer & Serializable) (e) ->
		{
			v.setXVel(-150);
		});
		Health h = new Health(0,10);
		DamageLauncher launcher = new DamageLauncher(0,2,2);

		Player play = new Player(0, 3);
        play.setRespawn(p.clone());
		/**k.addCode(KeyCode.R, (Runnable & Serializable) () ->
		{
			play.respawn(p, v, a);
		});**/

		Map<String, Component> mario = new HashMap<>();
		mario.put(Position.KEY, p);
		mario.put(Dimension.KEY, d);
		mario.put(Sprite.KEY, s);

		mario.put(Velocity.KEY, v);
		mario.put(Acceleration.KEY, a);
		mario.put(KeyInput.KEY, k);
		mario.put(Health.KEY, h);
		mario.put(DamageLauncher.KEY, launcher);
        mario.put(Player.KEY, play);

		Map<String, Component> mario2 = new HashMap<>();


		Position p2 = new Position(1, 100, 300);
		Dimension d2 = new Dimension(1, 100, 100);

		mario2.put(Position.KEY, p2);
		mario2.put(Dimension.KEY, d2);
		mario2.put(Sprite.KEY, s2);



		Position p3 = new Position(2, 300, 100);
		Dimension d3 = new Dimension(2, 100, 100);
		Velocity v3 = new Velocity(2, 0, 0);
		Acceleration a3 = new Acceleration(2, 0, 0);
		Health h3 = new Health(2,10);
		DamageLauncher launcher3 = new DamageLauncher(0,2,2); 
		Win win3 = new Win(2);

		List<Position> coordinates = new ArrayList<>();
		coordinates.add(new Position(-1, 500, 100));
		coordinates.add(new Position(-1, 200, 200));
		coordinates.add(new Position(-1, 500, 400));

		Map<String, Component> mario3 = new HashMap<>();
		mario3.put(Position.KEY, p3);
		mario3.put(Dimension.KEY, d3);
		mario3.put(Sprite.KEY, s3);
		mario3.put(Velocity.KEY, v3);
		mario3.put(Acceleration.KEY, a3);
		mario3.put(Health.KEY, h3);
		mario3.put(DamageLauncher.KEY, launcher3);
		mario3.put(Win.KEY, win3);
		AI ai = new AI(2);
		ai.setAction( actions.patrol(mario3, coordinates));
		mario3.put(AI.KEY, ai);

		Position p4 = new Position(3, 300, 300);
		Dimension d4 = new Dimension(3, 100, 100);

		Map<String, Component> mario4 = new HashMap<>();

		mario4.put(Position.KEY, p4);
		mario4.put(Dimension.KEY, d4);
		mario4.put(Sprite.KEY, s4);

		/**
		Conditional co1 = new Conditional(0);
		Supplier su1 = (Supplier & Serializable) () -> p3.getXPos();
		Supplier su2 = (Supplier & Serializable) () -> p.getYPos();

		co1.setCondition(su1, su2);

		Consumer<Object> consumer = (Consumer<Object> & Serializable) (e) -> {
				Position newPos = (Position) e;
				double x = newPos.getXPos();
				double y = newPos.getYPos();

				newPos.setXPos(p3.getXPos());
				newPos.setYPos(p3.getYPos());

				p3.setXPos(x);
				p3.setYPos(y+5);
		};
		co1.setAction(p, consumer);

		mario.put(Conditional.KEY, co1);
		 **/

		Position p5 = new Position(0, 100, 100);
		Dimension d5 = new Dimension(0, 100, 100);
		Velocity v5 = new Velocity(0, 0, 0);

		Acceleration a5 = new Acceleration(0, 0, 0);
		KeyInput k5 = new KeyInput(0);
		k5.addCode( KeyCode.RIGHT, (Consumer & Serializable) (e) -> {
			v5.setXVel(+150);
		});
		k5.addCode(KeyCode.UP, (Consumer & Serializable)(e) ->
		{
			v5.setYVel(-150);
		});
		k5.addCode(KeyCode.DOWN,(Consumer & Serializable) (e) ->
		{
			v5.setYVel(+150);
		});
		k5.addCode(KeyCode.LEFT,(Consumer & Serializable) (e) ->
		{
			v5.setXVel(-150);
		});
		Health h5 = new Health(0,10);
		DamageLauncher launcher5 = new DamageLauncher(0,2,2);

		Player play5 = new Player(0, 3);
		play.setRespawn(p.clone());
		/**k.addCode(KeyCode.R, (Runnable & Serializable) () ->
		 {
		 play.respawn(p, v, a);
		 });**/

		Map<String, Component> mario5 = new HashMap<>();
		mario5.put(Position.KEY, p5);
		mario5.put(Dimension.KEY, d5);
		mario5.put(Sprite.KEY, s5);

		mario5.put(Velocity.KEY, v5);
		mario5.put(Acceleration.KEY, a5);
		mario5.put(KeyInput.KEY, k5);
		mario5.put(Health.KEY, h5);
		mario5.put(DamageLauncher.KEY, launcher5);
		mario5.put(Player.KEY, play5);



		entities.put(0, mario);
		entities.put(1, mario2);
		entities.put(2, mario3);
		entities.put(3, mario4);

		entities2.put(0, mario5);

		GameInitializer gi = new GameInitializer(entities, 300, 50, 50);
		ih = gi.getInputHandler();
		eng = new InternalEngine(gi.getSystems());

		Map<Level, Map<Integer,Map<String,Component>>> state = new HashMap<>();
		Level l = new Level(1);
		Level l2 = new Level(2);
		state.put(l,entities);
		state.put(l2, entities2);
		DataGameState dState = new DataGameState(state, "DemoDemo");
		try {
			DataWrite.saveFile(dState);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public Map<Integer, Map<String, Component>> getEntities() {
		return entities;
	}


	public void run(Renderer r) {
		FixedSteps fs = new FixedSteps((time) -> eng.update(time), r, (fps) -> System.out.println("FPS: " + fps));
		fs.start();
	}

	public InputHandler getIH() { return ih; }

}
