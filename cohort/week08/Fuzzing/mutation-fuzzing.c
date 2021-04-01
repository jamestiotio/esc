#include <stdlib.h>
#include <assert.h>
#include <time.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>

// Choosing a random position in the input string and swap the adjacent characters
char* swap(char *input)
{
    if (strlen(input) < 2) {
        printf("Cannot swap two adjacent characters if the string is too short.\n");
        return input;
    }

    int swap_position;

    // choose a random position in the input string
    swap_position = rand() % (strlen(input) - 1);

    printf("Swapping character in position %d with character in position %d.....\n", swap_position, swap_position + 1);

    // swap the adjacent characters
    char temp = input[swap_position];
    input[swap_position] = input[swap_position + 1];
    input[swap_position + 1] = temp;

    return input;
}

// Flipping a random bit of a random character in the input string
char* flip(char *input)
{
    if (strlen(input) < 1) {
        printf("Cannot bit flip if the string is too short.\n");
        return input;
    }

    int flip_position, flip_bit;

    // choose a random position in the input string
    flip_position = rand() % (strlen(input) - 1);

    // choose a random bit in the respective character, why it is 7 and not 8?
    flip_bit = rand() % 7;

    printf("Flipping bit %d in position %d.....\n", flip_bit, flip_position);

    // flip the bit in-place
    input[flip_position] = input[flip_position] ^ (1 << flip_bit);

    return input;
}

// Trimming an input string at a randomly chosen position
char* trim(char *input)
{
    if (strlen(input) < 2) {
        printf("Cannot trim if the string is too short.\n");
        return input;
    }

    int trim_position = 0;
    char *mutated_input;

    trim_position = rand() % (strlen(input) - 1);
    printf("Trimming at position %d.....\n", trim_position);

    mutated_input = (char *)malloc((trim_position + 1) * sizeof(char));
    strncpy(mutated_input, input, trim_position);
    mutated_input[trim_position] = '\0';

    return mutated_input;
}

int main()
{
    FILE *fp = fopen("generalized-fuzzer-input.txt", "r");
    FILE *fc = fopen("generalized-fuzzer-output.txt", "w");
    if (fp == NULL || fc == NULL) exit(EXIT_FAILURE);

    char *input = NULL;
    size_t length = 0;
    ssize_t read;

    srand(time(NULL));

    typedef char* (*f)(char*);

    // More mutation operators can simply be added to this array
    f operators[3] = {&swap, &flip, &trim};

    while ((read = getline(&input, &length, fp)) != -1) {        
        int rng = rand() % (sizeof operators / sizeof operators[0]);

        fprintf(fc, "%s", operators[rng](input));
    }

    fclose(fp);
    fclose(fc);
    if (input) free(input);
}
