import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class QueenMouseListener implements MouseListener {

    private ArrayList<Integer> positions;
    private int blocksize;
    private int size;
    private Scacchiera scacchiera;

    public QueenMouseListener(Scacchiera scacchiera, int size, int blocksize, ArrayList<Integer> positions) {
        this.positions = positions;
        this.blocksize = blocksize;
        this.size = size;
        this.scacchiera = scacchiera;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(SwingUtilities.isRightMouseButton(e))
            positions.clear();
        else {
            int x = e.getX() / blocksize;
            int y = e.getY() / blocksize;
            positions.clear();

            for (int i = 0; i < size; i++) {
                positions.add(x + i * size);
                positions.add(i + y * size);
                if (x - y + i >= 0 && x - y + i < size)
                    positions.add(x - y + i + i * size);
                if (x + y - i >= 0 && x + y - i < size)
                    positions.add(x + y - i + i * size);
            }
        }
        scacchiera.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
