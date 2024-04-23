[ ] Our scanner here, like most, discards comments and whitespace since those aren’t 
    needed by the parser. Why might you want to write a scanner that does not discard 
    those? What would it be useful for?
[X] Add support to Lox’s scanner for C-style /* ... */ block comments. Make sure
    to handle newlines in them. Consider allowing them to nest. Is adding support for
    nesting more work than you expected? Why
[ ] In C, a block is a statement form that allows you to pack a series of statements 
    where a single one is expected. The comma operator is an analogous syntax for
    expressions. A comma-separated series of expressions can be given where a single
    expression is expected (except inside a function call’s argument list). At runtime,
    the comma operator evaluates the le! operand and discards the result. Then it
    evaluates and returns the right operand.
    Add support for comma expressions. Give them the same precedence and 
    associativity as in C. Write the grammar, and then implement the necessary parsing
    code.
[ ] Likewise, add support for the C-style conditional or “ternary” operator ?:. What
    precedence level is allowed between the ? and :? Is the whole operator le!- 
    associative or right-associative?
[ ] Add error productions to handle each binary operator appearing without a le!- 
    hand operand. In other words, detect a binary operator appearing at the beginning
    of an expression. Report that as an error, but also parse and discard a right-hand 
    operand with the appropriate precedence