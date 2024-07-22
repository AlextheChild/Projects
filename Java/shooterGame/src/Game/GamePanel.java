package Game;

import Main.*;
import Game.GameObjects.*;
import Game.GameObjects.Enemies.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    public Player player;

    ArrayList<Bullet> bullets;
    boolean reloaded = true;

    ArrayList<Bomb> bombs;
    long timeLastDropped;

    ArrayList<Particle> particles;

    ArrayList<Enemy> enemies;
    public ArrayList<EnemyBullet> enemyBullets;
    public int killCount = 0;

    public BlackHole blackHole;

    public int bulletDamage = 1;
    public int numBombs = 0;
    public boolean blackHoleShield = false;
    public String[] upgradeNames = { "laserScope", "bulletBounce", "bulletPierce", "accurateBullets", "bulletSpread",
            "autoFire", "laserDamage" };
    public HashMap<String, Boolean> upgrades = new HashMap<String, Boolean>();

    Timer GameTimer;
    int tick = 0;
    public long time = System.currentTimeMillis();
    public long millisecondsPassed = 0;
    int pauses = 0;

    public boolean devTools = false;

    public GamePanel() {
        player = new Player(0, 300);

        bullets = new ArrayList<Bullet>();

        bombs = new ArrayList<Bomb>();

        particles = new ArrayList<Particle>();

        enemies = new ArrayList<Enemy>();
        enemyBullets = new ArrayList<EnemyBullet>();

        blackHole = new BlackHole();

        for (String name : upgradeNames) {
            upgrades.put(name, false);
        }

        this.setBackground(Color.WHITE);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocus();

        GameTimer = new Timer(1, this);
        GameTimer.setActionCommand("gameTimerFired");
        GameTimer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Particle p : particles) {
            p.display(g);
        }

        for (Bullet b : bullets) {
            b.display(g);
        }

        if (upgrades.get("laserScope")) {
            drawLaser(g);
        }

        for (Bomb b : bombs) {
            b.display(g);
        }

        for (Enemy e : enemies) {
            e.display(g);
        }

        for (EnemyBullet eB : enemyBullets) {
            eB.display(g);
        }

        player.display(g);

        blackHole.display(g);
    }

    public void drawLaser(Graphics g) {
        double x = player.x;
        double y = player.y;
        double changeX = Math.cos(Math.toRadians(player.angle));
        double changeY = Math.sin(Math.toRadians(player.angle));

        boolean pointFound = false;
        while (!pointFound) {
            x += changeX;
            y += changeY;

            for (Enemy e : enemies) {
                if (Math.pow(x - e.x, 2) + Math.pow(y - e.y, 2) <= 289) {
                    pointFound = true;
                    if (upgrades.get("laserDamage")) {
                        e.health -= 0.05;
                    }
                }
            }

            if (x < 0 || x > 900 || y < 0 || y > 600) {
                pointFound = true;
            }
        }

        if (!upgrades.get("laserDamage")) {
            g.setColor(new Color(255, 100, 100));
            g.drawLine((int) player.x, (int) player.y, (int) x, (int) y);
            g.fillOval((int) x - 5, (int) y - 5, 10, 10);
        } else {
            g.setColor(new Color(255, 0, 0));
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(5));
            g.drawLine((int) player.x, (int) player.y, (int) x, (int) y);

            g.setColor(new Color(255, 100, 100));
            g2.setStroke(new BasicStroke(3));
            g.drawLine((int) player.x, (int) player.y, (int) x, (int) y);

            g.setColor(new Color(255, 255, 255));
            g2.setStroke(new BasicStroke(1));
            g.drawLine((int) player.x, (int) player.y, (int) x, (int) y);

            g.setColor(new Color(255, 0, 0));
            g.fillOval((int) x - 7, (int) y - 7, 14, 14);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tick++;

        millisecondsPassed = System.currentTimeMillis() - time;
        time = System.currentTimeMillis();

        if (e.getActionCommand().equals("gameTimerFired")) {
            for (int i = 0; i < millisecondsPassed; i++) {
                updatePlayer();
                updateBullets();
                updateBombs();
                updateParticles();
                updateEnemies();
                updateEnemyBullets();
                blackHole.move();
            }

            if (upgrades.get("autoFire")) {
                if (tick % 50 == 0) {
                    shootBullet();
                }
            }

            if (tick % 300 == 0) {
                spawnRandomEnemy();
            }

            repaint();
        }
    }

    public void updatePlayer() {
        player.move();

        if (player.x < 0) {
            player.x = 0;
            player.angle = 180 - player.angle;
        }
        if (player.x > 900) {
            player.x = 900;
            player.angle = 180 - player.angle;
        }
        if (player.y < 0) {
            player.y = 0;
            player.angle = 360 - player.angle;
        }
        if (player.y > 600) {
            player.y = 600;
            player.angle = 360 - player.angle;
        }

        for (Enemy e : enemies) {
            if (player.isTouching(e)) {
                if (player.money > 0) {
                    player.money--;
                }
            }
        }
    }

    public void updateBullets() {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            b.move();

            // out of bounds
            if (!upgrades.get("bulletBounce")) {
                if (b.x < 0 || b.x > 900 || b.y < 0 || b.y > 600) {
                    bullets.remove(b);
                    i--;
                }
            } else {
                if (b.x < 0 || b.x > 900) {
                    b.angle = 180 - b.angle;
                    b.bounces--;
                }

                if (b.y < 0 || b.y > 600) {
                    b.angle = 360 - b.angle;
                    b.bounces--;
                }

                if (b.bounces == 0) {
                    bullets.remove(b);
                    i--;
                }
            }
        }
    }

    public void updateBombs() {
        for (int i = 0; i < bombs.size(); i++) {
            Bomb b = bombs.get(i);

            if (System.currentTimeMillis() - b.milliseconds >= 1000) {
                b.exploding = true;
            }

            if (b.exploding) {
                for (int q = 0; q < enemies.size(); q++) {
                    Enemy e = enemies.get(q);
                    if (b.isTouching(e)) {
                        enemies.remove(e);
                    }
                }
            }

            if (System.currentTimeMillis() - b.milliseconds >= 1250) {
                bombs.remove(b);
                i--;
            }
        }
    }

    public void updateParticles() {
        if (Math.random() * 100 > 90) {
            for (int i = 0; i < (int) (5 * Math.random()); i++) {
                particles.add(new Particle(player.x, player.y, player.angle));
            }
        }

        for (Particle p : particles) {
            p.move();
        }

        if (particles.size() > 100) {
            particles.remove((int) (Math.random() * 50));
        }
    }

    public void updateEnemies() {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            e.move();

            // out of bounds
            if (e.x < 0 || e.x > 900 || e.y < 0 || e.y > 600) {
                enemies.remove(e);
                i--;
            }

            if (e.isTouching(blackHole)) {
                blackHole.mass += 1;
                blackHole.hitboxRadius += 1;
                enemies.remove(e);
                i--;
            }

            // losing health
            for (int q = 0; q < bullets.size(); q++) {
                Bullet b = bullets.get(q);
                if (b.isTouching(e)) {
                    e.health -= bulletDamage;
                    if (!upgrades.get("bulletPierce")) {
                        bullets.remove(b);
                        q--;
                    }
                }
            }

            // dying
            if (e.health <= 0) {
                enemies.remove(e);
                if (e.getClass().getName().equals("Game.GameObjects.Enemies.SquareEnemy")) {
                    player.money += 2;
                } else if (e.getClass().getName().equals("Game.GameObjects.Enemies.PentagonEnemy")) {
                    player.money += 3;
                } else if (e.getClass().getName().equals("Game.GameObjects.Enemies.HexagonEnemy")) {
                    player.money += 5;
                } else if (e.getClass().getName().equals("Game.GameObjects.Enemies.TriangleEnemy")) {
                    player.money += 8;
                }
                killCount++;
                Main.inventoryFrame.inventoryInfoPanel.killCountLabel
                        .setText("kill count: " + Main.gameFrame.gamePanel.killCount);
                i--;
            }
        }
    }

    public void updateEnemyBullets() {
        for (EnemyBullet eB : enemyBullets) {
            eB.move();
        }
    }

    public void spawnRandomEnemy() {
        int randomEnemy = (int) (100 * Math.random());

        if (randomEnemy <= 60) {
            enemies.add(new SquareEnemy());
        } else if (randomEnemy > 60 && randomEnemy <= 80) {
            enemies.add(new PentagonEnemy());
        } else if (randomEnemy > 80 && randomEnemy <= 87) {
            enemies.add(new HexagonEnemy());
        } else if (randomEnemy > 87 && randomEnemy <= 90) {
            enemies.add(new TriangleEnemy());
        }
    }

    @Override
    public void keyPressed(KeyEvent kE) {
        if (kE.getKeyCode() == KeyEvent.VK_P) {
            System.exit(0);
        }
        if (kE.getKeyCode() == KeyEvent.VK_L) {
            Main.darkMode = !Main.darkMode;
            if (Main.darkMode) {
                this.setBackground(Color.BLACK);
                Main.playerColor = Color.WHITE;
                Main.bulletColor = Color.WHITE;
                Main.bombColor = Color.WHITE;
                Main.holeColor = Color.WHITE;
            } else {
                this.setBackground(Color.WHITE);
                Main.playerColor = Color.BLACK;
                Main.bulletColor = Color.BLACK;
                Main.bombColor = Color.BLACK;
                Main.holeColor = Color.BLACK;
            }
        }
        if (kE.getKeyCode() == KeyEvent.VK_X) {
            if (devTools) {
                enemies.clear();
                blackHole.mass = 40;
                blackHole.hitboxRadius = 40;
            }
        }
        if (kE.getKeyCode() == KeyEvent.VK_V) {
            devTools = !devTools;
        }

        if (kE.getKeyCode() == KeyEvent.VK_A || kE.getKeyCode() == KeyEvent.VK_LEFT) {
            player.left = true;
            player.cruising = false;
        }
        if (kE.getKeyCode() == KeyEvent.VK_D || kE.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.right = true;
            player.cruising = false;
        }
        if (kE.getKeyCode() == KeyEvent.VK_W) {
            player.speed = 0.37;
        }

        if (kE.getKeyCode() == KeyEvent.VK_F) {
            player.angle += 30;
        }
        if (kE.getKeyCode() == KeyEvent.VK_S) {
            player.angle += 180;
        }
        if (kE.getKeyCode() == KeyEvent.VK_Q) {
            player.angle -= 30;
        }
        // !
        if (kE.getKeyCode() == KeyEvent.VK_R) {
            player.angle -= player.angle % 90 + ((int) (player.angle % 90) / 45) * 90;
        }
        if (kE.getKeyCode() == KeyEvent.VK_C) {
            player.cruising = !player.cruising;
            player.setCruiseDirection();
        }

        if (kE.getKeyCode() == KeyEvent.VK_SPACE) {
            if (reloaded) {
                shootBullet();
                reloaded = false;
            }
        }
        if (kE.getKeyCode() == KeyEvent.VK_COMMA) {
            if (numBombs > 0) {
                if (System.currentTimeMillis() - timeLastDropped >= 400) {
                    bombs.add(new Bomb(player.x, player.y, System.currentTimeMillis()));
                    timeLastDropped = System.currentTimeMillis();
                    numBombs--;
                }
            }
        }

        if (kE.getKeyCode() == KeyEvent.VK_E) {
            pause();
            Main.inventoryFrame.setVisible(true);
            Main.inventoryFrame.inventoryInfoPanel.moneyLabel.setText("money: " + player.money);
            Main.inventoryFrame.inventoryInfoPanel.killCountLabel.setText("kill count: " + killCount);
            Main.inventoryFrame.inventoryInfoPanel.bulletDamageLabel.setText("bullet damage: " + bulletDamage);
            Main.inventoryFrame.inventoryInfoPanel.numBombsLabel.setText("num bombs: " + numBombs);
            Main.inventoryFrame.inventoryUpgradePanel.requestFocus();
        }
    }

    public void shootBullet() {
        if (!upgrades.get("bulletSpread")) {
            Bullet b = new Bullet(player.x, player.y,
                    (int) player.angle + (!upgrades.get("accurateBullets") ? -2 + 4 * Math.random() : 0));
            bullets.add(b);
        }
        if (upgrades.get("bulletSpread")) {
            Bullet b1 = new Bullet(player.x, player.y,
                    (int) player.angle - 5 + (!upgrades.get("accurateBullets") ? -1 + 2 * Math.random() : 0));
            bullets.add(b1);

            Bullet b2 = new Bullet(player.x, player.y,
                    (int) player.angle + (!upgrades.get("accurateBullets") ? -1 + 2 * Math.random() : 0));
            bullets.add(b2);

            Bullet b3 = new Bullet(player.x, player.y,
                    (int) player.angle + 5 + (!upgrades.get("accurateBullets") ? -1 + 2 * Math.random() : 0));
            bullets.add(b3);
        }

        if (bullets.size() > 300) {
            bullets.remove(0);
        }
    }

    public void pause() {
        pauses++;
        switch (pauses % 2) {
        case (1):
            GameTimer.stop();
            break;
        case (0):
            GameTimer.start();
            break;
        }
    }

    @Override
    public void keyReleased(KeyEvent kE) {
        if (kE.getKeyCode() == KeyEvent.VK_A || kE.getKeyCode() == KeyEvent.VK_LEFT) {
            player.left = false;
        }
        if (kE.getKeyCode() == KeyEvent.VK_D || kE.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.right = false;
        }
        if (kE.getKeyCode() == KeyEvent.VK_W) {
            player.speed = 0.17;
        }
        if (kE.getKeyCode() == KeyEvent.VK_SPACE) {
            reloaded = true;
        }
    }

    // hell
    @Override
    public void keyTyped(KeyEvent kE) {
    }
}