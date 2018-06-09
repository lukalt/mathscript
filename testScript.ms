// Testing basic if blocks
let a = 1
let b = 1

if a == b
  let a = a + b
  println("if entered") // comment test
  if a == 2
      println("  subif")
      let a = a + 1
      if a != 3
        println("    subsubif")
      else
        println("    subsubelse")
      fi
      println("    subsubfi")
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