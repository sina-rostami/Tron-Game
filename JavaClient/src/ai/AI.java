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
        // todo : change enemy colour to enemy cell
        System.out.println("initialize");
    }

    @Override
    public void decide() {



        int currentX = world.getAgents().get(this.mySide).getPosition().getX();
        int currentY = world.getAgents().get(this.mySide).getPosition().getY();
        ECell enemyColour = ECell.YellowWall;
        if (mySide.equals("Yellow")) enemyColour = ECell.BlueWall;
        boolean chooseWisely = false;

        //break nearby enemy walls
        chooseWisely = breakNearbyEnemyWalls(currentX, currentY, enemyColour, chooseWisely);


        // Find nearest enemy wall
        if (chooseWisely == false) {
            chooseWisely = findNearestEnemyWall(currentX, currentY, enemyColour, chooseWisely);
        }


        // Find empty nearby cell
        if (chooseWisely == false) {
            chooseWisely = findNearestEmptyCell(currentX, currentY, chooseWisely);
        }

        // Be careful for area walls
        if (chooseWisely == false) {
            beCarefulForAreaWalls(currentX, currentY);
        }

        changeDirection(dir);

        if(currentCycle.compareTo(124) > 0)
        System.out.println(mySide + "  " + chooseWisely + "  " + dir);
    }

    private void beCarefulForAreaWalls(int currentX, int currentY) {
        EDirection temp = dir;

        if (world.getAgents().get(this.mySide).getWallBreakerCooldown() == 0) {
            ActivateWallBreaker();
        }
        if (world.getBoard().get(currentY + 1).get(currentX) != ECell.AreaWall && dir != EDirection.Up)
            temp = EDirection.Down;

        if (world.getBoard().get(currentY - 1).get(currentX) != ECell.AreaWall && dir != EDirection.Down)
            temp = EDirection.Up;

        if (world.getBoard().get(currentY).get(currentX + 1) != ECell.AreaWall && dir != EDirection.Left)
            temp = EDirection.Right;

        if (world.getBoard().get(currentY).get(currentX - 1) != ECell.AreaWall && dir != EDirection.Right)
            temp = EDirection.Left;
        dir = temp;
    }

    private boolean findNearestEmptyCell(int currentX, int currentY, boolean chooseWisely) {
        EDirection temp = dir;

        if (world.getBoard().get(currentY + 1).get(currentX) == ECell.Empty && dir != EDirection.Up) {
            chooseWisely = true;
            temp = EDirection.Down;
        }
        if (world.getBoard().get(currentY - 1).get(currentX) == ECell.Empty && dir != EDirection.Down) {
            chooseWisely = true;
            temp = EDirection.Up;
        }
        if (world.getBoard().get(currentY).get(currentX + 1) == ECell.Empty && dir != EDirection.Left) {
            chooseWisely = true;
            temp = EDirection.Right;
        }
        if (world.getBoard().get(currentY).get(currentX - 1) == ECell.Empty && dir != EDirection.Right) {
            chooseWisely = true;
            temp = EDirection.Left;
        }
        dir = temp;
        return chooseWisely;
    }

    private boolean findNearestEnemyWall(int currentX, int currentY, ECell enemyColour, boolean choseWisely) {
        EDirection temp = dir;
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
        if (nearestY > currentY && world.getBoard().get(currentY + 1).get(currentX) == ECell.Empty && dir != EDirection.Up) {
            choseWisely = true;
            temp = EDirection.Down;
        }
        if (nearestY < currentY && world.getBoard().get(currentY - 1).get(currentX) == ECell.Empty && dir != EDirection.Down) {
            choseWisely = true;
            temp = EDirection.Up;
        }
        if (nearestX > currentX && world.getBoard().get(currentY).get(currentX + 1) == ECell.Empty && dir != EDirection.Left) {
            choseWisely = true;
            temp = EDirection.Right;
        }
        if (nearestX < currentX && world.getBoard().get(currentY).get(currentX - 1) == ECell.Empty && dir != EDirection.Right) {
            choseWisely = true;
            temp = EDirection.Left;
        }
        dir = temp;
        return choseWisely;
    }

    private boolean breakNearbyEnemyWalls(int currentX, int currentY, ECell enemyColour, boolean choseWisely) {
        EDirection temp = dir;

        if (world.getAgents().get(this.mySide).getWallBreakerCooldown() == 0 || world.getAgents().get(this.mySide).getWallBreakerRemTime() > 1) {
            if (world.getBoard().get(currentY + 1).get(currentX) == enemyColour && dir != EDirection.Up) {
                choseWisely = true;
                temp = EDirection.Down;
                if (world.getAgents().get(this.mySide).getWallBreakerCooldown() == 0) ActivateWallBreaker();
            }
            if (world.getBoard().get(currentY - 1).get(currentX) == enemyColour && dir != EDirection.Down) {
                choseWisely = true;
                temp = EDirection.Up;
                if (world.getAgents().get(this.mySide).getWallBreakerCooldown() == 0) ActivateWallBreaker();

            }
            if (world.getBoard().get(currentY).get(currentX + 1) == enemyColour && dir != EDirection.Left) {
                choseWisely = true;
                temp = EDirection.Right;
                if (world.getAgents().get(this.mySide).getWallBreakerCooldown() == 0) ActivateWallBreaker();
            }
            if (world.getBoard().get(currentY).get(currentX - 1) == enemyColour && dir != EDirection.Right) {
                choseWisely = true;
                temp = EDirection.Left;
                if (world.getAgents().get(this.mySide).getWallBreakerCooldown() == 0) ActivateWallBreaker();
            }
        }
        dir = temp;
        return choseWisely;
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