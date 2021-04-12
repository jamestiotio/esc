import secrets

# Use a global limit to prevent an infinite loop and expanding forever
GLOBAL_LIMIT, CURRENT_EXPANSION_LENGTH = 1000, 0


def fuzz_expr():
    global GLOBAL_LIMIT, CURRENT_EXPANSION_LENGTH
    if CURRENT_EXPANSION_LENGTH < GLOBAL_LIMIT:
        return secrets.choice(["Expr + Term", "Expr - Term", "Term"])
    else:
        return "Term"


def fuzz_term():
    global GLOBAL_LIMIT, CURRENT_EXPANSION_LENGTH
    if CURRENT_EXPANSION_LENGTH < GLOBAL_LIMIT:
        return secrets.choice(["Term * Factor", "Term / Factor", "Factor"])
    else:
        return "Factor"


def fuzz_factor():
    global GLOBAL_LIMIT, CURRENT_EXPANSION_LENGTH
    if CURRENT_EXPANSION_LENGTH < GLOBAL_LIMIT:
        return secrets.choice(["(-Integer)", "(Expr)", "Integer", "Integer.Integer"])
    else:
        return "Integer"


def fuzz_integer():
    global GLOBAL_LIMIT, CURRENT_EXPANSION_LENGTH
    if CURRENT_EXPANSION_LENGTH < GLOBAL_LIMIT:
        return secrets.choice(["Digit", "IntegerDigit"])
    else:
        return "Digit"


def fuzz_digit():
    # Calculators should be able to handle redundant leading prefix zeros
    return str(secrets.choice([i for i in range(0, 10)]))


# Do iteration instead of recursion (attempting to avoid recursion so as to prevent exceeding the maximum recursion depth allowable)
def fuzzer():
    global GLOBAL_LIMIT, CURRENT_EXPANSION_LENGTH
    S = "Expr"
    while "Expr" in S:
        S = S.replace("Expr", fuzz_expr(), 1)
        CURRENT_EXPANSION_LENGTH += 1

        while "Term" in S:
            S = S.replace("Term", fuzz_term(), 1)
            CURRENT_EXPANSION_LENGTH += 1

        while "Factor" in S:
            S = S.replace("Factor", fuzz_factor(), 1)
            CURRENT_EXPANSION_LENGTH += 1

    while "Integer" in S:
        S = S.replace("Integer", fuzz_integer(), 1)
        CURRENT_EXPANSION_LENGTH += 1

    while "Digit" in S:
        S = S.replace("Digit", fuzz_digit(), 1)

    return S


if __name__ == "__main__":
    print(fuzzer())