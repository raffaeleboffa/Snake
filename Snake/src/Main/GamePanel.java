package Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable {
    private Head head;
    private String go;
    private int velocity;
    private boolean start = false, restart = false;
    private BufferedImage logo;

    private Apple apple = new Apple((int)(Math.random() * 18)*30, (int)(Math.random() * 18)*30);

    private int points = 0;

    Thread gameThread;

    public GamePanel() {
        setBackground(Color.GREEN);

        try {
            logo = ImageIO.read(getClass().getResourceAsStream("/img/logo.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        head = new Head(30*10, 30*10, new Head(30*10, 30*11));
        go = "up";
        velocity = 180;
        points = 0;

        while(true) {
            go = head.update(go);

            if(head.getX() == apple.getX() && head.getY() == apple.getY()) {
                generateApple();
                points += 10;
                head.addNodo();
            }

            if(collision()) {
                velocity = 0;
                break;
            }

            repaint();

            try {
                Thread.sleep(velocity);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        restart = false;
        repaint();
    }

    public void generateApple() {
        int x = (int)(Math.random() * 18)*30;
        int y = (int)(Math.random() * 18)*30;

        Head segment = head;

        do {
            if(segment.getX() == x && segment.getY() == y) {
                generateApple();
                return;
            }
            segment = segment.getNext();
        }while(segment != null);

        apple = new Apple(x, y);
    }

    public boolean collision() {
        if(head.getX() < 0 || head.getY() < 0 || head.getX() > getWidth() || head.getY() > getHeight()) return true;
        else if(collisionSnake(head.getNext())) return true;
        return false;
    }

    public boolean collisionSnake(Head segment) {
        if(segment != null) {
            if (head.getX() == segment.getX() && head.getY() == segment.getY()) return true;
            else return collisionSnake(segment.getNext());
        }
        return false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        if(start) {
            Head t = head;
            while(t != null) {
                t.draw(g2);
                t = t.getNext();
            }

            apple.draw(g2);

            g2.setFont(new Font( "SansSerif", Font.PLAIN, 18 ));
            g2.setColor(Color.RED);
            g2.drawString("Punti: " + points, 20, 50);
        }

        if(velocity == 0) {
            g2.setColor(Color.BLACK);
            g2.setFont(new Font( "SansSerif", Font.PLAIN, 30 ));
            g2.drawString("Game Over", 30*7, 30*9);
            g2.setFont(new Font( "SansSerif", Font.PLAIN, 16 ));
            g2.drawString("Press ENTER to Restart", 30*7, 30*10);
        }

        if(!start) {
            g2.drawImage(logo, 150, 0, 300, 300, null);
            g2.setFont(new Font( "SansSerif", Font.PLAIN, 16 ));
            g2.drawString("Press ENTER to Start", 30*7, 30*10);
        }

        g2.dispose();
    }

    public boolean getRestart() {
        return restart;
    }
    public void setRestart(boolean restart) {
        this.restart = restart;
    }

    public boolean getStart() {
        return start;
    }
    public void setStart(boolean start) {
        this.start = start;
    }

    public String getGo() {
        return go;
    }
    public void setGo(String go) {
        if(start) this.go = go;
    }
}
