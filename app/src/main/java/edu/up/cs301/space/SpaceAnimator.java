package edu.up.cs301.space;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import java.util.ArrayList;
import java.util.Random;

import edu.up.cs301.animation.Animator;

/**
 * space animation
 *
 * @author Steve Vegdahl
 * @version September 2017
 *
 * This file was edited by:
 *
 * Logan J Crawford
 * November 3, 2017
 * Bare Bones is complete.
 * Enhancements Include:
 * Multiple suns with varying size, color, and gravities.
 * Stars "twinkle".
 */
public class SpaceAnimator implements Animator {

    /**
     * Interval between animation frames: .05 seconds (i.e., about 50/1000 times
     * per second).
     *
     * @return the time interval between frames, in milliseconds.
     */
    //instance variables
    // the animator
    private SpaceAnimator animator;
    private boolean isTouch;
    private Random rand = new Random(); //random() instance variable
    private int r = rand.nextInt(255); //a red filter between 0-255
    private int g = rand.nextInt(255); //a green filter between 0-255
    private int b = rand.nextInt(255); //a blue filter between 0-255
    private ArrayList<PointF> stLoc = new ArrayList<PointF>(); //create ArrayList of type PointF
    protected float x; //x-co-ord
    protected float y; //y-co-ord
    protected float vx; //velocity in x direction
    protected float vy; //velocity in y direction
    protected float aX;
    protected float aY;
    private double angG; //gun angle
    private PointF velBall = new PointF(5,5);
    protected PointF curBall = new PointF(100,175);;
    Point sp = new Point(1000, 600);
    Point sp1 = new Point(1750, 400);
    Point sp2 = new Point(380, 980);
    private int count = 1;
    private GestureDetector myDetector;
    public static final int INIT_SIZE = 20;
    public static final int MIN_SIZE = 3;
    public static final int MAX_SIZE = 100;
    private int size = INIT_SIZE;
    protected float m;
    protected float d;
    protected float gC = 2;

    //constructor
    public SpaceAnimator() {
        for(int i = 0; i < 100; i++)
        {
            x = rand.nextInt(2000);
            y = rand.nextInt(1500);
            stLoc.add(new PointF(x,y));
        }


    }
    @Override
    public int interval() {
        return 50;
    }

    /**
     * The background color: black.
     *
     * @return the background color onto which we will draw the image.
     */
    @Override
    public int backgroundColor() {
        // create/return the background color
        return Color.BLACK;
    }

    /**
     * Action to perform on clock tick
     *
     * @param canvas the graphics object on which to draw
     */
    @Override
    public void tick(Canvas canvas) {
        count++;
        //instance variables
        int Low = 1;
        int High = 3;
        int randomColor = Color.rgb(r, g, b); //color instance variable of randomColor.

        Paint ballPaint = new Paint(); //new Paint object ballPaint
        Paint sunPaint = new Paint(); //new Paint object sunPaint
        Paint starPaint = new Paint(); //new Paint object starPaint
        Paint sun1Paint = new Paint(); //new Paint object sun1Paint
        Paint sun2Paint = new Paint(); //new Paint object sun2Paint
        Paint gunPaint = new Paint(); //new Paint object gunPaint
        starPaint.setColor(Color.WHITE); //set the starPaint object to white
        sunPaint.setColor(Color.YELLOW); //set the sunPaint object to yellow
        sun1Paint.setColor(Color.GREEN); //set the sun1Paint object to green
        sun2Paint.setColor(Color.RED); //set the sun2Paint object to red
        gunPaint.setColor(Color.GRAY); //set the gunPaint object to gray
        ballPaint.setColor(randomColor); //set the ballPaint object to a random color
        //curBall = new PointF(100, 175);
        //int num = (count*15);



        //find x component of the magnitude
        x = sp.x - curBall.x;
        //x = (sp.x - curBall.x)+(sp1.x - curBall.x)+(sp2.x - curBall.x);
        //find y component of the magnitude
        y = sp.y - curBall.y;
        //y = (sp.y - curBall.y)+(sp1.y - curBall.y)+(sp2.y - curBall.y);
        //find the distance between the sun and the ball
        d = (float) Math.sqrt((x * x) + (y * y));
        //find the magnitude of acceleration
        m = gC / ((x * x) + (y * y));
        //change the acceleration based on the pull of gravity..
        //in the x direction
        aX = m * (x / d);
        //in the y direction
        aY = m * (y / d);
        //change the velocity based on the acceleration..
        //in the x direction
        vx += aX;
        //in the y direction
        vy += aY;
        //move the ball based on its velocity
        curBall.set(x += vx, y += vy);
        canvas.drawCircle(curBall.x, curBall.y, 20, ballPaint);

        //moveBall(aX, aY, canvas.getWidth(), canvas.getHeight());
            //canvas.drawCircle(curBall.x, curBall.y, 20, ballPaint); //Then create a new curBall shot from the gun.
            // Check if there is a ball present and what happens if there is
            if (curBall != null) {
                if (curBall.equals(sp.x, sp.y)) //if curBall hits yellow sun, set curBall to null(destroy)
                {
                    curBall = null;
                } else if (curBall.equals(sp1.x, sp1.y)) //if curBall hits green sun, set curBall to null(destroy)
                {
                    curBall = null;
                } else if (curBall.equals(sp2.x, sp2.y)) //if curBall hits red sun, set curBall to null(destroy)
                {
                    curBall = null;
                } else if (curBall.equals(canvas.getWidth(), canvas.getHeight())) //if curBall leaves the screen, set curBall to null(destroy)
                {
                    curBall = null;
                }
            }


        int radius = rand.nextInt(High-Low) + Low; //stars radius changes, causing a "twinkle", take thee parameters of ((0 through (3-1))+1)
        //This yields a random radius size of 1-3.
        for(PointF p: stLoc) //iterate through the ArrayList of coordinates.
        {
            canvas.drawCircle(p.x, p.y, radius, starPaint); //Draw the stars at the iterated coordinates, with changing radius, and color white.
        }
        canvas.drawCircle(sp.x, sp.y, 70, sunPaint); //Draw the sun at a set point on the screen. with radius 70 and color yellow.
        canvas.drawCircle(sp1.x, sp1.y, 80, sun1Paint); //Draw the sun1 at a set point on the screen with radius 80 and color green.
        canvas.drawCircle(sp2.x, sp2.y, 45, sun2Paint); //Draw the sun2 at set point on the screen with radius 45 and color red.
        canvas.drawRect(0.0f, 150.0f, 100.0f, 200.0f, gunPaint);
        //Rect gun = new Rect(0, 150, 100, 200);
        //RotateAnimation gunRot = new RotateAnimation(0.0f, -90.0f, 0, 175);
        //gunRot.setStartOffset(1000);
        //gunRot.setDuration(1000);
        //gunRot.setFillAfter(true);
        //gunRot.setInterpolator(new LinearInterpolator());
        //gun.startAnimation(gunRot);
    }

    /**
     * Tells that we never pause.
     *
     * @return indication of whether to pause
     */
    @Override
    public boolean doPause() {
        return false;
    }

    /**
     * Tells that we never stop the animation.
     *
     * @return indication of whether to quit.
     */
    @Override
    public boolean doQuit() {
        return false;
    }

    /**
     * event method when screen is touched
     */
    @Override
    public void onTouch(MotionEvent event)
    {

    }

}//class SpaceAnimator
