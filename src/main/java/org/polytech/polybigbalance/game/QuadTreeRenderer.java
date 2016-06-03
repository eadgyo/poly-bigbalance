package org.polytech.polybigbalance.game;

import org.cora.graphics.graphics.Graphics;
import org.cora.physics.Engine.QuadTree;

/**
 * @autor ronan-h
 * @date 02/06/16
 */

/**
 * Used to render QuadTree collision bounds
 * QuadTree are used to divide space to speed up collision detection process
 * But they can be used when drawing scene
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
