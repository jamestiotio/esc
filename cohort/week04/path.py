# This function has 2 paths
def first_main():
    a = int(input())
    x, i = 0, 0

    while i < 100:
        if a < 5:
            x += 1
        else:
            x -= 1

        i += 1


# This function has 2^100 paths
def second_main():
    a = []

    for _ in range(100):
        a.append(int(input()))

    x, i = 0, 0

    while i < 100:
        if a[i] < 5:
            x += 1
        else:
            x -= 1

        i += 1


# This function has infinite paths
def third_main():
    x = int(input())

    while x > 0:
        x -= 1