package ai;

import java.util.ArrayList;

public class BFS {
    private static int[][] world = {{-1, 0, -2, 0, 0}, {-2, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, -3}};

    // -1 : start
    // -2 : wall
    // -3 : end

    private static ArrayList<String> queue = new ArrayList<>();
    private static boolean[][] seen = new boolean[5][5];

    public static void main(String[] args) {
        new Thread(() -> bfs()).start();
    }

    private static void bfs() {
        int cnt = 0;
        String[] directions = {"U", "D", "R", "L" };
        String currentDir = "";
        queue.add("");
        while (!foundEnd(currentDir)) {
            currentDir = queue.get(0);
            queue.remove(0);

            printWorld();

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (String s : directions) {
                String put = currentDir + s;
                if (isValid(put)) {
                    queue.add(put);
                    cnt ++;
                }
            }
        }

        System.out.println("cnt = " + cnt);
        System.out.println("Route = " + currentDir);
    }

    private static boolean isValid(String put) {
        int iIndex = 0, jIndex = 0;
        for(int i = 0; i < world.length; ++i)
            for(int j = 0; j < world[0].length; ++j)
                if(world[i][j] == -1) {
                    iIndex = i;
                    jIndex = j;
                }
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
        if(iIndex < 0 || iIndex >= world.length ||
        jIndex < 0 || jIndex >= world[0].length ||
        world[iIndex][jIndex] == -2 ||
        seen[iIndex][jIndex])
            return false;

        seen[iIndex][jIndex] = true;
        return true;
    }

    private static boolean foundEnd(String currentDir) {
        int iIndex = 0, jIndex = 0;
        for(int i = 0; i < world.length; ++i)
            for(int j = 0; j < world[0].length; ++j)
                if(world[i][j] == -1) {
                    iIndex = i;
                    jIndex = j;
                }
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
        if(world[iIndex][jIndex] == -3)
            return true;

        return false;
    }


    private static void printWorld() {
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                System.out.print((seen[i][j] ? "1" : "0") + " ");
            }
            System.out.println();
        }
        System.out.println("---------------------------");
    }





}
