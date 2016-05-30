package org.polytech.polybigbalance;

public class MainPBB
{
    public static void main(String[] args)
    {
        PolyBigBalance pbb = new PolyBigBalance();

        pbb.init();
        pbb.mainLoop();
        pbb.exit();
    }
}
