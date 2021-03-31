# SUTD 2021 50.003 ESC In-Class Demo Programs

Before running/executing some of these Java source file scripts, please ensure that [Selenium](https://www.selenium.dev/downloads/) (Client & WebDriver Language Bindings for Java), [JUnit](https://junit.org/junit5/) and a corresponding W3C browser WebDriver of your choice are already installed first. If you are using the `.jar` files ([this one](https://search.maven.org/artifact/org.junit.platform/junit-platform-console-standalone) for JUnit 5) instead of using dependency managers such as Gradle or Maven, place them in your directory path of choice and include/indicate the relevant paths in the corresponding Java build classpath. For the WebDriver executable file, you can either pass the file as the `vmargs` properties into the JVM or add its path into your system's `$PATH` environment variable.

These scripts utilize the [GeckoDriver](https://github.com/mozilla/geckodriver) WebDriver.

In terms of the greybox and whitebox fuzzers, EvoSuite can be downloaded and installed from [here](https://www.evosuite.org/downloads/), while KLEE (the symbolic execution engine one) can be downloaded and installed from [here](https://klee.github.io/getting-started/) (more tools need to be installed such as a working LLVM installation, a constraint solver, uClibc and the KLEE POSIX environment runtime model).

> Unfortunately, this KLEE does not come with [explosives that go boom](https://genshin.mihoyo.com/en/character/mondstadt?char=8)... 😣

Alternatively, the Docker images of EvoSuite and KLEE could also be pulled and used by running these commands:

```console
# EvoSuite
$ docker pull evosuite/evosuite:latest-java-8
$ docker run -it -u ${UID} -v ${PWD}:/evosuite --entrypoint=/bin/bash evosuite/evosuite:latest-java-8

# KLEE
$ docker pull klee/klee:latest
$ docker run --rm -it -u ${UID} -v ${PWD}:/klee --ulimit='stack=-1:-1' klee/klee
```

> Do not accidentally overwrite the `/evosuite-bin` and the `/home/klee` directories since currently-existing host volumes/mounts will override the container's linked directories/files, the `UID` and `PWD` POSIX environment variables are optional and interchangeable to specific desired values, and use double quotation marks instead of single quotation marks for certain argument/option/flag values if using Windows (or WSL-related products).

The Z3 Theorem Prover/Constraint Solver can be downloaded, built and installed by following the official instructions [here](https://github.com/Z3Prover/z3). Alternatively, an online editor is available and can be used on the `rise4fun` web service platform [here](https://rise4fun.com/z3).

Z3 can be run by using these commands:

```console
$ z3 -smt2 *.smt2
$ z3 *.z3
```
