/*
  Test Script
*/

println(matrixparse("1 2 3|4 5 6|7 8 9"))

// Testing basic if blocks
let a = 1
let b = 1

if a == b
  let a = a + b
  println("if entered") // comment test
  if a != 2
    println("  subif")
  else
    println("  subelse")
  fi
  println("  subfi")
else
  println("else entered")
fi
println("endif")

// testing while loops
let i = 0
/*
while i < 10
  println(i)
  let i = i + 1
done
*/

// testings lists and other structures
let l = list(1,3,3,7)
let m = list(4,2,4,2)

let matrix = matrix(l,m)
println(matrix)

transpose(matrix)
println(matrix)

let set = {1,3,3,7}
println(set) // prints {1,3,7}
println()
let list = list(1,2,3,4,5)
listrev(list)
println(list)
listshuffle(list)
println(list)
listdsort(list)
println(list)
unset("list")

println(typeof(matrix))

let boolFlag = matrix == a
println(boolFlag)
println(!boolFlag)
println(!boolFlag == !boolFlag)
println(boolFlag == !boolFlag)