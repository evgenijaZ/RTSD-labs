package sample;

import java.util.ArrayList;
import java.util.List;

public class Functions {
    List<Double> generateSignal(int n, double Wmax, int N) {
        List<Double> values = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            double A = Math.random() * 10;
            double fi = Math.random() * 10;
            double val = 0;
            for (int k = 0; k < N; k++) {
                val += A * Math.sin((Wmax / n) * (i + 1) * k + fi);
            }
            values.add(val);
        }
        return values;
    }

    List<Double> DFT(List<Double> x) {
        List<Double> f = new ArrayList<>();
        final int N = x.size();

        for (int p = 0; p < N; p++) {
            double re = 0;
            double im = 0;
            for (int k = 0; k < N; k++) {
                re += x.get(k) * Math.cos(2 * Math.PI * p * k / N);
                im += x.get(k) * Math.sin(2 * Math.PI * p * k / N);
            }
            f.add(Math.sqrt(re * re + im * im));
        }

        return f;
    }

    Complex[][] generateTable(int N) {
        List<Complex> ks = new ArrayList<>();
        for (int pk = 0; pk < N; pk++) {
            Complex complex = new Complex(Math.cos(2 * Math.PI * pk / N), Math.sin(2 * Math.PI * pk / N));
            ks.add(complex);
        }

        Complex[][] t = new Complex[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                t[i][j] = ks.get((i * j) % N);
            }
        }
        return t;
    }

    List<Double> DFT2(List<Double> x) {
        List<Double> f = new ArrayList<>();
        final int N = x.size();
        Complex[][] table = generateTable(N);

        for (int p = 0; p < N; p++) {
            double re = 0;
            double im = 0;
            for (int k = 0; k < N; k++) {
                re += x.get(k) * table[p][k].a;
                im += x.get(k) * table[p][k].b;
            }
            f.add(Math.sqrt(re * re + im * im));
        }

        return f;
    }

    Complex[] FFT(Complex[] x) {
        final int n = x.length;
        if (n == 1) return new Complex[]{x[0]};

        Complex[] even = new Complex[n / 2];
        Complex[] odd = new Complex[n / 2];

        for (int m = 0; m < n / 2; m++) {
            even[m] = x[2 * m];
            odd[m] = x[2 * m + 1];
        }

        Complex[] e = FFT(even);
        Complex[] o = FFT(odd);

        Complex[] res = new Complex[n];
        for (int i = 0; i < n / 2; i++) {
            Complex w = new Complex(Math.cos(-2 * i * Math.PI / n), Math.sin(-2 * i * Math.PI / n));
            res[i] = e[i].add(o[i].mul(w));
            res[i + n / 2] = e[i].sub(o[i].mul(w));
        }
        return res;
    }

    List<Double> FFT(List<Double> x) {
        Complex[] xc = new Complex[x.size()];
        for (int i = 0; i < xc.length; i++) {
            xc[i] = new Complex(x.get(i), 0);
        }

        Complex[] res = FFT(xc);
        List<Double> f = new ArrayList<>();
        for (Complex c : res) {
            f.add(Math.sqrt(c.a * c.a + c.b * c.b));
        }
        return f;
    }
}
