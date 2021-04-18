import sys


def calculate_maximum_speedup(n: int, f: float) -> float:
    """
    Utilizes Amdahl's Law to calculate the maximum possible speedup.

    Parameters
    ----------
    n: int
        The number of processors on the specific machine.
    f: float
        The fraction of the program/code/calculation that must be executed serially/sequentially.

    Raises
    ----------
    ValueError
        If f < 0 or f > 1, or if n <= 0.
    """
    if n <= 0:
        raise ValueError("n must be positive!")
    elif (f < 0) or (f > 1):
        raise ValueError("f must be between within the closed unit interval!")

    max_speedup = 1 / (f + ((1 - f) / n))
    return max_speedup


if __name__ == "__main__":
    for n, f in [
        (10, 0.1),
        (100, 0.1),
        (sys.maxsize, 0.1),
        (10, 0.25),
        (100, 0.25),
        (sys.maxsize, 0.25),
    ]:
        print(
            "For n = "
            + str(n)
            + " and f = "
            + str(f)
            + ", the maximum speedup is: "
            + str(calculate_maximum_speedup(n, f))
        )
