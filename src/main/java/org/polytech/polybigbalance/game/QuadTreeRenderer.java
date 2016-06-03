package org.polytech.polybigbalance.game;

import org.cora.graphics.graphics.Graphics;
import org.cora.physics.Engine.QuadTree;

/**
 * Created by ronan-h on 02/06/16.
 */
public class QuadTreeRenderer
{
    public static void drawQuadTree(Graphics g, QuadTree quadTree)
    {
        if (quadTree.getRect() == null)
            return;

        g.drawForm(quadTree.getRect());

        QuadTree nodes[] = quadTree.getNodes();

        if (nodes[0] != null)
        {
            for (int i = 0; i < nodes.length; i++)
            {
                drawQuadTree(g, nodes[i]);
            }
        }
    }
}
