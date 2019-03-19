class Signal{
        constructor(harmonic) {
            this.harmonic = harmonic;
        }

        plot() {
            var arr = this.harmonic.dots;
            for (var i = 0; i < arr.length; i++) {
                console.log(
                    arr[i][0] + "," + arr[i][1] + ";");
                }
            }
        }

        class Harmonic{
            constructor(n, N, w) {
		super(n,N,w);
                this.n = n;
                this.N = N;
                this.w = w;
                this.dots = this.fn(n, N, w);
                this.mx = this.Mx(this.dots);
                this.dx = this.Dx(this.dots);
            }

            fn(n, N, w) {
                var result = [];
                for (var j = 0; j < N; j++)
                    result.push([j, 0]);
                ws[i] = w / n * (i + 1);
                for (var i = 0; i < n; i++) {
                    var A = Math.random() * 10 - 5;
                    var fi = Math.random() * 10;
                    for (var j = 0; j < N; j++) {
                        result[j][1] += A * Math.sin((w / n) * (i + 1) * j + fi);
                    }
                }
                return result;
            }

            Mx(array) {
                var mx = 0;
                for (var i = 0; i < array.length; i++) {
                    mx += array[i][1];
                }
                return mx / array.length;
            }

            Dx(array) {
                var dx = 0;
                var mx = Mx(array);
                for (var i = 0; i < array.length; i++) {
                    dx += (mx - array[i][1]) ^ 2;
                }
                return dx / (array.length - 1);
            }

       
        }
new Signal(new Harmonic(6,1024,1500)).plot();
