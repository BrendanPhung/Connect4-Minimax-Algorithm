import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

class Mouse implements MouseListener {

    public void mouseClicked(MouseEvent event) {
        int x = event.getX();
        int y = event.getY();
        System.out.println("X: " + x + "\nY: " + y);

        if (x < 100 || x > 800 || y > 700) {
            System.out.println("Click within the board");
        }

        if (x > 100 && x < 800 && y < 800) {
            for (int columnStart = 100; columnStart < 800; columnStart += 100) {
                int columnFinish = columnStart + 100;
                if (x > columnStart && x < columnFinish) {
                    System.out.println("row " + (columnStart / 100));
                    int column = (columnStart / 100);
                }
            }
        }
    }

    public void mouseReleased(MouseEvent event) {
    }

    public void mouseEntered(MouseEvent event) {
    }

    public void mouseExited(MouseEvent event) {
    }

    public void mousePressed(MouseEvent event) {
    }
}