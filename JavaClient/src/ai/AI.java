package ai;

import java.util.*;

import team.koala.chillin.client.RealtimeAI;

import ks.KSObject;
import ks.models.*;
import ks.commands.*;


public class AI extends RealtimeAI<World, KSObject> {

	private Random random = new Random();

	public AI(World world) {
		super(world);
	}

	@Override
	public void initialize() {
		System.out.println("initialize");
	}

	@Override
	public void decide() {
		System.out.println("decide");

		int randIndex = (int) (Math.random() * EDirection.values().length);
		EDirection randDir = EDirection.values()[randIndex];
		changeDirection(randDir);

		if (this.world.getAgents().get(this.mySide).getWallBreakerCooldown() == 0)
			ActivateWallBreaker();
	}


	public void changeDirection(EDirection direction)
	{
		final ECommandDirection dir = ECommandDirection.of(direction.getValue()); 
		this.sendCommand(new ChangeDirection() {
			{ direction = dir; }
		});
	}

	public void ActivateWallBreaker()
	{ 
		this.sendCommand(new ActivateWallBreaker());
	}
}
