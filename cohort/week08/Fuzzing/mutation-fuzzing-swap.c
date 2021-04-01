#include <stdlib.h>
#include <assert.h>
#include <time.h>
#include <stdio.h>
#include <string.h>

// Choosing a random position in the input string and swap the adjacent characters
void swap(char *input)
{
    if (strlen(input) < 2) {
        printf("Cannot swap two adjacent characters if the string is too short.\n");
        return;
    }
    int swap_position;

    srand(time(NULL));
    // choose a random position in the input string
    swap_position = rand() % (strlen(input) - 1);

    printf("Swapping character in position %d with character in position %d.....\n", swap_position, swap_position + 1);

    // swap the adjacent characters
    char temp = input[swap_position];
    input[swap_position] = input[swap_position + 1];
    input[swap_position + 1] = temp;

    printf("Mutated input = %s\n", input);
}

int main()
{
    char existing_input[128] = "SUTD";
    char *input;
    int index;

    printf("Original input = %s\n", existing_input);
    swap(existing_input);
}
