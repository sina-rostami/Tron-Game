package ai;

import java.util.ArrayList;

public class BFS {
    private static int[][] world = {{0, 0, -2, 0, 0}, {-2, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, -1}};


    public static void main(String[] args) {
        new Thread(() -> NearestEnemyWall(0, 0)).start();
    }


    private static int[] NearestEnemyWall(int currentX, int currentY) {

        ArrayList<int[]> queue = new ArrayList<>();
        boolean[][] seen = new boolean[5][5];

        queue.add(new int[]{currentX, currentY});


        while (!queue.isEmpty()) {
            int[] current = queue.get(0);
            seen[current[0]][current[1]] = true;
            if (world[current[1]][current[0]] == -1) {
                return queue.get(0);
            }

            printWorld();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            queue.remove(0);

            if (current[1] > 0 && !seen[current[0]][current[1] - 1] && world[current[1] - 1][current[0]] != -2) {
                queue.add(new int[]{current[0], current[1] - 1});
                seen[current[0]][current[1] - 1] = true;
            }
            if (current[0] > 0 && !seen[current[0] - 1][current[1]]&& world[current[1]][current[0] - 1] != -2) {
                queue.add(new int[]{current[0] - 1, current[1]});
                seen[current[0] - 1][current[1]] = true;
            }
            if (current[1] < 4 && !seen[current[0]][current[1] + 1]&& world[current[1] + 1][current[0]] != -2) {
                queue.add(new int[]{current[0], current[1] + 1});
                seen[current[0]][current[1] + 1] = true;
            }
            if (current[0] < 4 && !seen[current[0] + 1][current[1]]&& world[current[1]][current[0] + 1] != -2) {
                queue.add(new int[]{current[0] + 1, current[1]});
                seen[current[0] + 1][current[1]] = true;
            }
        }
        return null;
    }


    private static void printWorld() {
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                System.out.print(world[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("---------------------------");
    }

}
