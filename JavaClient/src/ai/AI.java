package ai;

import ks.KSObject;
import ks.commands.ActivateWallBreaker;
import ks.commands.ChangeDirection;
import ks.commands.ECommandDirection;
import ks.models.ECell;
import ks.models.EDirection;
import ks.models.World;
//import sun.jvm.hotspot.types.JBooleanField;
import team.koala.chillin.client.RealtimeAI;

import javax.swing.text.EditorKit;
import java.sql.Array;
import java.sql.SQLOutput;
import java.util.Arrays;


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
        int wallBeakerRem = world.getConstants().getWallBreakerDuration();
        if (world.getAgents().get(mySide).getWallBreakerRemTime() != 0)
            wallBeakerRem = world.getAgents().get(mySide).getWallBreakerRemTime();
        else if (world.getAgents().get(mySide).getWallBreakerCooldown() <= 6 && world.getAgents().get(mySide).getWallBreakerCooldown() != 0)
            wallBeakerRem = 0;

        int bestPoint = DFS(currentY, currentX, wallBeakerRem, 0, 0, world.getAgents().get(mySide).getHealth(), dir);

        if (dir == EDirection.Up && world.getBoard().get(currentY - 1).get(currentX) != ECell.Empty)
            ActivateWallBreaker();
        if (dir == EDirection.Down && world.getBoard().get(currentY + 1).get(currentX) != ECell.Empty)
            ActivateWallBreaker();
        if (dir == EDirection.Left && world.getBoard().get(currentY).get(currentX - 1) != ECell.Empty)
            ActivateWallBreaker();
        if (dir == EDirection.Right && world.getBoard().get(currentY).get(currentX + 1) != ECell.Empty)
            ActivateWallBreaker();


        changeDirection(dir);
    }

    public int DFS(int locationY, int locationX, int wallBrakeRem, int score, int movesCnt,
                   int health, EDirection lastDirection) {
        if (movesCnt == 12) {
            if (world.getBoard().get(locationY).get(locationX) != ECell.Empty) return (score - 5);
            return score;
        }

        if (wallBrakeRem > 0 && (world.getBoard().get(locationY).get(locationX) != ECell.Empty || wallBrakeRem < world.getConstants().getWallBreakerDuration()))
            --wallBrakeRem;
        else if (wallBrakeRem == 0 && health > 0 && world.getBoard().get(locationY).get(locationX) != ECell.Empty) {
            --health;
            score -= 5;
        }
        if (health == 0) {
            if (world.getBoard().get(locationY).get(locationX) == enemyCell)
                score += world.getConstants().getEnemyWallCrashScore();
            else score += world.getConstants().getMyWallCrashScore();

            return score;
        }

        // change score
        if (world.getBoard().get(locationY).get(locationX) == enemyCell) score += 2;
        if (world.getBoard().get(locationY).get(locationX) == ECell.Empty) score += 1;

        int down = -30000, up = -30000, right = -30000, left = -30000;
        // check down
        int newLocation = (locationY + 1) * world.getBoard().get(0).size() + locationX;
        if (lastDirection != EDirection.Up && world.getBoard().get(locationY + 1).get(locationX) != ECell.AreaWall ) {
            down = DFS(locationY + 1, locationX, wallBrakeRem, score, movesCnt + 1, health, EDirection.Down);
        }

        //check up
        newLocation = (locationY - 1) * world.getBoard().get(0).size() + locationX;
        if (lastDirection != EDirection.Down && world.getBoard().get(locationY - 1).get(locationX) != ECell.AreaWall) {
            up = DFS(locationY - 1, locationX, wallBrakeRem, score, movesCnt + 1, health, EDirection.Up);
        }

        //check right
        newLocation = locationY * world.getBoard().get(0).size() + locationX + 1;
        if (lastDirection != EDirection.Left && world.getBoard().get(locationY).get(locationX + 1) != ECell.AreaWall) {
            right = DFS(locationY, locationX + 1, wallBrakeRem, score, movesCnt + 1, health, EDirection.Right);
        }

        //check left
        newLocation = locationY * world.getBoard().get(0).size() + locationX - 1;
        if (lastDirection != EDirection.Right && world.getBoard().get(locationY).get(locationX - 1) != ECell.AreaWall) {
            left = DFS(locationY, locationX - 1, wallBrakeRem, score, movesCnt + 1, health, EDirection.Left);
        }
        if (movesCnt == 0) DFSdirection(up, down, left, right);
        return maxScore(down, up, right, left);
    }

    private int maxScore(int a, int b, int c, int d) {
        if (a >= b && a >= c && a >= d) return a;
        if (b >= a && b >= c && b >= d) return b;
        if (c >= a && c >= b && c >= d) return c;
        return d;
    }

    private void DFSdirection(int up, int down, int left, int right) {
        int[] scores = {up, down, left, right};
        int start = 0;
        Arrays.sort(scores);
        while (scores[start] == -30000) ++start;

        boolean chooseWisely = false;
        if (scores[start] == scores[3])
            chooseWisely = findNearestEnemyWall(world.getAgents().get(this.mySide).getPosition().getX(), world.getAgents().get(this.mySide).getPosition().getY());

        if (!chooseWisely) {
            if (up >= down && up >= left && up >= right) dir = EDirection.Up;
            else if (down >= up && down >= left && down >= right) dir = EDirection.Down;
            else if (left >= up && left >= down && left >= right) dir = EDirection.Left;
            else if (right >= up && right >= down && right >= left) dir = EDirection.Right;
        }
    }

    private void beCarefulForAreaWalls(int currentX, int currentY) {
        if (world.getAgents().get(this.mySide).getWallBreakerCooldown() == 0)
            ActivateWallBreaker();

        EDirection temp = dir;
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

    private boolean findNearestEmptyCell(int currentX, int currentY) {
        if (world.getBoard().get(currentY + 1).get(currentX) == ECell.Empty && dir != EDirection.Up) {
            dir = EDirection.Down;
            return true;
        }
        if (world.getBoard().get(currentY - 1).get(currentX) == ECell.Empty && dir != EDirection.Down) {
            dir = EDirection.Up;
            return true;
        }
        if (world.getBoard().get(currentY).get(currentX + 1) == ECell.Empty && dir != EDirection.Left) {
            dir = EDirection.Right;
            return true;
        }
        if (world.getBoard().get(currentY).get(currentX - 1) == ECell.Empty && dir != EDirection.Right) {
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
        if (nearestY > currentY && world.getBoard().get(currentY + 1).get(currentX) == ECell.Empty && dir != EDirection.Up) {
            dir = EDirection.Down;
            return true;
        }
        if (nearestY < currentY && world.getBoard().get(currentY - 1).get(currentX) == ECell.Empty && dir != EDirection.Down) {
            dir = EDirection.Up;
            return true;
        }
        if (nearestX > currentX && world.getBoard().get(currentY).get(currentX + 1) == ECell.Empty && dir != EDirection.Left) {
            dir = EDirection.Right;
            return true;
        }
        if (nearestX < currentX && world.getBoard().get(currentY).get(currentX - 1) == ECell.Empty && dir != EDirection.Right) {
            dir = EDirection.Left;
            return true;
        }
        return false;
    }


    private boolean breakNearbyEnemyWalls(int currentX, int currentY) {
        if (world.getAgents().get(this.mySide).getWallBreakerCooldown() == 0 || world.getAgents().get(this.mySide).getWallBreakerRemTime() > 1) {
            if (world.getBoard().get(currentY + 1).get(currentX) == enemyCell && dir != EDirection.Up) {
                if (world.getAgents().get(this.mySide).getWallBreakerCooldown() == 0) ActivateWallBreaker();
                dir = EDirection.Down;
                return true;
            }

            if (world.getBoard().get(currentY - 1).get(currentX) == enemyCell && dir != EDirection.Down) {
                if (world.getAgents().get(this.mySide).getWallBreakerCooldown() == 0) ActivateWallBreaker();
                dir = EDirection.Up;
                return true;
            }
            if (world.getBoard().get(currentY).get(currentX + 1) == enemyCell && dir != EDirection.Left) {
                if (world.getAgents().get(this.mySide).getWallBreakerCooldown() == 0) ActivateWallBreaker();
                dir = EDirection.Right;
                return true;
            }
            if (world.getBoard().get(currentY).get(currentX - 1) == enemyCell && dir != EDirection.Right) {
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