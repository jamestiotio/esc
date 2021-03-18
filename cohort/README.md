# SUTD 2021 50.003 ESC In-Class Demo Programs

Before running/executing some of these Java source file scripts, please ensure that [Selenium](https://www.selenium.dev/downloads/) (Client & WebDriver Language Bindings for Java), [JUnit](https://junit.org/junit5/) and a corresponding W3C browser WebDriver of your choice are already installed first. If you are using the `.jar` files ([this one](https://search.maven.org/artifact/org.junit.platform/junit-platform-console-standalone) for JUnit 5) instead of using dependency managers such as Gradle or Maven, place them in your directory path of choice and include/indicate the relevant paths in the corresponding Java build classpath. For the WebDriver executable file, you can either pass the file as the `vmargs` properties into the JVM or add its path into your system's `$PATH` environment variable.

These scripts utilize the [GeckoDriver](https://github.com/mozilla/geckodriver) WebDriver.

In terms of the greybox and whitebox fuzzers, EvoSuite can be downloaded and installed from [here](https://www.evosuite.org/downloads/), while KLEE can be downloaded and installed from [here](https://klee.github.io/getting-started/).

> Unfortunately, this KLEE does not come with [explosives that go boom](https://genshin.mihoyo.com/en/character/mondstadt?char=8)... 😣
