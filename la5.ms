let aStr = "0 0 -1 0|3 0 1 -7|0 1 0 -8|-1 0 0 2"
let A = matrixparse(aStr)
let bStr = "1 3 3 1|-1 2 -1 2|3 -2 5 -3"
let B = matrixparse(bStr)
let cStr = "4 1 0 -39|2 -5 1 -8|-3 5 -5 -32"
let C = matrixparse(cStr)

let Atr = transpose(A)
let Btr = transpose(B)
let Ctr = transpose(C)

let BA = B * A
let BA_C = BA + C

println("BA + C: ")
println(BA_C)

println("A*Atr:")
println(A * Atr)

let CtrB = Ctr * B
println("CtrB + A")
println(CtrB + A)
