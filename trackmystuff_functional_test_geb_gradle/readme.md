This is an example of incorporating Geb into a Gradle build. It shows the use of JUnit 4 tests.

The build is setup to work with HTMLUnit and FireFox. Have a look at the `build.gradle` and the `src/test/resources/GebConfig.groovy` files.

The following commands will launch the tests with the individual browsers:

    ./gradlew htmlunitTest
    ./gradlew firefoxTest

To run with all, you can run:

    ./gradlew test
