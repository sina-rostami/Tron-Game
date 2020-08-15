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
    private EDirection dir;
    private int cycleCnt = 0;

    public AI(World world) {
        super(world);

    }

    @Override
    public void initialize() {
        if (world.getBoard().get(world.getAgents().get(this.mySide).getPosition().getY() - 1).get(world.getAgents().get(this.mySide).getPosition().getX()) == ECell.AreaWall) {
            dir = EDirection.Down;
        } else {
            dir = EDirection.Up;
        }
        changeDirection(dir);
        System.out.println("initialize");
    }

    @Override
    public void decide() {
        cycleCnt++;
        areaWallCheck();
        agentWallCheck();
        System.out.println("decide");
        changeDirection(dir);
    }

    private void agentWallCheck() {
        int x = world.getAgents().get(this.mySide).getPosition().getX();
        int y = world.getAgents().get(this.mySide).getPosition().getY();
        if (dir == EDirection.Up) {
            if (world.getBoard().get(y - 1).get(x) == ECell.YellowWall || world.getBoard().get(y - 1).get(x) == ECell.BlueWall)
                ActivateWallBreaker();
        } else if(dir == EDirection.Down) {
            if (world.getBoard().get(y + 1).get(x) == ECell.YellowWall || world.getBoard().get(y + 1).get(x) == ECell.BlueWall)
                ActivateWallBreaker();
        } else if(dir == EDirection.Right) {
            if (world.getBoard().get(y).get(x + 1) == ECell.YellowWall || world.getBoard().get(y).get(x + 1) == ECell.BlueWall)
                ActivateWallBreaker();
        } else if(dir == EDirection.Left) {
            if (world.getBoard().get(y).get(x - 1) == ECell.YellowWall || world.getBoard().get(y).get(x - 1) == ECell.BlueWall)
                ActivateWallBreaker();
        }
    }

    private void areaWallCheck() {
        int x = world.getAgents().get(this.mySide).getPosition().getX();
        int y = world.getAgents().get(this.mySide).getPosition().getY();
        if (dir == EDirection.Up) {
            if (world.getBoard().get(y - 1).get(x) == ECell.AreaWall)
                dir = (world.getBoard().get(y).get(x + 1) == ECell.AreaWall) ? EDirection.Left : EDirection.Right;
        } else if(dir == EDirection.Down) {
            if (world.getBoard().get(y + 1).get(x) == ECell.AreaWall)
                dir = (world.getBoard().get(y).get(x + 1) == ECell.AreaWall) ? EDirection.Left : EDirection.Right;
        } else if(dir == EDirection.Right) {
            if (world.getBoard().get(y).get(x + 1) == ECell.AreaWall)
                dir = (world.getBoard().get(y + 1).get(x) == ECell.AreaWall) ? EDirection.Up : EDirection.Down;
        } else if(dir == EDirection.Left) {
            if (world.getBoard().get(y).get(x - 1) == ECell.AreaWall)
                dir = (world.getBoard().get(y + 1).get(x) == ECell.AreaWall) ? EDirection.Up : EDirection.Down;
        }
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
