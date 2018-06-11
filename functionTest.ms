/*
function addTwo(arg)
  return arg + 2
end function

function double(a)
  return 2 * a
end function

function comb(a,b)
  let k = double(a)
  return k + addTwo(b)
end function

let res = addTwo(1)
println(res)
while res < 100
  let res = double(res)
  println(res)
done

let res = comb(1,2)
println(res)

let i = 0
println(i)
i += 2
println(i)
i *= 4
println(i)

i++
println(i)
let j = 5
while j > 0
  i--
  j--
done
println(i)
*/
let l = list(1,2,3)
let b = list()
listadd(b, "a")
listadd(b, "b")
foreach e in l
  while e >= 0
    foreach k in b
      println(e + k)
    done
    e--
  done
done

println()
println()

function start(s)
  return s * 3
end function

for a from start(1) to 10
  println(a)
done

println()
println()

for a step 4 from 1 to 10
  println(a)
done

let m = [1..20]
println(m)