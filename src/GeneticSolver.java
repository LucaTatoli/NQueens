import java.util.*;
import java.util.stream.Collectors;

public class GeneticSolver {

    private Map<int[], Integer> parents, children;
    private int size;
    private final int POPULATION = 1000;
    private float mutationRate;
    private boolean doneFlag = false;
    private int hValue;

    public GeneticSolver(int size, float mutationRate) {
        parents = new HashMap<>();
        children = new HashMap<>();
        this.size = size;
        hValue = size*size;
        this.mutationRate = mutationRate;
        init();
    }

    private void init() {
        int[] state;
        Random r = new Random();
        int index;
        for(int i = 0; i < POPULATION; i++) {
            state = new int[this.size];
            for(int j = 0; j < this.size; j++) {
                index = Math.abs(r.nextInt()%this.size);
                state[j] = index;
            }
            parents.put(state, hFunction(state));
        }
    }

    private void mixParents() {
        List<int[]> sortedParents = parents.keySet().stream().sorted(Comparator.comparingInt(x -> parents.get(x))).collect(Collectors.toList());
        int[] child1, child2;
        Random r = new Random();

        for(int i = 0; i < POPULATION/2 * 2/5; i++) {
            child1 = new int[size];
            child2 = new int[size];
            for(int n = 0; n < size; n++) {
                if(n < size/3) {
                    child1[n] = sortedParents.get(i)[n];
                    child2[n] = sortedParents.get(i+1)[n];
                }
                else {
                    child1[n] = sortedParents.get(i+1)[n];
                    child2[n] = sortedParents.get(i)[n];
                }

            }
            if(r.nextFloat() < mutationRate)
                child1[Math.abs(r.nextInt()%size)] = Math.abs(r.nextInt()%size);
            if(r.nextFloat() < mutationRate)
                child2[Math.abs(r.nextInt()%size)] = Math.abs(r.nextInt()%size);
            children.put(child1, hFunction(child1));
            children.put(child2, hFunction(child2));
        }

        int p1, p2;

        for(int i = 0; i < POPULATION/2 * 3/5 ; i++) {
            child1 = new int[size];
            child2 = new int[size];
            p1 = Math.abs(r.nextInt()%POPULATION);
            p2 = Math.abs(r.nextInt()%POPULATION);
            while(p1 == p2)
                p2 = Math.abs(r.nextInt()%POPULATION);
            for(int n = 0; n < size; n++) {
                if(n < size/3) {
                    child1[n] = sortedParents.get(p1)[n];
                    child2[n] = sortedParents.get(p2)[n];
                }
                else {
                    child1[n] = sortedParents.get(p1)[n];
                    child2[n] = sortedParents.get(p2)[n];
                }
            }

            if(r.nextFloat() < mutationRate)
                child1[Math.abs(r.nextInt()%size)] = Math.abs(r.nextInt()%size);
            if(r.nextFloat() < mutationRate)
                child2[Math.abs(r.nextInt()%size)] = Math.abs(r.nextInt()%size);
            children.put(child1, hFunction(child1));
            children.put(child2, hFunction(child2));
        }
        parents = children;
        children = new HashMap<>();
    }

    private int hFunction(int[] state) {
        int value = 0;

        for(int i = 0; i < this.size; i++) {
            for(int j = 0; j < this.size; j++) {
                if(i != j) {
                    //System.out.println(state[i] + " --- " + state[j]);
                    if(state[i] == state[j])
                        value++;
                    if(state[i] == state[j] + j - i)
                        value ++;
                    if(state[i] == state[j] + i - j)
                        value ++;
                }
            }
        }
        if(value == 0) {
            doneFlag = true;
            hValue = 0;
        }
        return value;
    }

    public int[] getBestState() {
        int[] best = new int[this.size];
        int value = Integer.MAX_VALUE;
        for(int[] state : parents.keySet())
            if(parents.get(state) < value) {
                value = parents.get(state);
                best = state;
            }
//        System.out.println("Best state value: " + hFunction(best));
        if(value < hValue)
            hValue = value;
        return best;
    }

    public int getBestStateValue() {
        return hValue;
    }

    public void nextGeneration() {
        mixParents();
    }

    public boolean isResolved() {
        return doneFlag;
    }


}
