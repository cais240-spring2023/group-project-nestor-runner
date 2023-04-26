package edu.wsu.model;
import edu.wsu.model.entities.*;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertEquals;

public class test {
    @Test
    public void testNestorStartPos(){
        Nestor nestor = new Nestor();
        assertEquals(nestor.getX(), 50);
        assertEquals(nestor.getY(), 350);
    }

    @Test
    public void testNestorSetters(){
        Nestor nestor = new Nestor();
        nestor.setY(300);
        nestor.setX(100);
        assertEquals(nestor.getX(), 100);
        assertEquals(nestor.getY(), 300);
    }


    @Test
    public void testHoleType() {
        Hole hole = new Hole();
        assertEquals(hole.type(), Entity.Type.Hole);
    }

    @Test
    public void testHoleX() {
        Hole hole = new Hole();
        hole.setX(10);
        assertEquals(hole.getX(), 10);
    }
    @Test
    public void testHoleY() {
        Hole hole = new Hole();
        hole.setY(20);
        assertEquals(hole.getY(), 20);
    }

    @Test
    public void testCoinX() {
        Coin coin = new Coin();
        coin.setX(10);
        assertEquals(coin.getX(), 10);
    }

    @Test
    public void testCoinY() {
        Coin coin = new Coin();
        coin.setY(20);
        assertEquals(coin.getY(), 20);
    }

    @Test
    public void testCoinType() {
        Coin coin = new Coin();
        assertEquals(coin.type(), Entity.Type.Coin);
    }

    @Test
    public void testLargeObstacleX() {
        LargeObstacle obstacle = new LargeObstacle();
        obstacle.setX(10);
        assertEquals(obstacle.getX(), 10);
    }

    @Test
    public void testLargeObstacleY() {
        LargeObstacle obstacle = new LargeObstacle();
        obstacle.setY(20);
        assertEquals(obstacle.getY(), 20);
    }

    @Test
    public void testLargeObstacleWidth() {
        LargeObstacle obstacle = new LargeObstacle();
        assertEquals(obstacle.getWidth(), 55);
    }

    @Test
    public void testLargeObstacleHeight() {
        LargeObstacle obstacle = new LargeObstacle();
        assertEquals(obstacle.getHeight(), 75);
    }

    @Test
    public void testLargeObstacleType() {
        LargeObstacle obstacle = new LargeObstacle();
        assertEquals(obstacle.type(), Entity.Type.LargeObstacle);
    }

    @Test
    public void testSmallObstacleX() {
        SmallObstacle obstacle = new SmallObstacle();
        obstacle.setX(10);
        assertEquals(obstacle.getX(), 10);
    }

    @Test
    public void testSmallObstacleY() {
        SmallObstacle obstacle = new SmallObstacle();
        obstacle.setY(20);
        assertEquals(obstacle.getY(), 20);
    }

    @Test
    public void testSmallObstacleWidth() {
        SmallObstacle obstacle = new SmallObstacle();
        assertEquals(obstacle.getWidth(), 50);
    }

    @Test
    public void testSmallObstacleHeight() {
        SmallObstacle obstacle = new SmallObstacle();
        assertEquals(obstacle.getHeight(), 50);
    }

    @Test
    public void testSmallObstacleType() {
        SmallObstacle obstacle = new SmallObstacle();
        assertEquals(obstacle.type(), Entity.Type.SmallObstacle);
    }

}
