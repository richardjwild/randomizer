# It creates random values for use in automated tests!

Imagine you're pair programming and practicing TDD with someone who follows the rule _do the simplest thing possible to
make the tests pass_ a little too literally. It might be okay to make the very first test pass by returning a literal,
but if you make the next test pass the same way then you're not making progress. However, if your partner could not
predict the input data they would be forced to evolve the code to make it more general, and this is what you want. Enter
the randomizer.

To add it to your project, these are the Maven details:
 
```xml
<dependency>
    <groupId>com.github.richardjwild</groupId>
    <artifactId>randomizer</artifactId>
    <version>0.3.0</version>
</dependency>
``` 

Or if you're using Gradle, put this in your `build.gradle` script:

```groovy
repositories {
    mavenCentral()
}

dependencies {
    testCompile 'com.github.richardjwild:randomizer:0.3.0'
}
```

To use it, simply do:

```java
import com.github.richardjwild.randomizer.Randomizer;

public class RandomizerTest {

    public static void main(String[] args) {
        int randomInt = Randomizer.forType(Integer.class).value();
    }
}
```

### What about maximum and minimum values?

This will give a random integer between 100 and 200 (inclusive):

```java
Randomizer.forType(Integer.class).min(100).max(200).value();
```

The minimum and maximum values can both take positive and negative values, so both of these are valid:

```java
Randomizer.forType(Integer.class).min(-100).max(100).value();
Randomizer.forType(Integer.class).min(-2000).max(-1000).value();
```

### Cool. Does it work for strings too?

Of course. In this case the length must be specified, otherwise an IllegalArgumentException will be thrown:

```java
Randomizer.forType(String.class).length(10).value();
```

You can also specify a pattern for the generated string, using a small subset of the regular expression syntax:

```java
Randomizer.forType(String.class).pattern("[a-zA-Z]{5,10}[0-9]{2}@[a-z]{5,10}.com").value();
```

That will return between five and ten random letters (upper and lower case) then two random digits, followed by an @ 
symbol, then between five and ten random lowercase letters, finishing with ".com" - in other words, a random string that
resembles an email address.

### Dates would be useful.

I thought so too, so you can also request random dates, and specify minimum and maximum values:

```java
Date thePresent = new Date();
Date anyRandomDate = Randomizer.forType(Date.class).value();
Date randomDateInTheFuture = Randomizer.forType(Date.class).min(thePresent).value();
Date randomDateInThePast = Randomizer.forType(Date.class).max(thePresent).value();
```

You can specify both min _and_ max to generate a random date within a range.

### What types are supported?

All the primitives, Strings, Dates and BigDecimals so far. This table shows the constraint specifiers that are valid for
each type:

| Type       | min | max | length | maxLength | minLength | maxChar | minChar | scale | pattern |
| ---------- | --- | --- | ------ | --------- | --------- | ------- | ------- | ----- | ------- |
| Integer    | yes | yes | no     | no        | no        | no      | no      | no    | no      |
| Long       | yes | yes | no     | no        | no        | no      | no      | no    | no      |
| Float      | yes | yes | no     | no        | no        | no      | no      | no    | no      |
| Double     | yes | yes | no     | no        | no        | no      | no      | no    | no      |
| Character  | yes | yes | no     | no        | no        | no      | no      | no    | no      |
| Boolean    | no  | no  | no     | no        | no        | no      | no      | no    | no      |
| Date       | yes | yes | no     | no        | no        | no      | no      | no    | no      |
| String     | no  | no  | yes    | yes       | yes       | yes     | yes     | no    | yes     |
| BigDecimal | yes | yes | no     | no        | no        | no      | no      | yes   | no      |

### I want it to support another type. How can I implement this?

If you want the randomizer to support a different type, please feel free to implement it yourself and raise a pull
request. What you'll need to do is create a new subtype of `Randomizer` in `com.github.richardjwild.randomizer.types`
and implement the `value` method (plus any of the other methods you want to support, such as `min`, `max` and `length`.
Then just hook it into `RandomizerFactory` and you're good to go.
