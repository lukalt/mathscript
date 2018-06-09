
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