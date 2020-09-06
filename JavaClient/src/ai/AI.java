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
    private ECell enemyCell;

    public AI(World world) {
        super(world);
    }

    @Override
    public void initialize() {
        if (world.getBoard().get(world.getAgents().get(this.mySide).getPosition().getY() - 1).get(world.getAgents().get(this.mySide).getPosition().getX()) == ECell.AreaWall)
            dir = EDirection.Down;
        else
            dir = EDirection.Up;
        changeDirection(dir);
        enemyCell = mySide.equals("Yellow") ? ECell.BlueWall : ECell.YellowWall;
        System.out.println("initialize");
    }

    @Override
    public void decide() {
        int currentX = world.getAgents().get(this.mySide).getPosition().getX();
        int currentY = world.getAgents().get(this.mySide).getPosition().getY();

        boolean chooseWisely = false;

        chooseWisely = breakNearbyEnemyWalls(currentX, currentY);

        // Find nearest enemy wall
        if (chooseWisely == false)
            chooseWisely = findNearestEnemyWall(currentX, currentY);

        // Find empty nearby cell
        if (chooseWisely == false)
            chooseWisely = findNearestEmptyCell(currentX, currentY);


        // Be careful for area walls
        if (chooseWisely == false)
            beCarefulForAreaWalls(currentX, currentY);

        changeDirection(dir);
    }
    private int BFS(int locationX,int locationY){


        // todo : delete this after implementing method
        return 0;
    }

    private void beCarefulForAreaWalls(int currentX, int currentY) {
        if (world.getAgents().get(this.mySide).getWallBreakerCooldown() == 0)
            ActivateWallBreaker();

        if (world.getBoard().get(currentY + 1).get(currentX) != ECell.AreaWall)
            dir = EDirection.Down;

        if (world.getBoard().get(currentY - 1).get(currentX) != ECell.AreaWall)
            dir = EDirection.Up;

        if (world.getBoard().get(currentY).get(currentX + 1) != ECell.AreaWall)
            dir = EDirection.Right;

        if (world.getBoard().get(currentY).get(currentX - 1) != ECell.AreaWall)
            dir = EDirection.Left;

    }

    private boolean findNearestEmptyCell(int currentX, int currentY) {
        if (world.getBoard().get(currentY + 1).get(currentX) == ECell.Empty) {
            dir = EDirection.Down;
            return true;
        }
        if (world.getBoard().get(currentY - 1).get(currentX) == ECell.Empty) {
            dir = EDirection.Up;
            return true;
        }
        if (world.getBoard().get(currentY).get(currentX + 1) == ECell.Empty) {
            dir = EDirection.Right;
            return true;
        }
        if (world.getBoard().get(currentY).get(currentX - 1) == ECell.Empty) {
            dir = EDirection.Left;
            return true;
        }
        return false;
    }


    private boolean findNearestEnemyWall(int currentX, int currentY) {
        int nearestX = 1, nearestY = 1, minDistance = 10000000;

        for (int i = 0; i < world.getBoard().size(); i++) {
            for (int j = 0; j < world.getBoard().get(0).size(); j++) {
                int distance = (i - currentY) * (i - currentY) + (j - currentX) * (j - currentX);
                if (distance < minDistance && world.getBoard().get(i).get(j) == enemyCell) {
                    nearestY = i;
                    nearestX = j;
                    minDistance = distance;
                }
            }
        }
        if (nearestY > currentY && world.getBoard().get(currentY + 1).get(currentX) == ECell.Empty) {
            dir = EDirection.Down;
            return true;
        }
        if (nearestY < currentY && world.getBoard().get(currentY - 1).get(currentX) == ECell.Empty) {
            dir = EDirection.Up;
            return true;
        }
        if (nearestX > currentX && world.getBoard().get(currentY).get(currentX + 1) == ECell.Empty) {
            dir = EDirection.Right;
            return true;
        }
        if (nearestX < currentX && world.getBoard().get(currentY).get(currentX - 1) == ECell.Empty) {
            dir = EDirection.Left;
            return true;
        }
        return false;
    }


    private boolean breakNearbyEnemyWalls(int currentX, int currentY) {
        if (world.getAgents().get(this.mySide).getWallBreakerCooldown() == 0 || world.getAgents().get(this.mySide).getWallBreakerRemTime() > 1) {
            if (world.getBoard().get(currentY + 1).get(currentX) == enemyCell) {
                if (world.getAgents().get(this.mySide).getWallBreakerCooldown() == 0) ActivateWallBreaker();
                dir = EDirection.Down;
                return true;
            }

            if (world.getBoard().get(currentY - 1).get(currentX) == enemyCell) {
                if (world.getAgents().get(this.mySide).getWallBreakerCooldown() == 0) ActivateWallBreaker();
                dir = EDirection.Up;
                return true;
            }
            if (world.getBoard().get(currentY).get(currentX + 1) == enemyCell) {
                if (world.getAgents().get(this.mySide).getWallBreakerCooldown() == 0) ActivateWallBreaker();
                dir = EDirection.Right;
                return true;
            }
            if (world.getBoard().get(currentY).get(currentX - 1) == enemyCell) {
                if (world.getAgents().get(this.mySide).getWallBreakerCooldown() == 0) ActivateWallBreaker();
                dir = EDirection.Left;
                return true;
            }
        }
        return false;
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