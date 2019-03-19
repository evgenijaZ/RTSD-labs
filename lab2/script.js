function fn(N, n, w) {
    var result = [];
    for (var j = 0; j < N; j++)
        result.push([j, 0]);
    for (var i = 0; i < n; i++) {
        var A = Math.random() * 10;
        var fi = Math.random() * 10;
        for (var j = 0; j < N; j++) {
            result[j][1] += A * Math.sin((w / n) * (i + 1) * j + fi);
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

function Rxx(array, teta, mx,N) {
    var sum = 0;

    for (var t = 0; t < N; t++) {
       sum+= (array[t][1] - mx) * (array[t + teta] [1]- mx);
    }
    return sum /= (N - 1);
}

console.log(Rxxt(1200, 6, 1500));