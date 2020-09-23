package ai;
import ks.models.EDirection;
import ks.models.World;
import ks.models.ECell;
import ks.models.Agent;

import java.util.ArrayList;

public class BFSinWorld {
    private World world;
    private ArrayList<String> queue;
    private Agent mySide;
    private ECell enemyCell, myCell;
    private int wallBreakerDuration;
    private boolean[][] seen;
    public int cnt = 0;

    public BFSinWorld(World world, Agent mySide, ECell enemyCell, ECell myCell) {
        this.world = world;
        this.mySide = mySide;
        this.enemyCell = enemyCell;
        this.myCell = myCell;

        queue = new ArrayList<>();
        seen = new boolean[world.getBoard().size()][world.getBoard().get(0).size()];
        wallBreakerDuration = world.getConstants().getWallBreakerDuration();
    }

    public EDirection doBfs() {
        String[] directions = {"U", "D", "R", "L"};
        String currentDir = "";
        queue.add("");
        while (!queue.isEmpty()) {
            currentDir = queue.get(0);
            queue.remove(0);
            for (String s : directions) {
                String put = currentDir + s;
                if (isValid(put)) {
                    queue.add(put);
                    cnt++;
                }
            }
            if(foundEnd(currentDir))
                break;
        }

        return stringToDir(currentDir);
    }

    private EDirection stringToDir(String currentDir) {
        switch (currentDir.charAt(0)) {
            case 'U' :
                return EDirection.Up;
            case 'D' :
                return EDirection.Down;
            case 'L' :
                return EDirection.Left;
            case 'R' :
                return EDirection.Right;
            default:
                break;
        }
        return null;
    }

    private boolean isValid(String put) {
        int iIndex = mySide.getPosition().getY(), jIndex = mySide.getPosition().getX();
        for(int i = 0; i < put.length(); ++i) {
            switch (put.charAt(i)) {
                case 'U' :
                    iIndex--;
                    break;
                case 'D' :
                    iIndex++;
                    break;
                case 'L' :
                    jIndex--;
                    break;
                case 'R' :
                    jIndex++;
                    break;
                default:
                    break;
            }

        }
        if(iIndex < 1 || iIndex >= world.getBoard().size() - 1 ||
                jIndex < 1 || jIndex >= world.getBoard().get(0).size() - 1 ||
                world.getBoard().get(iIndex).get(jIndex) == ECell.AreaWall ||
                seen[iIndex][jIndex])
            return false;

        seen[iIndex][jIndex] = true;
        return true;
    }

    private boolean foundEnd(String currentDir) {
        int iIndex = mySide.getPosition().getY(), jIndex = mySide.getPosition().getX();
        for(int i = 0; i < currentDir.length(); ++i) {
            switch (currentDir.charAt(i)) {
                case 'U' :
                    iIndex--;
                    break;
                case 'D' :
                    iIndex++;
                    break;
                case 'L' :
                    jIndex--;
                    break;
                case 'R' :
                    jIndex++;
                    break;
                default:
                    break;
            }
        }


        if(world.getBoard().get(iIndex).get(jIndex) != enemyCell)
            return false;
        if(currentDir.length() < 6)
            return false;

        return isSouitableRoute(currentDir);

    }

    private boolean isSouitableRoute(String currentDir) {
        int iIndex = mySide.getPosition().getY(), jIndex = mySide.getPosition().getX();

        int wallBreakerOnTime = mySide.getWallBreakerCooldown();
        boolean wallBreakerOn = wallBreakerOnTime > 6;

        for(int i = 0; i < currentDir.length(); ++i) {
            if(i >= 12) break;
            switch (currentDir.charAt(i)) {
                case 'U' :
                    iIndex--;
                    break;
                case 'D' :
                    iIndex++;
                    break;
                case 'L' :
                    jIndex--;
                    break;
                case 'R' :
                    jIndex++;
                    break;
                default:
                    break;
            }

            ECell current = world.getBoard().get(iIndex).get(jIndex);
            if(!wallBreakerOn  &&
                    (current == myCell ||
                    current == enemyCell)) {
                wallBreakerOn = true;
                wallBreakerOnTime = 12;
            }
            if(wallBreakerOn) wallBreakerOnTime--;
            if(wallBreakerOnTime < wallBreakerDuration) {
                if(current != ECell.Empty)
                    return false;
            }
        }

        return true;
    }
}

