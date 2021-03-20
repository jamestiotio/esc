int main()
{
    // "x" does not have any concrete values here (i.e., "x" is just a symbol)
    int x, i = 0;
    int y;

    /* declare the memory region occupied by 
    * variable "x" to be symbolic */

    /* This means the program will be executed 
    * with uninstantiated, i.e., symbolic 
    * values of x */
    klee_make_symbolic(&x, sizeof(x), "x");

    while (i < 10)
    {
        if (x < 5)
            y++;
        else
            y--;
        i++;
    }
}
