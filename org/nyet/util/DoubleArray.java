package org.nyet.util;

import vec_math.SavitzkyGolaySmoothing;

public class DoubleArray
{
    private int sp = 0; // "stack pointer" to keep track of position in the array
    private double[] array;
    private int growthSize;

    public DoubleArray()
    {
        this( 1024 );
    }

    public DoubleArray( double[] a)
    {
	this(a.length);
	System.arraycopy( a, 0, this.array, 0, a.length );
	this.sp=a.length;
    }

    public DoubleArray( double[] a, int initialSize)
    {
	this(initialSize);
	System.arraycopy( a, 0, this.array, 0, a.length );
	this.sp=initialSize;
    }

    public DoubleArray( int initialSize )
    {
        this( initialSize, initialSize );
    }

    public DoubleArray( int initialSize, int growthSize )
    {
        this.growthSize = growthSize;
        array = new double[ initialSize ];
    }

    public void append( double d )
    {
        if( sp >= array.length ) // time to grow!
        {
            double[] tmpArray = new double[ array.length + growthSize ];
            System.arraycopy( array, 0, tmpArray, 0, array.length );
            array = tmpArray;
        }
        array[ sp ] = d;
        sp += 1;
    }

    public int size() { return sp; }

    public double[] toArray()
    {
        double[] trimmedArray = new double[ sp ];
        System.arraycopy( array, 0, trimmedArray, 0, trimmedArray.length );
        return trimmedArray;
    }

    public double get(int i) {
	return i<this.sp?array[i]:0;
    }

    public double[] _add(double d) {
        double[] out = new double[ sp ];
	for(int i=0;i<this.sp;i++) {
	    out[i]=this.array[i]+d;
	}
	return out;
    }
    public DoubleArray add(double d) {
	return new DoubleArray(this._add(d));
    }

    public double[] _add(double[] d) {
	int len = this.sp>d.length?this.sp:d.length;
        double[] out = new double[ len ];
	for(int i=0;i<len;i++) {
	    out[i]=this.get(i)+(i<d.length?d[i]:0);
	}
	return out;
    }
    public DoubleArray add(DoubleArray d) {
	return new DoubleArray(this._add(d.toArray()));
    }

    public double[] _mult(double d) {
        double[] out = new double[ sp ];
	for(int i=0;i<this.sp;i++) {
	    out[i]=this.array[i]*d;
	}
	return out;
    }
    public DoubleArray mult(double d) {
	return new DoubleArray(this._mult(d));
    }

    public double[] _mult(double[] d) {
	int len = sp>d.length?this.sp:d.length;
        double[] out = new double[ len ];
	for(int i=0;i<len;i++) {
	    out[i]=this.get(i)*(i<d.length?d[i]:0);
	}
	return out;
    }
    public DoubleArray mult(DoubleArray d) {
	return new DoubleArray(this._mult(d.toArray()));
    }

    public double[] _div(double d) {
        double[] out = new double[ sp ];
	for(int i=0;i<this.sp;i++) {
	    out[i]=this.array[i]/d;
	}
	return out;
    }
    public DoubleArray div(double d) {
	return new DoubleArray(this._div(d));
    }

    public double[] _div(double[] d) {
        double[] out = new double[ sp ];
	for(int i=0;i<this.sp;i++) {
	    out[i]=this.get(i)/(i<d.length?d[i]:1);
	}
	return out;
    }
    public DoubleArray div(DoubleArray d) {
	return new DoubleArray(this._div(d.toArray()));
    }

    public double[] _derivative(double[] d) {
        double[] out = new double[ sp ];
	if(sp==1 || d.length<2 || d.length!=sp) {
	    System.out.println("sp: " + sp +", d.len: " + d.length +
	    ", sp=" + sp);
	}
	for(int i=0;i<this.sp;i++) {
	    double dy, dx;
	    int i0=Math.max(i-1, 0), i1=Math.min(i+1,this.sp-1);
	    out[i]=(this.get(i1)-this.get(i0))/(d[i1]-d[i0]);
	}
	return out;
    }
    public DoubleArray derivative(DoubleArray d) {
	return new DoubleArray(this._derivative(d.toArray()));
    }
    public DoubleArray derivative(DoubleArray d, boolean smooth) {
	if(!smooth) return this.derivative(d);
	SavitzkyGolaySmoothing s = new SavitzkyGolaySmoothing(5,5);
	return new DoubleArray(s.smoothAll(this._derivative(d.toArray()))).smooth();
    }

    public DoubleArray smooth() {
	SavitzkyGolaySmoothing s = new SavitzkyGolaySmoothing(5,5);
	return new DoubleArray(s.smoothAll(this.array));
    }
}