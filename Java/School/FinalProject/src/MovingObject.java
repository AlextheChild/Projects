import java.awt.*;
import java.io.*;
import javax.imageio.*;

public class MovingObject extends GameObject {
    Image defaultIcon, upIcon, downIcon, leftIcon, rightIcon, leftUpIcon, leftDownIcon, rightUpIcon,
            rightDownIcon;

    int direction;

    public MovingObject(int x, int y) {
        super(x, y);
        try {
            defaultIcon = ImageIO.read(new File("src/BlockIcons/MovingObject/defaultMoving.png")).getScaledInstance(30,
                    30, Image.SCALE_DEFAULT);
            leftIcon = ImageIO.read(new File("src/BlockIcons/MovingObject/leftMoving.png")).getScaledInstance(30, 30,
                    Image.SCALE_DEFAULT);
            rightIcon = ImageIO.read(new File("src/BlockIcons/MovingObject/rightMoving.png")).getScaledInstance(30, 30,
                    Image.SCALE_DEFAULT);
            upIcon = ImageIO.read(new File("src/BlockIcons/MovingObject/upMoving.png")).getScaledInstance(30, 30,
                    Image.SCALE_DEFAULT);
            downIcon = ImageIO.read(new File("src/BlockIcons/MovingObject/downMoving.png")).getScaledInstance(30, 30,
                    Image.SCALE_DEFAULT);
            leftUpIcon = ImageIO.read(new File("src/BlockIcons/MovingObject/leftUpMoving.png")).getScaledInstance(30,
                    30, Image.SCALE_DEFAULT);
            leftUpIcon = ImageIO.read(new File("src/BlockIcons/MovingObject/leftUpMoving.png")).getScaledInstance(30,
                    30, Image.SCALE_DEFAULT);
            leftDownIcon = ImageIO.read(new File("src/BlockIcons/MovingObject/leftDownMoving.png")).getScaledInstance(
                    30, 30, Image.SCALE_DEFAULT);
            rightUpIcon = ImageIO.read(new File("src/BlockIcons/MovingObject/rightUpMoving.png")).getScaledInstance(30,
                    30, Image.SCALE_DEFAULT);
            rightDownIcon = ImageIO.read(new File("src/BlockIcons/MovingObject/rightDownMoving.png")).getScaledInstance(
                    30, 30, Image.SCALE_DEFAULT);
        } catch (IOException e) {
        }

        // starting icon
        icon = defaultIcon;
        isPlayer = false;
    }

    public void getDirection() {
        Player player = Main.mainFrame.mainPanel.player;

        // left
        if (player.realX < realX && player.realY == realY) {
            icon = leftIcon;
            direction = 4;
        }
        // right
        else if (player.realX > realX && player.realY == realY) {
            icon = rightIcon;
            direction = 6;
        }
        // up
        else if (player.realY < realY && player.realX == realX) {
            icon = upIcon;
            direction = 8;
        }
        // down
        else if (player.realY > realY && player.realX == realX) {
            icon = downIcon;
            direction = 2;
        }
        // left up
        else if (player.realX < realX && player.realY < realY) {
            icon = leftUpIcon;
            direction = 7;
        }
        // left down
        else if (player.realX < realX && player.realY > realY) {
            icon = leftDownIcon;
            direction = 1;
        }
        // right up
        else if (player.realX > realX && player.realY < realY) {
            icon = rightUpIcon;
            direction = 9;
        }
        // right down
        else if (player.realX > realX && player.realY > realY) {
            icon = rightDownIcon;
            direction = 3;
        }
    }

    @Override
    public String toString() {
        return "2";
    }
}