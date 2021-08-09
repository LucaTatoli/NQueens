import javax.swing.*;

public class NQueens extends JFrame {

    private Scacchiera scacchiera;

    public NQueens(int size, int blocksize, float mutationRate) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        scacchiera = new Scacchiera(size, blocksize, mutationRate);
        add(scacchiera);
        pack();
        setResizable(false);
        setVisible(true);
    }

    public void start() {
        scacchiera.solve();
    }

    public static void main(String[] args) {
        NQueens nQueens = new NQueens(20, 30, 0.5f);
        nQueens.start();
    }
}
