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
        System.out.println(world.getAgents().get(this.mySide).getPosition().getX());
    }

    @Override
    public void decide() {
        int currentX = world.getAgents().get(this.mySide).getPosition().getX();
        int currentY = world.getAgents().get(this.mySide).getPosition().getY();
        ECell enemyColour = ECell.YellowWall;
        if (mySide.equals("Yellow")) enemyColour = ECell.BlueWall;
        boolean choseWisely = false;

        //brake nearby enemy walls
        if (world.getAgents().get(this.mySide).getWallBreakerCooldown() == 0 || world.getAgents().get(this.mySide).getWallBreakerRemTime() > 1) {
            if (world.getBoard().get(currentY + 1).get(currentX) == enemyColour) {
                choseWisely = true;
                dir = EDirection.Down;
                if (world.getAgents().get(this.mySide).getWallBreakerCooldown() == 0) ActivateWallBreaker();
            }
            if (world.getBoard().get(currentY - 1).get(currentX) == enemyColour) {
                choseWisely = true;
                dir = EDirection.Up;
                if (world.getAgents().get(this.mySide).getWallBreakerCooldown() == 0) ActivateWallBreaker();

            }
            if (world.getBoard().get(currentY).get(currentX + 1) == enemyColour) {
                choseWisely = true;
                dir = EDirection.Right;
                if (world.getAgents().get(this.mySide).getWallBreakerCooldown() == 0) ActivateWallBreaker();
            }
            if (world.getBoard().get(currentY).get(currentX - 1) == enemyColour) {
                choseWisely = true;
                dir = EDirection.Left;
                if (world.getAgents().get(this.mySide).getWallBreakerCooldown() == 0) ActivateWallBreaker();
            }
        }


        // Find nearest enemy wall
        if (choseWisely == false) {

            int nearestX = 1, nearestY = 1, minDistance = 10000000;

            for (int i = 0; i < world.getBoard().size(); i++) {
                for (int j = 0; j < world.getBoard().get(0).size(); j++) {
                    int distance = (i - currentY) * (i - currentY) + (j - currentX) * (j - currentX);
                    if (distance < minDistance && world.getBoard().get(i).get(j) == enemyColour) {
                        nearestY = i;
                        nearestX = j;
                        minDistance = distance;
                    }
                }
            }
                if (nearestY > currentY && world.getBoard().get(currentY + 1).get(currentX) == ECell.Empty) {
                    choseWisely = true;
                    dir = EDirection.Down;
                }
                if (nearestY < currentY && world.getBoard().get(currentY - 1).get(currentX) == ECell.Empty) {
                    choseWisely = true;
                    dir = EDirection.Up;
                }
                if (nearestX > currentX && world.getBoard().get(currentY).get(currentX + 1) == ECell.Empty) {
                    choseWisely = true;
                    dir = EDirection.Right;
                }
                if (nearestX < currentX && world.getBoard().get(currentY).get(currentX - 1) == ECell.Empty) {
                    choseWisely = true;
                    dir = EDirection.Left;
                }

        }


        // Find empty nearby cell
        if (choseWisely == false) {
            if (world.getBoard().get(currentY + 1).get(currentX) == ECell.Empty) {
                choseWisely = true;
                dir = EDirection.Down;
            }
            if (world.getBoard().get(currentY - 1).get(currentX) == ECell.Empty) {
                choseWisely = true;
                dir = EDirection.Up;
            }
            if (world.getBoard().get(currentY).get(currentX + 1) == ECell.Empty) {
                choseWisely = true;
                dir = EDirection.Right;
            }
            if (world.getBoard().get(currentY).get(currentX - 1) == ECell.Empty) {
                choseWisely = true;
                dir = EDirection.Left;
            }
        }

        // Be careful for area walls
        if (choseWisely == false) {
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

        changeDirection(dir);
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