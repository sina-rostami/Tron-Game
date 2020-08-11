package ai;

import ks.KSObject;
import ks.commands.ActivateWallBreaker;
import ks.commands.ChangeDirection;
import ks.commands.ECommandDirection;
import ks.models.ECell;
import ks.models.EDirection;
import ks.models.World;
import team.koala.chillin.client.RealtimeAI;

public class AI extends RealtimeAI<World, KSObject> {
    private EDirection dir = EDirection.Down;
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

        if(world.getBoard().get(world.getAgents().get(this.mySide).getPosition().getY() + 1).get(world.getAgents().get(this.mySide).getPosition().getX()) == ECell.AreaWall) {
    	    dir = EDirection.Right;
        }
        if(world.getBoard().get(world.getAgents().get(this.mySide).getPosition().getY()).get(world.getAgents().get(this.mySide).getPosition().getX() + 4) == ECell.YellowWall) {
            ActivateWallBreaker();
        }
		System.out.println("decide");
        world.getScores().get(this.otherSide);
		changeDirection(dir);
    }


    public void changeDirection(EDirection direction) {
        final ECommandDirection dir = ECommandDirection.of(direction.getValue());
        this.sendCommand(new ChangeDirection() {
            { direction = dir; }
        });
    }

    public void ActivateWallBreaker() {
        this.sendCommand(new ActivateWallBreaker());
    }
}
