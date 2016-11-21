public class Fraction {

	private int num, den;

	public Fraction(int num, int den) {
		this.num = num;
		this.den = den;
		this.reduse();
	}

	public Fraction reduse() {
		int c = getMaximumDevider(num, den);
		num /= c;
		den /= c;
		return this;
	}

	public Fraction reverse() {
		int den = this.den;
		this.den = num;
		this.num = den;

		return this;
	}

	public Fraction setDenumenator(int den) {
		this.num = (int)(1f * this.den / den);
		this.den = den;
		return this;
	}

	public Fraction setNuminator(int num) {
		this.num = num;
		return this;
	}

	public int getNuminator() {
		return num;
	}

	public int getDenuminator() {
		return den;
	}

	public Fraction plus(Fraction f) {
		int gen = getMinimumDenomenator(den, this.den);

		setDenumenator(gen);
		f.setDenumenator(gen);

		num += f.num;

		f.reduse();
		reduse();

		return this;
	}

	public Fraction minus(Fraction f) {
		int gen = getMinimumDenomenator(den, this.den);

		setDenumenator(gen);
		f.setDenumenator(gen);

		num -= f.num;

		f.reduse();
		reduse();

		return this;
	}

	public Fraction multiply(Fraction f) {
		this.num *= f.num;
		this.den *= f.den;
		this.reduse();

		return this;
	}

	public Fraction devide(Fraction f) {
		multiply(f.reverse());
		f.reverse();
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if(o.getClass() == Fraction.class) {
			Fraction f = (Fraction)o;
			return f.num == num && f.den == den;
		}

		return super.equals(o);
	}

	@Override
	protected Object clone() {
		return new Fraction(num, den);
	}

	@Override
	public String toString() {
		return num + "/" + den;
	}

	public static int getMaximumDevider(int a, int b) {
		for(int dev = Math.max(a, b); dev > 0; dev++)
			if(a % dev == 0 && b % dev == 0)
				return dev;
		return 1;
	}

	public static int getMinimumDenomenator(int a, int b) {
		for(int i = 1; i < a * b; i++)
			if(i % a == 0 && i % b == 0)
				return i;

		return a * b;
	}
}
