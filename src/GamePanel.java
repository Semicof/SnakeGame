import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int screenW=600;
    static final int screenH=600;
    static final int unitSize=25;
    static final int gameUnits=(screenW*screenH)/unitSize;
    static final int delay=75;
    final int x[]=new int[gameUnits];
    final int y[]=new int[gameUnits];
    int bodyParts=6;
    int appleEaten=0;
    int aX,aY;
    char direction='R';
    boolean isRunning=false;
    Timer timer;
    Random random;

    GamePanel()
    {
        random=new Random();
        this.setPreferredSize(new Dimension(screenW,screenH));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame()
    {
        newApple();
        isRunning=true;
        timer=new Timer(delay,this);
        timer.start();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g)
    {
        if(isRunning) {
            g.setColor(Color.RED);
            g.fillOval(aX, aY, unitSize, unitSize);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], unitSize, unitSize);
                } else {
                    g.setColor(Color.pink);
                    g.fillRect(x[i], y[i], unitSize, unitSize);
                }
            }
            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free",Font.BOLD,35));
            g.drawString("Score: "+appleEaten,screenW/2-3*unitSize,unitSize);
        }
        else gameOver(g);
    }

    public void newApple()
    {
        aX=random.nextInt((int)screenW/unitSize)*unitSize;
        aY=random.nextInt((int)screenH/unitSize)*unitSize;
    }

    public void move()
    {
        for(int i=bodyParts;i>0;i--)
        {
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch (direction)
        {
            case 'U':
                y[0]=y[0]-unitSize;
                break;
            case 'D':
                y[0]=y[0]+unitSize;
                break;
            case 'R':
                x[0]=x[0]+unitSize;
                break;
            case 'L':
                x[0]=x[0]-unitSize;
                break;
        }
    }

    public void checkApple()
    {
        if(x[0]==aX&&y[0]==aY)
        {
            bodyParts++;
            appleEaten++;
            newApple();
        }
    }

    public void checkCollision()
    {

        for(int i=bodyParts;i>0;i--)
        {
            if(x[0]==x[i]&&y[0]==y[i])
            {
                isRunning=false;
            }
        }

        if(x[0]<0||x[0]>screenW||y[0]<0||y[0]>screenH)
        {
            isRunning=false;
        }

        if(!isRunning)
        {
            timer.stop();
        }

    }

    public void gameOver(Graphics g)
    {
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        g.drawString("Game Over",screenW/4-unitSize,screenH/2-2*unitSize);
        g.setFont(new Font("Ink Free",Font.BOLD,45));
        g.drawString("Score: "+appleEaten,screenW/4+3*unitSize,screenH/2+unitSize);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(isRunning)
        {
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        public void keyPressed(KeyEvent k)
        {
            switch (k.getKeyCode())
            {
                case KeyEvent.VK_LEFT:
                    if(direction!='R')
                    {
                        direction='L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction!='L')
                    {
                        direction='R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction!='D')
                    {
                        direction='U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction!='U')
                    {
                        direction='D';
                    }
                    break;
            }
        }

    }
}
