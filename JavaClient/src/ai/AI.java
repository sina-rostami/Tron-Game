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
    private ECell myCell;

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
        myCell = mySide.equals("Yellow") ? ECell.YellowWall : ECell.BlueWall;
        System.out.println("initialize");

        //System.out.println(world.getConstants().getWallBreakerDuration());
    }
    // todo : add bfs to find nearest enemy wall
    // todo : best first search
    // todo : go to more empty places

    @Override
    public void decide() {
        int currentX = world.getAgents().get(this.mySide).getPosition().getX();
        int currentY = world.getAgents().get(this.mySide).getPosition().getY();
        int wallbrakerCool = world.getAgents().get(mySide).getWallBreakerCooldown();
        if (world.getAgents().get(mySide).getWallBreakerRemTime() == 0 && world.getAgents().get(mySide).getWallBreakerCooldown() == 0)
            wallbrakerCool = 12;
        dir = world.getAgents().get(mySide).getDirection();

        //BFSinWorld bfs = new BFSinWorld(world, world.getAgents().get(mySide), enemyCell, myCell);
        //dir = bfs.doBfs();
        DFS(currentY, currentX, wallbrakerCool, 0, 0, world.getAgents().get(mySide).getHealth(), dir);


        WallBeakerCheck(currentX, currentY);

        changeDirection(dir);
    }


    private void WallBeakerCheck(int currentX, int currentY) {
        if (dir == EDirection.Up && world.getBoard().get(currentY - 1).get(currentX) != ECell.Empty)
            ActivateWallBreaker();
        if (dir == EDirection.Down && world.getBoard().get(currentY + 1).get(currentX) != ECell.Empty)
            ActivateWallBreaker();
        if (dir == EDirection.Left && world.getBoard().get(currentY).get(currentX - 1) != ECell.Empty)
            ActivateWallBreaker();
        if (dir == EDirection.Right && world.getBoard().get(currentY).get(currentX + 1) != ECell.Empty)
            ActivateWallBreaker();
    }

    public int DFS(int locationY, int locationX, int wallBrakeCool, int score, int movesCnt,
                   int health, EDirection lastDirection) {

        if (movesCnt == 12) {
            if (world.getBoard().get(locationY).get(locationX) != ECell.Empty)
                return score - 80;
            return score;
        }
        if (locationY == world.getAgents().get(otherSide).getPosition().getY() && locationX == world.getAgents().get(otherSide).getPosition().getX()) {
            if (movesCnt <= 3) return score + 10;
            if (world.getScores().get(mySide) > world.getScores().get(otherSide)) return score + 3;
            else return score - 40;
        }

        if (movesCnt != 0) {
            if (wallBrakeCool < 12 || world.getBoard().get(locationY).get(locationX) != ECell.Empty) --wallBrakeCool;
            if (wallBrakeCool == -1) wallBrakeCool = 12;

            if (wallBrakeCool < 6 && health > 0 && world.getBoard().get(locationY).get(locationX) != ECell.Empty) {
                --health;
                score -= 20;
            }
            if (health == 0) {
                if (world.getBoard().get(locationY).get(locationX) == enemyCell)
                    score += world.getConstants().getEnemyWallCrashScore();
                else score += world.getConstants().getMyWallCrashScore();
                return score;
            }

            if (world.getBoard().get(locationY).get(locationX) == enemyCell) score += 3;
            if (world.getBoard().get(locationY).get(locationX) == ECell.Empty) score += 1;
            if (world.getBoard().get(locationY).get(locationX) == myCell) score -= 1;

            int dist = Math.abs(world.getAgents().get(otherSide).getPosition().getY() - locationY) + Math.abs(world.getAgents().get(otherSide).getPosition().getX() - locationX);
            if (world.getScores().get(mySide) <= world.getScores().get(otherSide) && dist <= 1)
                return score - 20;
        }

        int down = -30000, up = -30000, right = -30000, left = -30000;

        if (lastDirection != EDirection.Up && world.getBoard().get(locationY + 1).get(locationX) != ECell.AreaWall)
            down = DFS(locationY + 1, locationX, wallBrakeCool, score, movesCnt + 1, health, EDirection.Down);

        if (lastDirection != EDirection.Down && world.getBoard().get(locationY - 1).get(locationX) != ECell.AreaWall)
            up = DFS(locationY - 1, locationX, wallBrakeCool, score, movesCnt + 1, health, EDirection.Up);

        if (lastDirection != EDirection.Left && world.getBoard().get(locationY).get(locationX + 1) != ECell.AreaWall)
            right = DFS(locationY, locationX + 1, wallBrakeCool, score, movesCnt + 1, health, EDirection.Right);

        if (lastDirection != EDirection.Right && world.getBoard().get(locationY).get(locationX - 1) != ECell.AreaWall)
            left = DFS(locationY, locationX - 1, wallBrakeCool, score, movesCnt + 1, health, EDirection.Left);

        if (movesCnt == 0) DFSDirection(up, down, left, right);

        return maxFour(down, up, right, left);
    }


    private void DFSDirection(int up, int down, int left, int right) {
        EDirection nearestEnemyDir = findNearestEnemyWall(world.getAgents().get(mySide).getPosition().getX(), world.getAgents().get(mySide).getPosition().getY());
        //System.out.println(currentCycle + " " + up + " " + down + " " + right + " " + left);

        if (up >= maxFour(up, right, down, left) && nearestEnemyDir == EDirection.Up) {
            dir = EDirection.Up;
            return;
        } else if (down >= maxFour(up, right, down, left) && nearestEnemyDir == EDirection.Down) {
            dir = EDirection.Down;
            return;
        } else if (left >= maxFour(up, right, down, left) && nearestEnemyDir == EDirection.Left) {
            dir = EDirection.Left;
            return;
        } else if (right >= maxFour(up, right, down, left) && nearestEnemyDir == EDirection.Right) {
            dir = EDirection.Right;
            return;
        }

        if (up >= maxFour(up, right, down, left)) dir = EDirection.Up;
        else if (down >= maxFour(up, right, down, left)) dir = EDirection.Down;
        else if (left >= maxFour(up, right, down, left)) dir = EDirection.Left;
        else if (right >= maxFour(up, right, down, left)) dir = EDirection.Right;

    }

    private EDirection findNearestEnemyWall(int currentX, int currentY) {
        int nearestX = 1, nearestY = 1, minDistance = 10000000;

        for (int i = 0; i < world.getBoard().size(); i++) {
            for (int j = 0; j < world.getBoard().get(0).size(); j++) {
                int distance = (i - currentY) * (i - currentY) + (j - currentX) * (j - currentX);
                if (world.getBoard().get(i).get(j) == enemyCell && distance < minDistance) {
                    nearestY = i;
                    nearestX = j;
                    minDistance = distance;
                }
            }
        }

        if (nearestY > currentY && world.getBoard().get(currentY + 1).get(currentX) != ECell.AreaWall && dir != EDirection.Up)
            return EDirection.Down;

        if (nearestY < currentY && world.getBoard().get(currentY - 1).get(currentX) != ECell.AreaWall && dir != EDirection.Down)
            return EDirection.Up;

        if (nearestX > currentX && world.getBoard().get(currentY).get(currentX + 1) != ECell.AreaWall && dir != EDirection.Left)
            return EDirection.Right;

        if (nearestX < currentX && world.getBoard().get(currentY).get(currentX - 1) != ECell.AreaWall && dir != EDirection.Right)
            return EDirection.Left;


        // make sure
        if (nearestY >= currentY && world.getBoard().get(currentY + 1).get(currentX) != ECell.AreaWall && dir != EDirection.Up)
            return EDirection.Down;

        if (nearestY <= currentY && world.getBoard().get(currentY - 1).get(currentX) != ECell.AreaWall && dir != EDirection.Down)
            return EDirection.Up;

        if (nearestX >= currentX && world.getBoard().get(currentY).get(currentX + 1) != ECell.AreaWall && dir != EDirection.Left)
            return EDirection.Right;

        if (nearestX <= currentX && world.getBoard().get(currentY).get(currentX - 1) != ECell.AreaWall && dir != EDirection.Right)
            return EDirection.Left;

        return dir;
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

    private int maxFour(int a, int b, int c, int d) {
        if (a >= b && a >= c && a >= d) return a;
        if (b >= a && b >= c && b >= d) return b;
        if (c >= a && c >= b && c >= d) return c;
        return d;
    }

}