int main()
{
    int x[10], i = 0;
    int y;

    /* declare the memory region occupied by 
    * variable "x" to be symbolic */

    /* This means the program will be executed 
    * with uninstantiated, i.e., symbolic 
    * values of x */
    klee_make_symbolic(x, 10 * sizeof(x[0]), "x");

    while (i < 10)
    {
        if (x[i] < 5)
            y++;
        else
            y--;
        i++;
    }
}
