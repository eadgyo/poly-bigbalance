package org.polytech.polybigbalance.score;

public class Test
{

    public static void main(String[] args)
    {
        HighScores hs = new HighScores();
        System.out.println(hs);
        hs.addScore(new Score("B", 25));
        System.out.println(hs);
        hs.addScore(new Score("A", 50));
        System.out.println(hs);
        hs.addScore(new Score("C", 30));
        System.out.println(hs);
        hs.addScore(new Score("D", 100));
        System.out.println(hs);
        hs.addScore(new Score("E", 20));
        System.out.println(hs);
        hs.addScore(new Score("F", 10));
        System.out.println(hs);
        hs.addScore(new Score("G", 70));
        System.out.println(hs);
        hs.addScore(new Score("H", 27));
        System.out.println(hs);
    }
}