package Main;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main implements KeyListener {
    static JFrame frame = new JFrame("Snake");
    static GamePanel gp = new GamePanel();

    public static void main(String[] args) {
        frame.setResizable(false);
        frame.setBounds(200, 50, 30*20, 30*20);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(gp);

        Main mainInstance = new Main();
        frame.addKeyListener(mainInstance);

        frame.setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_W) gp.setGo("up");
        else if (keyCode == KeyEvent.VK_S) gp.setGo("down");
        else if (keyCode == KeyEvent.VK_A) gp.setGo("left");
        else if (keyCode == KeyEvent.VK_D) gp.setGo("right");
        else if (keyCode == KeyEvent.VK_ENTER && !gp.getRestart()) {
            gp.startGameThread();
            gp.setStart(true);
            gp.setRestart(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}