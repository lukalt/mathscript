mathscript is a very simple script language which supports lots of common math operations and data structures directly.

It is not fast and was never supposed to be fast because it directly parses the source while executing the code.

The syntax is very similar to pseudo code and here is a simple overview of it. Watch out - lines or statements must not end with a simicolon.

First, there is no main function or something similar which is executed on startup, the interpreter just starts at the first line of the file.
You are able to define functions but keep in mind that you can only invoke functions which have been defined before the line which is invoking them. 
A function declaration may look like this:

```
function functionName (arg0, arg1, ..., argn)
  // do stuff
  return result
end function
```

Function's arguments and return value is not typesafe which means that you can pass arguments of any type.
If you just use the statement `return` without any argument, `null` will be returned.

Variables can have any alphanumeric name (underscores are supported as well) but must not start with a number like in Java.
You can set a variable to a value with the `let` statement:
```
let k = 1
let m = k + 3
let k = k + 3
```
A few basic control statements known from other script languages are supported as well:

```
let i = 0
while i < 10
  println(i)
  i++
done
```

```
for i from 1 to 10
  println(i)
done
```

```
for i step 2 from 1 to 10
  println(i)
done
```

```
let l = list(1,2,3,4,5,6,7,8,9)
foreach e in l
  println(e)
done   
```

If you are trying to increment a variables's value, the following statements are equivalent:
```
let i = 0
let i = i +1
```
```
let i = 0
i += 1
```
```
let i = 0
i ++
```

mathscript supports a few common data structures which may contain objects of any type:

**Lists**
Lists (or Arrays) is an ordered collection of objects with no fixed length. 
You can initialize them using the function ``list(el1, el2, .., eln)`` or using the short form ``[el1, el2, .., eln].

* ``listadd(list, element)`` Adds the element to the given list
* ``listremove(list, element)`` Removes the first occurrence of element from the list
* ``listcontains(list, element)`` Returns true if the element is inside the list
* ``length(list)`` Returns the length of the list

**Tuples**
Tuples are Lists of a fixed length and are initialized using ``(el1, el2, .., eln)``

**Matrixes** (description coming soon)

**Sets** (description coming soon)

**Coming Functions**

* ``println(el1, .., eln)`` Prints the given objects to the console and starts a new line after each one
* ``print(e)`` Prints an element to the console without a new line at the end
* ``max(collection)`` Returns the maximum element of the collection
* ``max(el1, .., eln)`` Returns the maximum of the specified arguments
* ``min(collection)`` Returns the minimum element of the collection
* ``min(el1, .., eln)`` Returns the minimum of the specified arguments
* ``typeof(el)`` Returns the type of the object as a string
* ``read()`` Reads a object from the standard input, the syntax is the same as the one from script
* ``readln()`` Reads a string from the standard input
* ``tostring(object)`` Returns the objects representation as a string
* ``unset(variable)`` Deletes a set variable (required the variable's name as a string)

Of course, you can use common operations and comparisons such as (==, !=, <=, <, >, >= etc.) and (!boolean, fac(x), sqrt(x), ln(x), log(base,x))

(to be continued)