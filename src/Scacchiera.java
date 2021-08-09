import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Scacchiera extends JPanel {

    private int size;
    private int blocksize;
    private GeneticSolver solver;
    private ArrayList<Integer> highlightPosition;

    public Scacchiera(int size, int blocksize, float mutationRate) {
        this.size = size;
        this.blocksize = blocksize;
        setPreferredSize(new Dimension(size * blocksize + 1, size * blocksize + 1));
        solver = new GeneticSolver(size, mutationRate);
        highlightPosition = new ArrayList<>();
        addMouseListener(new QueenMouseListener(this, size, blocksize, highlightPosition));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setBackground(Color.black);
        g2d.clearRect(0, 0, size*blocksize, size*blocksize);

        //POSIZIONI EVIDENZIATE
        g2d.setColor(Color.red);
        highlightPosition.forEach(pos -> {
            g2d.fillRect(pos%size * blocksize, pos/size * blocksize, blocksize, blocksize);
        });

        g2d.setColor(Color.white);

        //GRIGLIA
        for(int i = 0; i < size; i++) {
            g2d.drawLine(          0, i*blocksize, size*blocksize,    i*blocksize);
            g2d.drawLine(i*blocksize,           0,    i*blocksize, size*blocksize);
        }

        //REGINE
        try {
            int[] state = solver.getBestState();
            for (int i = 0; i < size; i++)
                g2d.fillArc(state[i] * blocksize + blocksize / 4, i * blocksize + blocksize / 4, blocksize / 2, blocksize / 2, 0, 360);
        } catch (Exception e) {};

    }

    public void solve() {
        for(int i = 0; i < 10000; i++) {
            solver.nextGeneration();
            repaint();
            if(solver.isResolved()) {
                System.out.println("Generation: " + i);
                System.out.println("Best state value: " + solver.getBestStateValue());
                break;
            }
//            try {
//                TimeUnit.MILLISECONDS.sleep(10);
//            } catch (Exception e) {}
        }
    }
}
