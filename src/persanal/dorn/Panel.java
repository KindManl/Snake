package persanal.dorn;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Panel extends JPanel implements ActionListener {

    static final int Width = 900;
    static final int Height = 600;
    static final int Size = 30;
    static final int NumberOfUnits = Width * Height / Size;
    static final int Delay = 150;
    int foodX;
    int foodY;
    int foodCount = 0;
    char Route = 'R';
    char TrueRoute = 'R';
    Snake snake = new Snake(4, NumberOfUnits, Route, Size);
    boolean game = false;
    Timer timer;
    Random random;


    Image head;
    Image bodyG;
    Image bodyV;
    Image turn0;
    Image turn1;
    Image turn2;
    Image turn3;
    Image apple;
    Image backGround;


    Panel() throws IOException {
        random = new Random();
        this.setPreferredSize(new Dimension(Width, Height));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new KeyBoard());
        start();
    }

    public void start() throws IOException {
        newFood();
        game = true;
        timer = new Timer(Delay, this);
        timer.start();
        apple = ImageIO.read(new FileImageInputStream(new File("Resources/apple.png")));
        bodyG = ImageIO.read(new FileImageInputStream(new File("Resources/bodyRight.png")));
        bodyV = ImageIO.read(new FileImageInputStream(new File("Resources/bodyUp.png")));
        turn0 = ImageIO.read(new FileImageInputStream(new File("Resources/turn0.gif")));
        turn1 = ImageIO.read(new FileImageInputStream(new File("Resources/turn1.gif")));
        turn2 = ImageIO.read(new FileImageInputStream(new File("Resources/turn2.gif")));
        turn3 = ImageIO.read(new FileImageInputStream(new File("Resources/turn3.gif")));
        backGround = ImageIO.read(new FileImageInputStream(new File("Resources/BackGround.png")));

    }

    public void paint(Graphics graphics) {
        super.paintComponent(graphics);
        draw(graphics);
    }

    public void draw(Graphics graphics) {
        if (game) {
            //background image
            graphics.drawImage(backGround, 0, 0, null);

            //food
            graphics.drawImage(apple, foodX, foodY, null);

            //body
            snake.checkFalseTurn();
            for (int i = 1; i < snake.getLength(); ++i) {
                if (!snake.getTurn(i)) {//straight body
                    switch (snake.getDirection(i)) {
                        case 'R', 'L' -> graphics.drawImage(bodyG, snake.getCoordsX(i), snake.getCoordsY(i), null);
                        case 'U', 'D' -> graphics.drawImage(bodyV, snake.getCoordsX(i), snake.getCoordsY(i), null);
                    }
                } else //turn body
                    switch (snake.getTurnDirection(i)) {
                        case 0 -> graphics.drawImage(turn0, snake.getCoordsX(i), snake.getCoordsY(i), null);
                        case 1 -> graphics.drawImage(turn1, snake.getCoordsX(i), snake.getCoordsY(i), null);
                        case 2 -> graphics.drawImage(turn2, snake.getCoordsX(i), snake.getCoordsY(i), null);
                        case 3 -> graphics.drawImage(turn3, snake.getCoordsX(i), snake.getCoordsY(i), null);
                    }
            }

            //head
            graphics.setColor(Color.green);
            graphics.drawImage(head, snake.getCoordsX(0), snake.getCoordsY(0), null);

            //backGround lines
            graphics.setColor(Color.darkGray);
            for (int i = 0; i < Width / Size; ++i)
                graphics.drawLine(Size * i, 0, Size * i, Height);
            for (int i = 0; i < Height / Size; ++i)
                graphics.drawLine(0, i * Size, Width, i * Size);


            //score
            graphics.setColor(Color.red);
            graphics.setFont(new Font("Ink Free", Font.BOLD, 50));
            FontMetrics metrics = getFontMetrics(graphics.getFont());
            graphics.drawString("Score: " + foodCount, (Width - metrics.stringWidth("Score" + foodCount)) / 2,  60);
        }else
            gameOver(graphics);
    }

    public void move() throws IOException {
        for (int i = snake.getLength(); i > 0; --i) {
            snake.setCoordsX(i, snake.getCoordsX(i - 1));
            snake.setCoordsY(i, snake.getCoordsY(i - 1));
            snake.setDirection(i, snake.getDirection(i - 1));
            snake.setTurn(i, snake.getTurn(i - 1));
        }
        snake.setTurn(0, false);


        switch (Route) {
            case 'R' -> {
                snake.setCoordsX(0, snake.getCoordsX(0) + Size);
                snake.setDirection(0, 'R');
                head = ImageIO.read(new FileImageInputStream(new File("Resources/snakeRight.png")));
            }
            case 'L' -> {
                snake.setCoordsX(0, snake.getCoordsX(0) - Size);
                snake.setDirection(0, 'L');
                head = ImageIO.read(new FileImageInputStream(new File("Resources/snakeLeft.png")));
            }
            case 'U' -> {
                snake.setCoordsY(0, snake.getCoordsY(0) - Size);
                snake.setDirection(0, 'U');
                head = ImageIO.read(new FileImageInputStream(new File("Resources/snakeUp.png")));
            }
            case 'D' -> {
                snake.setCoordsY(0, snake.getCoordsY(0) + Size);
                snake.setDirection(0, 'D');
                head = ImageIO.read(new FileImageInputStream(new File("Resources/snakeDown.png")));
            }
        }
        TrueRoute = Route;



    }

    public void checkFood (){
        if ((snake.getCoordsX(0) == foodX) && (snake.getCoordsY(0) == foodY)) {
            newFood();
            snake.addLength();
            foodCount++;
        }
    }

    public void newFood(){
        foodX = random.nextInt((int) (Width / Size)) * Size;
        foodY = random.nextInt((int) (Height / Size)) * Size;
        for (int i = 0; i < snake.getLength(); ++i)//ask
            if ((foodX == snake.getCoordsX(i)) && (foodY == snake.getCoordsY(i)))
                newFood();
    }

    public void checkCollisions(){
        if (snake.getCoordsX(0) >= Width)
            snake.setCoordsX(0,0);
        if (snake.getCoordsY(0) >= Height)
            snake.setCoordsY(0,0);
        if (snake.getCoordsX(0) < 0)
            snake.setCoordsX(0,  Width - Size);
        if (snake.getCoordsY(0) < 0)
            snake.setCoordsY(0, Height - Size);
        for (int i = 1; i < snake.getLength(); ++i)
            if ((snake.getCoordsX(0) == snake.getCoordsX(i)) && (snake.getCoordsY(0) == snake.getCoordsY(i))) {
                game = false;
                gameOver(getGraphics());
            }
    }

    public void gameOver(Graphics graphics){

        graphics.setColor(Color.red);
        graphics.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(graphics.getFont());
        graphics.drawString("Game Over", (Width - metrics.stringWidth("Game Over")) / 2, Height / 2);
        graphics.drawString("Score: " + foodCount, (Width - metrics.stringWidth("Score" + foodCount)) / 2,  60);
    }

    public class KeyBoard extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent keyEvent){
            switch (keyEvent.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if ((Route != 'L') && (TrueRoute != 'R')) {
                        Route = 'L';
                        snake.setTurn(0, true);
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if ((Route != 'R') && (TrueRoute != 'L')) {
                        Route = 'R';
                        snake.setTurn(0, true);
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if ((Route != 'D') && (TrueRoute != 'U')) {
                        Route = 'D';
                        snake.setTurn(0, true);
                    }
                    break;
                case KeyEvent.VK_UP:
                    if ((Route != 'U') && (TrueRoute != 'D')) {
                        Route = 'U';
                        snake.setTurn(0, true);
                    }
                    break;
            }

        }
    }
    @Override

    public void actionPerformed(ActionEvent event){
        if (game) {
            //checkCollisions();
            try {
                move();
            } catch (IOException e) {
                e.printStackTrace();
            }
            checkCollisions();
            checkFood();
        }
        repaint();
    }
}
