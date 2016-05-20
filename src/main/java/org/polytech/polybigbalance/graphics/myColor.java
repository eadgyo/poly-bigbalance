package org.polytech.polybigbalance.graphics;

public class myColor
{
    public float r;
    public float g;
    public float b;
    public float a;

    public myColor()
    {
	this(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public myColor(float r, float g, float b, float a)
    {
	set(r, g, b, a);
    }

    public myColor(float r, float g, float b)
    {
	set(r, g, b, 1.0f);
    }

    public void set(float r, float g, float b, float a)
    {
	set(r, g, b);
	this.a = a;
    }

    public void set(float r, float g, float b)
    {
	this.r = r;
	this.g = g;
	this.b = b;
    }

    public myColor add(myColor color)
    {
	return new myColor(color.r + r, color.g + g, color.b + b, color.a + a);
    }

    public myColor add(float color)
    {
	return new myColor(color + r, color + g, color + b, color + a);
    }

    public void selfAdd(myColor color)
    {
	r += color.r;
	a += color.a;
	g += color.g;
	b += color.b;
    }

    public void selfAdd(float color)
    {
	r += color;
	a += color;
	g += color;
	b += color;
    }

    public myColor multiply(float factor)
    {
	return new myColor(factor * r, factor * g, factor * b, factor * a);
    }

    public void selfMultiply(float factor)
    {
	r *= factor;
	a *= factor;
	g *= factor;
	b *= factor;
    }

    public void set(myColor color)
    {
	r = color.r;
	a = color.a;
	g = color.g;
	b = color.b;
    }

    public void set(float color)
    {
	r = color;
	a = color;
	g = color;
	b = color;
    }

    public static myColor RED(float a)
    {
	return new myColor(1.0f, 0.0f, 0.0f, a);
    }

    public static myColor RED()
    {
	return RED(1.0f);
    }

    public static myColor WHITE(float a)
    {
	return new myColor(1.0f, 1.0f, 1.0f, a);
    }

    public static myColor WHITE()
    {
	return WHITE(1.0f);
    }

    public static myColor BLUE(float a)
    {
	return new myColor(0.0f, 0.0f, 1.0f, a);
    }

    public static myColor BLUE()
    {
	return BLUE(1.0f);
    }

    public static myColor GREEN(float a)
    {
	return new myColor(0.0f, 1.0f, 0.0f, a);
    }

    public static myColor GREEN()
    {
	return GREEN(1.0f);
    }

    public static myColor BLACK(float a)
    {
	return new myColor(0.0f, 0.0f, 0.0f, a);
    }

    public static myColor BLACK()
    {
	return BLACK(1.0f);
    }
}
