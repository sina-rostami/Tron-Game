package ai;

import ks.models.ECell;

import java.util.ArrayList;

public class AStar {
    private ArrayList<ECell> openSet;
    private ArrayList<ECell> closedSet;
    private ECell start;
    private ECell end;

    public AStar(ECell start, ECell end) {
        this.start = start;
        this.end = end;
        openSet = new ArrayList<>();
        closedSet = new ArrayList<>();
        openSet.add(start);
    }

    public void search() {

        while (!openSet.isEmpty()) {
            int winnerIndex = 0;
            for(int i = 0; i < openSet.size(); ++i) {
                if(openSet.get(i).f() < openSet.get(winnerIndex).f())
                    winnerIndex = i;
            }

            ECell current = openSet.get(winnerIndex);

            if(current == end) {
                // done !
            }

            openSet.remove(current);
            closedSet.add(current);

            // search in neighbors ....


        }
    }

}
