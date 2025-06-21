package Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Head {
    private Head next;
    private int width = 30, height = 30, x, y;
    private String last_go = "up";

    private BufferedImage image;
    private BufferedImage head_up, head_down, head_left, head_right,
            body_horizontal, body_vertical,
            body_bottomleft, body_bottomright, body_topleft, body_topright,
            tail_up, tail_down, tail_left, tail_right;

    public Head(int x, int y, Head tail) {
        this.x = x;
        this.y = y;
        next = tail;

        try {
            head_up = ImageIO.read(getClass().getResourceAsStream("/img/head_up.png"));
            head_down = ImageIO.read(getClass().getResourceAsStream("/img/head_down.png"));
            head_left = ImageIO.read(getClass().getResourceAsStream("/img/head_left.png"));
            head_right = ImageIO.read(getClass().getResourceAsStream("/img/head_right.png"));
            body_horizontal = ImageIO.read(getClass().getResourceAsStream("/img/body_horizontal.png"));
            body_vertical = ImageIO.read(getClass().getResourceAsStream("/img/body_vertical.png"));
            body_bottomleft = ImageIO.read(getClass().getResourceAsStream("/img/body_bottomleft.png"));
            body_bottomright = ImageIO.read(getClass().getResourceAsStream("/img/body_bottomright.png"));
            body_topleft = ImageIO.read(getClass().getResourceAsStream("/img/body_topleft.png"));
            body_topright = ImageIO.read(getClass().getResourceAsStream("/img/body_topright.png"));
            tail_up = ImageIO.read(getClass().getResourceAsStream("/img/tail_up.png"));
            tail_down = ImageIO.read(getClass().getResourceAsStream("/img/tail_down.png"));
            tail_left = ImageIO.read(getClass().getResourceAsStream("/img/tail_left.png"));
            tail_right = ImageIO.read(getClass().getResourceAsStream("/img/tail_right.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Head(int x, int y) {
        this.x = x;
        this.y = y;
        next = null;

        try {
            head_up = ImageIO.read(getClass().getResourceAsStream("/img/head_up.png"));
            head_down = ImageIO.read(getClass().getResourceAsStream("/img/head_down.png"));
            head_left = ImageIO.read(getClass().getResourceAsStream("/img/head_left.png"));
            head_right = ImageIO.read(getClass().getResourceAsStream("/img/head_right.png"));
            body_horizontal = ImageIO.read(getClass().getResourceAsStream("/img/body_horizontal.png"));
            body_vertical = ImageIO.read(getClass().getResourceAsStream("/img/body_vertical.png"));
            body_bottomleft = ImageIO.read(getClass().getResourceAsStream("/img/body_bottomleft.png"));
            body_bottomright = ImageIO.read(getClass().getResourceAsStream("/img/body_bottomright.png"));
            body_topleft = ImageIO.read(getClass().getResourceAsStream("/img/body_topleft.png"));
            body_topright = ImageIO.read(getClass().getResourceAsStream("/img/body_topright.png"));
            tail_up = ImageIO.read(getClass().getResourceAsStream("/img/tail_up.png"));
            tail_down = ImageIO.read(getClass().getResourceAsStream("/img/tail_down.png"));
            tail_left = ImageIO.read(getClass().getResourceAsStream("/img/tail_left.png"));
            tail_right = ImageIO.read(getClass().getResourceAsStream("/img/tail_right.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(image, x, y, width, height, null);

        if(next != null) {
            next.draw(g2);
        }
    }

    public void addNodo() {
        if(next != null) next.addNodo();
        else next = new Head(x+1, y+1);
    }

    public void updateNext(String move_father, Head segment, int x, int y, String last_go, BufferedImage image) {
        if(segment.getNext() != null){
            updateNext(last_go, segment.getNext(), segment.getX(), segment.getY(), segment.getLastGo(), segment.getImage());
        }
        segment.setLastGo(last_go);
        segment.setX(x);
        segment.setY(y);
        if(segment.getNext() != null) segment.setImage(image);
        else {
            if(move_father.equals("up")) segment.setImage(tail_down);
            else if(move_father.equals("down")) segment.setImage(tail_up);
            else if(move_father.equals("left")) segment.setImage(tail_right);
            else if(move_father.equals("right")) segment.setImage(tail_left);
        }
    }

    public String update(String go) {
        if(next != null) {
            switch(go) {
                case "up": {
                    if(last_go != "down" && last_go != "up") {
                        if(last_go.equals("right")) updateNext(go, next, x, y, last_go, body_topleft);
                        else if(last_go.equals("left")) updateNext(go, next, x, y, last_go, body_topright);
                    } else if (last_go.equals("up")) updateNext(go, next, x, y, last_go, body_vertical);
                    break;
                }
                case "down": {
                    if(last_go != "up" && last_go != "down") {
                        if(last_go.equals("right")) updateNext(go, next, x, y, last_go, body_bottomleft);
                        else if(last_go.equals("left")) updateNext(go, next, x, y, last_go, body_bottomright);
                    } else if (last_go.equals("down")) updateNext(go, next, x, y, last_go, body_vertical);
                    break;
                }
                case "left": {
                    if(last_go != "right" && last_go != "left") {
                        if(last_go.equals("up")) updateNext(go, next, x, y, last_go, body_bottomleft);
                        else if(last_go.equals("down")) updateNext(go, next, x, y, last_go, body_topleft);
                    } else if (last_go.equals("left")) updateNext(go, next, x, y, last_go, body_horizontal);
                    break;
                }
                case "right": {
                    if(last_go != "left" && last_go != "right") {
                        if(last_go.equals("up")) updateNext(go, next, x, y, last_go, body_bottomright);
                        else if(last_go.equals("down")) updateNext(go, next, x, y, last_go, body_topright);
                    } else if (last_go.equals("right")) updateNext(go, next, x, y, last_go, body_horizontal);
                    break;
                }
            }
        }
        switch(go) {
            case "up": {
                if(last_go != "down") {
                    image = head_up;
                    y -= 30;
                    last_go = go;
                } else update(last_go);
                break;
            }
            case "down": {
                if(last_go != "up") {
                    image = head_down;
                    y += 30;
                    last_go = go;
                } else update(last_go);
                break;
            }
            case "left": {
                if(last_go != "right") {
                    image = head_left;
                    x -= 30;
                    last_go = go;
                } else update(last_go);
                break;
            }
            case "right": {
                if(last_go != "left") {
                    image = head_right;
                    x += 30;
                    last_go = go;
                } else update(last_go);
                break;
            }
        }
        return last_go;
    }

    public BufferedImage getImage() {
        return image;
    }
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    public Head getNext() {
        return next;
    }
    public void setNext(Head next) {
        this.next = next;
    }

    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    public String getLastGo() {
        return last_go;
    }
    public void setLastGo(String last_go) {
        this.last_go = last_go;
    }
}
