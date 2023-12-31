
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

class Fire {

    private int x;
    private int y;

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

    public Fire(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class Oyun extends JPanel implements KeyListener, ActionListener {

    Timer timer = new Timer(5, this);

    private int time = 0;
    private int ammoo = 0;
    private BufferedImage image;
    private ArrayList<Fire> atesler = new ArrayList<Fire>();

    private int fireDirY = 5;

    private int topX = 0;
    private int topDirX = 2;

    private int uzayGemisiX = 0;
    private int dirUzayX = 20;
    
    public boolean kontrolEt()
    {
        for(Fire fire : atesler)
        {
            if(new Rectangle(fire.getX(), fire.getY(),10,20).intersects(new Rectangle(topX,0,20,20)))
            {
                return true;
            }
        }
        
        return false;
    }

    public Oyun() {
        try {
            image = ImageIO.read(new FileImageInputStream(new File("uzaygemisi.png")));
        } catch (IOException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        }

        setBackground(Color.BLACK);

        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        time += 5;

        g.setColor(Color.RED);
        g.fillOval(topX, 0, 20, 20);

        g.drawImage(image, uzayGemisiX, 490, image.getWidth() / 10, image.getHeight() / 10, this);
        
        for(Fire fire : atesler)
        {
            if(fire.getY() < 0)
            {
                atesler.remove(fire); 
            }
        }
        
        g.setColor(Color.BLUE);
        
        for(Fire fire : atesler)
        {
            g.fillRect(fire.getX(), fire.getY(), 10 , 20);
        }
        
        if(kontrolEt())
        {
            timer.stop();
            String message = "You won...\n"+
                             "Used Amount Of Ammo: " + ammoo +
                             "\nTime to complete: " + time / 1000.0 + " ms";
            
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }
    }

    @Override
    public void repaint() {
        super.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();

        if (c == KeyEvent.VK_LEFT) {
            if (uzayGemisiX <= 0) {
                uzayGemisiX = 0;
            } else {
                uzayGemisiX -= dirUzayX;
            }
        } 
        else if (c == KeyEvent.VK_RIGHT) {
            if (uzayGemisiX >= 720) {
                uzayGemisiX = 720;
            } else {
                uzayGemisiX += dirUzayX;
            }
        }
        else if(c == KeyEvent.VK_CONTROL)
        {
            atesler.add(new Fire(uzayGemisiX + 20,490));
            ammoo++;    
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        for(Fire fire : atesler)
        {
            fire.setY(fire.getY() - fireDirY);
            
        }
        
        topX += topDirX;

        if (topX >= 720) {
            topDirX = -topDirX;
        }
        if (topX <= 0) {
            topDirX = -topDirX;
        }

        repaint();
    }

}
