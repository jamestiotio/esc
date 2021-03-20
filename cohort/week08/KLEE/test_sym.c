int main()
{
    // "x" does not have any concrete values here (i.e., "x" is just a symbol)
    int x, i = 0;
    int y, z, m, n;

    /* declare the memory region occupied by 
    * variable "x" to be symbolic */

    /* This means the program will be executed 
    * with uninstantiated, i.e., symbolic 
    * values of x */
    klee_make_symbolic(&x, sizeof(x), "x");

    /* same for variables y and z */
    klee_make_symbolic(&y, sizeof(y), "y");
    klee_make_symbolic(&z, sizeof(z), "z");

    // Simple linear integer arithmetic can be efficiently computed by the SMT solver (same for bit vectors and string)
    // Might not be particularly scalable or efficient for certain other theories
    m = x + 1;
    n = 2 * m + 5;

    /* do some computations */
    if (n < 10)
    {
        n++;
    }
    else if (n >= 100)
    {
        n -= 100;
    }

    /* do some computations */
    if (n < 1)
    {
        n += 10;
    }
    else if (n >= 10)
    {
        n -= 10;
    }
}
