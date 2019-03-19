//const nano = require('nanoseconds');

function fn(N, n, w) {
    var result = [];
    for (var j = 0; j < N; j++)
        result.push([j, 0]);
    for (var i = 0; i < n; i++) {
        var A = Math.random() * 10;
        var fi = Math.random() * 10;
        for (var k = 0; k < N; k++) {
            result[k][1] += A * Math.sin((w / n) * (i + 1) * k + fi);
        }
    }
    return result;
}

function Mx(array) {
    var mx = 0;
    for (var i = 0; i < array.length; i++) {
        mx += array[i][1];
    }
    return mx / array.length;
}

function Rxxt(N, n, w) {
    var result = [];
    var array = fn(2* N, n, w);
	var mx=Mx(array);
    for (var j = 0; j < N / 2; j++)
        result.push([j, 0]);
    for (var teta = 0; teta < N/ 2; teta++) {
        result[teta][1] = Rxx(array, teta, mx,N/2);
    }
    return result;
}

function tRxN(Nmax, step, N, n, w){
	var chart = [];
	var array1;
	var array2;
	var start, end;
	var mx1;
	var mx2;

	for (var Ni = step; Ni <= Nmax; Ni=Ni+step) {
		var i = Ni/step - 1;
		chart[i] = [Ni, 0, 0];
		
		//start = nano(process.hrtime());
		start = window.performance.now();

		array1	= fn(2* Ni, n, w);

		mx1 = Mx(array1);
		Rxx(array1, 10, mx1, Ni/2);

		//end = nano(process.hrtime());
		end = window.performance.now();
		chart[i][1] = end - start;
		
		//start = nano(process.hrtime());
		start = window.performance.now();
		array1	= fn(2* Ni, n, w);
		array2	= fn(2* Ni, n, w);
		mx1 = Mx(array1);
		mx2 = Mx(array2);
		Rxy(array1, array2, mx1, mx2, Ni/2);
		//end = nano(process.hrtime());
		end = window.performance.now();

		chart[i][2] = end - start;

	}
	return chart;
}

function Rxx(array, teta, mx,N) {
    var sum = 0;

    for (var t = 0; t < N; t++) {
       sum+= (array[t][1] - mx) * (array[t + teta] [1]- mx);
    }
    return sum /= (N - 1);
}

function Rxy(array1, array2, mx1, mx2, N) {
    var sum = 0;

    for (var t = 0; t < N; t++) {
       sum+= (array1[t][1] - mx1) * (array2[t][1]- mx2);
    }
    return sum /= (N - 1);
}

//console.log(tRxN(1000, 10, 1200, 6, 1500));