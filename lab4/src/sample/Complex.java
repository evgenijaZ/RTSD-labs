package sample;

public class Complex {
    double a;
    double b;

    Complex(double a, double b) {
        this.a = a;
        this.b = b;
    }

    Complex add(Complex that) {
        return new Complex(this.a + that.a, this.b + that.b);
    }

    Complex sub(Complex that) {
        return new Complex(this.a - that.a, this.b - that.b);
    }

    Complex mul(Complex that) {
        return new Complex(this.a * that.a - this.b * that.b, this.b * that.a + this.a * that.b);
    }
}
