#include <klee/klee.h>

int main()
{
    int x;

    /* declare the memory region occupied by 
    * variable "x" to be symbolic */

    /* This means the program will be executed 
    * with uninstantiated, i.e., symbolic 
    * values of x */
    klee_make_symbolic(&x, sizeof(x), "x");

    /* do some computations */
    if (x < 10)
    {
        x = x + 20;
        if (x < 5)
        {
            x = -x * 10;
            if (x > 20)
            {
                x = x + 4;
            }
            else
            {
                x = -x * 4;
            }
        }
        else
        {
            x = x + 90;
            if (x < 100)
            {
                x = x - 1;
            }
            else
            {
                x = x % 2;
            }
        }
    }
    else
    {
        x = x + 7;
        if (x < 50)
        {
            x = x * 2;
            if (x > 40)
            {
                x = x % 3;
            }
            else
            {
                x = -x + 2;
            }
        }
        else
        {
            x = x * x;
            if (x % 2 == 1)
            {
                x = x + 5;
            }
            else
            {
                x = x * 3;
            }
        }
    }
}