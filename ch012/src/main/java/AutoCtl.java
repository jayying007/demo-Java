import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @author jayying
 * @date 2021/1/13
 */
public class AutoCtl {
    private Robot robot;

    public AutoCtl() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void action() {
        robot.mouseMove(400, 500);
        Color color = robot.getPixelColor(400, 500);
        System.out.printf("color(400,500)=RGB(%d,%d,%d)\n", color.getRed(), color.getGreen(), color.getBlue());
        robot.keyPress(KeyEvent.VK_J);
        robot.keyRelease(KeyEvent.VK_J);
        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_A);
        robot.keyPress(KeyEvent.VK_Y);
        robot.keyRelease(KeyEvent.VK_Y);
    }

    public static void main(String[] args) {
        new AutoCtl().action();
    }
}
