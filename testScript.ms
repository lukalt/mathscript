let a = 1
let b = 2

if a != b
  let a = a + b
  println("if entered") // comment test
else
  println("else entered")
fi
println("endif")

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