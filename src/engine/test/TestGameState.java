package engine.test;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import authoring.gamestate.Level;
import data.DataGameState;
import data.DataWrite;
import engine.Engine;
import engine.InternalEngine;
import engine.actions.Actions;
import engine.components.*;

import engine.setup.GameInitializer;
import engine.systems.InputHandler;
import javafx.scene.input.KeyCode;

public class TestGameState {

	private Map<Integer, Map<String, Component>> entities;
	private Engine eng;
	private InputHandler ih;


	public TestGameState() throws FileNotFoundException {
		System.out.println("TestGameState");
		entities = new HashMap<>();
		//ActionReader AR = new ActionReader();
		
		Sprite s = new Sprite(0,"Blob_Walk.png");
		//Sprite s4 = new Sprite(3,"mario.png");

		Sprite s4 = new Sprite(3,"8Bit.png");


		XPosition px = new XPosition(0, 100);
		YPosition py = new YPosition(0, 100);
		Collidable c = new Collidable(0);
		Width w = new Width(0, 100);
		Height h = new Height(0, 100);
		XVelocity vx = new XVelocity(0, 0);
		YVelocity vy = new YVelocity(0, 0);
		Animated a = new Animated(0);
		XAcceleration ax = new XAcceleration(0, 0);
		YAcceleration ay = new YAcceleration(0,40);
		KeyInput k = new KeyInput(0);
		k.addCode( KeyCode.RIGHT, (Consumer<Map<String,Component>> & Serializable) (map) -> {
			Actions.moveRight(100).accept(map);
			//Actions.animateSprite("braid.png", 1000, 27, 7, 0, 0, 75, 85).accept(map);
			if(!s.isPlaying()) 	s.animate(1000, 24, 6, 0, 0, 93, 158);//s.animate(1000, 27, 7, 0, 0, 75, 85);	
			s.getImage().setScaleX(1);
			//Actions.accelerateRight(100).accept(map);
		});
		k.addCode(KeyCode.UP, (Consumer & Serializable)(e) ->
		{
			vy.setData(-50);
		});
		k.addCode(KeyCode.DOWN,(Consumer & Serializable) (e) ->
		{
			vy.setData(+50);
		});
		k.addCode(KeyCode.LEFT,(Consumer & Serializable) (e) ->
		{
			vx.setData(-50);
			//if(!s.isPlaying()) s.animate(1000, 27, 7, 0, 0, 75, 85);						//s.animate(1000, 24, 6, 0, 0, 93, 158);
			s.getImage().setScaleX(-1);
		});
		k.addCode(KeyCode.SPACE,(Consumer & Serializable) (e) ->
		{
			vx.setData(0);
		});
		Health health = new Health(0,10);
		DamageValue damage = new DamageValue(0, 10);
		DamageLifetime dl = new DamageLifetime(0,1);
		
		Player play = new Player(0);
		Lives lives = new Lives(0,3);

		Map<String, Component> mario = new HashMap<>();
		mario.put(XPosition.KEY, px);
		mario.put(Collidable.KEY, c);
		mario.put(YPosition.KEY, py);
		mario.put(Height.KEY, h);
		mario.put(Lives.KEY, lives);
		mario.put(Width.KEY, w);
		mario.put(Sprite.KEY, s);

		mario.put(XVelocity.KEY, vx);
		mario.put(YVelocity.KEY, vy);

		mario.put(XAcceleration.KEY, ax);
		mario.put(YAcceleration.KEY, ay);

		mario.put(KeyInput.KEY, k);
		mario.put(Health.KEY, health);
		mario.put(DamageValue.KEY, damage);
		mario.put(DamageLifetime.KEY, dl);
		mario.put(Player.KEY, play);
		mario.put(Animated.KEY, a);
		//Map<String, Component> mario2 = new HashMap<>();

		/**
		 Position p2 = new Position(1, 100, 300);
		 Dimension d2 = new Dimension(1, 100, 100);

		 mario2.put(Position.KEY, p2);
		 mario2.put(Dimension.KEY, d2);
		 mario2.put(Sprite.KEY, s2);**/



		/**
		 Position p3 = new Position(2, 300, 100);
		 Dimension d3 = new Dimension(2, 100, 100);
		 Velocity v3 = new Velocity(2, 0, 0);
		 Acceleration a3 = new Acceleration(2, 0, 0);
		 Health h3 = new Health(2,10);
		 DamageLauncher launcher3 = new DamageLauncher(0,2,2);
		 Win win3 = new Win(2);

		 List<Object> arguments = new ArrayList<>();
		 Map<String, Component> mario3 = new HashMap<>();
		 arguments.add(mario3);
		 List<Position> coordinates = new ArrayList<>();
		 coordinates.add(new Position(-1, 500, 100));
		 coordinates.add(new Position(-1, 200, 200));
		 coordinates.add(new Position(-1, 500, 400));
		 arguments.add(coordinates);

		 mario3.put(Position.KEY, p3);
		 mario3.put(Dimension.KEY, d3);
		 mario3.put(Sprite.KEY, s3);
		 mario3.put(Velocity.KEY, v3);
		 mario3.put(Acceleration.KEY, a3);
		 mario3.put(Health.KEY, h3);
		 mario3.put(DamageLauncher.KEY, launcher3);
		 mario3.put(Win.KEY, win3);
		 AI ai = new AI(2);
		 ai.setAction(AR.getAction("patrol", arguments));
		 mario3.put(AI.KEY, ai);

		 **/


		XPosition xp4 = new XPosition(3, 300);
		YPosition yp4 = new YPosition(3, 300);
		Collidable c4 = new Collidable(3);
		Width w4 = new Width(3, 100);
		Height h4 = new Height(3, 100);
		Health health4 = new Health(3, 10);

		Map<String, Component> mario4 = new HashMap<>();

		mario4.put(XPosition.KEY, xp4);
		mario4.put(YPosition.KEY, yp4);
		mario4.put(Width.KEY, w4);
		mario4.put(Height.KEY, h4);
		mario4.put(Sprite.KEY, s4);
		mario4.put(Health.KEY, health4);
		mario4.put(Collidable.KEY, c4);


		/**Conditional co1 = new Conditional(0);
		 Supplier su1 = (Supplier & Serializable) () -> null;

		 co1.setCondition(su1);

		 Consumer consumer = (Consumer & Serializable) (e) -> {
		 int i = 10;
		 };
		 co1.setAction(consumer);

		 mario.put(Conditional.KEY, co1);**/

		entities.put(0, mario);
		//entities.put(1, mario2);
		//entities.put(2, mario3);
		entities.put(3, mario4);
		GameInitializer gi = new GameInitializer(entities, 300, 50, 50);
		ih = gi.getInputHandler();
		eng = new InternalEngine(gi.getSystems());

		Map<Level, Map<Integer,Map<String,Component>>> state = new HashMap<>();
		Level l = new Level(1);
		state.put(l,entities);

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
