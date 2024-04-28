- [ ] Our scanner here, like most, discards comments and whitespace since those aren’t 
    needed by the parser. Why might you want to write a scanner that does not discard 
    those? What would it be useful for?

- [X] Add support to Lox’s scanner for C-style /* ... */ block comments. Make sure
    to handle newlines in them. Consider allowing them to nest. Is adding support for
    nesting more work than you expected? Why

- [ ] In C, a block is a statement form that allows you to pack a series of statements 
    where a single one is expected. The comma operator is an analogous syntax for
    expressions. A comma-separated series of expressions can be given where a single
    expression is expected (except inside a function call’s argument list). At runtime,
    the comma operator evaluates the le! operand and discards the result. Then it
    evaluates and returns the right operand.
    Add support for comma expressions. Give them the same precedence and 
    associativity as in C. Write the grammar, and then implement the necessary parsing
    code.

- [ ] Likewise, add support for the C-style conditional or “ternary” operator ?:. What
    precedence level is allowed between the ? and :? Is the whole operator le!- 
    associative or right-associative?

- [ ] Add error productions to handle each binary operator appearing without a le!- 
    hand operand. In other words, detect a binary operator appearing at the beginning
    of an expression. Report that as an error, but also parse and discard a right-hand 
    operand with the appropriate precedence

- [ ] Many languages define + such that if either operand is a string, the other is 
    converted to a string and the results are then concatenated. For example,
    "scone" + 4 would yield scone4. Extend the code in visitBinaryExpr() to support that. 

- [X] What happens right now if you divide a number by zero? What do you think should 
    happen? Justify your choice. How do other languages you know handle division by
    zero and why do they make the choices they do?
    Change the implementation in visitBinaryExpr() to detect and report a
    runtime error for this case.

- [ ] The REPL no longer supports entering a single expression and automatically
    printing its result value. That’s a drag. Add support to the REPL to let users type in
    both statements and expressions. If they enter a statement, execute it. If they enter
    an expression, evaluate it and display the result value

- [ ] Maybe you want Lox to be a little more explicit about variable initialization. Instead
    of implicitly initializing variables to nil, make it a runtime error to access a
    variable that has not been initialized or assigned to.

- [ ] Unlike Lox, most other C-style languages also support break and continue
    statements inside loops. Add support for break statements.
    The syntax is a break keyword followed by a semicolon. It should be a syntax
    error to have a break statement appear outside of any enclosing loop. At
    runtime, a break statement causes execution to jump to the end of the nearest
    enclosing loop and proceeds from there. Note that the break may be nested
    inside other blocks and if statements that also need to be exited

- [ ] Lox’s function declaration syntax performs two independent operations. It creates
    a function and also binds it to a name. This improves usability for the common
    case where you do want to associate a name with the function. But in functionalstyled code, you o!en want to create a function to immediately pass it to some
    other function or return it. In that case, it doesn’t need a name.
    Languages that encourage a functional style usually support anonymous functions
    or lambdas—an expression syntax that creates a function without binding it to a
    name. Add anonymous function syntax to Lox so that this works:
    fun thrice(fn) {
        for (var i = 1; i <= 3; i = i + 1) {
            fn(i);
        }
    }
    thrice(fun (a) {
        print a;
    });
    // "1".
    // "2".
    // "3
    How do you handle the tricky case of an anonymous function expression occurring
    in an expression statement:
    fun () {};

- [ ] Extend the resolver to report an error if a local variable is never used.

- [ ] Our resolver calculates which environment the variable is found in, but it’s still
    looked up by name in that map. A more e"icient environment representation
    would store local variables in an array and look them up by index.
    Extend the resolver to associate a unique index for each local variable declared in a
    scope. When resolving a variable access, look up both the scope the variable is in
    and its index and store that. In the interpreter, use that to quickly access a variable
    by its index instead of using a map.

- [ ] We have methods on instances, but there is no way to define “static” methods that can
  be called directly on the class object itself. Add support for them. Use a class keyword 
  preceding the method to indicate a static method that hangs off the class object.

  class Math {
    class square(n) {
      return n * n;
    }
  }
  
  print Math.square(3); // Prints "9".
  You can solve this however you like, but the “metaclasses” used by Smalltalk and Ruby are a
  particularly elegant approach. Hint: Make LoxClass extend LoxInstance and go from there.
  
- [ ] Most modern languages support “getters” and “setters”—members on a class that look like field 
  reads and writes but that actually execute user-defined code. Extend Lox to support getter
  methods. These are declared without a parameter list. The body of the getter is executed when
  a property with that name is accessed.