package nazarrod.adventgem.advgem.utils;

import nazarrod.adventgem.advgem.model.Platform2D;

/**
 * Class that provides methods for getting relative position of figures
 */
public class Geometry {
    /**Check if dot [x,y] is inside a platofrm*/
    public static boolean checkBelongs(int x, int y, Platform2D p){
        int xp1 = p.getX();
        int yp1 = p.getY();
        int xp2 = xp1+p.getWidth();
        int yp2 = yp1+p.getHeight();
        return xp1 <= x && x <= xp2 && yp1 <= y && y <= yp2;
    }

    /**Check is platform is inside x,y bounds*/
    public static boolean outOfBounds(Platform2D p,int x,int y){
        Platform2D o = new Platform2D(0,0,x,y);
        return !(checkIndCollision(p,o) || checkIndCollision(o,p));
    }

    /**Check collisions between platforms p and o*/
    public static boolean checkCollision(Platform2D p,Platform2D o){
        return (checkIndCollision(p,o) || checkIndCollision(o,p) );
    }

    /**Check individual collision between platforms p and o*/
    private static boolean checkIndCollision(Platform2D p,Platform2D o){
        int x,y;
        x = p.getX();
        y = p.getY();
        if(checkBelongs(x,y,o))return true;

        x = p.getX();
        y = p.getY()+p.getHeight();
        if(checkBelongs(x,y,o))return true;

        x = p.getX()+p.getWidth();
        y = p.getY();
        if(checkBelongs(x,y,o))return true;

        x = p.getX()+p.getWidth();
        y = p.getY()+p.getHeight();
        if(checkBelongs(x,y,o))return true;

        if( (p.getX() <= o.getX() && o.getX()+o.getWidth() <= p.getX()+p.getWidth()) &&
                (p.getY() <= o.getY() && o.getY()+o.getHeight() <= p.getY()+p.getHeight()) )return true;

        if( (o.getX() <= p.getX() && p.getX()+p.getWidth() <= o.getX()+o.getWidth()) &&
                (o.getY() <= p.getY() && p.getY()+p.getHeight() <= o.getY()+o.getHeight()) )return true;

        if( (p.getX() <= o.getX() && o.getX()+o.getWidth() <= p.getX()+p.getWidth()) &&
                (o.getY() <= p.getY() && p.getY()+p.getHeight() <= o.getY()+o.getHeight()) )return true;

        if( (o.getX() <= p.getX() && p.getX()+p.getWidth() <= o.getX()+o.getWidth()) &&
                (p.getY() <= o.getY() && o.getY()+o.getHeight() <= p.getY()+p.getHeight()) )return true;


        //TODO It is not complete
        return false;
    }
}
