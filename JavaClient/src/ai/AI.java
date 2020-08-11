package ai;

import ks.KSObject;
import ks.commands.ActivateWallBreaker;
import ks.commands.ChangeDirection;
import ks.commands.ECommandDirection;
import ks.models.EDirection;
import ks.models.World;
import team.koala.chillin.client.RealtimeAI;

public class AI extends RealtimeAI<World, KSObject> {
    private EDirection dir = EDirection.Right;
	private int cycleCnt = 0;

    public AI(World world) {
        super(world);
    }

    @Override
    public void initialize() {
        System.out.println("initialize");
    }

    @Override
    public void decide() {
    	cycleCnt++;
    	dir =  switch (cycleCnt) {
			case 4 ->  EDirection.Down;
			case 6 ->  EDirection.Left;
			case 8 ->  EDirection.Up;
			case 10 -> EDirection.Right;
    		default -> dir;
		};
		System.out.println("decide");

//		int randIndex = (int) (Math.random() * EDirection.values().length);
//		EDirection randDir = EDirection.values()[randIndex];
		changeDirection(dir);
        if (this.world.getAgents().get(this.mySide).getWallBreakerCooldown() == 0)
            ActivateWallBreaker();
    }


    public void changeDirection(EDirection direction) {
        final ECommandDirection dir = ECommandDirection.of(direction.getValue());
        this.sendCommand(new ChangeDirection() {
            {
                direction = dir;
            }
        });
    }

    public void ActivateWallBreaker() {
        this.sendCommand(new ActivateWallBreaker());
    }
}
