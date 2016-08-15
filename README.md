# It creates random values for use in automated tests!

There are two situations I commonly encounter. To illustrate both, let's use the trivial example of multiplying two
numbers. I might write a test like this:

```java
@Test
public void productOfTwoNumbers() {
    int operandOne = 1;
    int operandTwo = 2;
    int actual = new Multiplier().multiply(operandOne, operandTwo);
    int expected = operandOne * operandTwo;
    Assert.assertEquals(expected, actual);
}
```

I could implement the `multiply` method in the logical way and the test will pass, but how do I know that it will pass
for other argument values? Especially if I want to keep my test ignorant of the implementation, which I always prefer to
do. Short of putting my test in a pair of loops that exhaustively tests all possible inputs - which has already become
impractical - there is no way to verify this.

## Keeping your programming pair honest

The second situation sometimes crops up when pair programming with someone who follows the rule "do the simplest thing
possible to make the tests pass" a little too literally. For the example of the multiplier, my partner could write some
code that would make the test pass like this:

```java
public class Multiplier {

    public int multiply(int arg1, int arg2) {
        return 2;
    }
}
```

If your partner does that once, then fine. So you might write a second test to tease out some actual functionality:

```java
@Test
public void productOfTwoNumbers() {
    int operandOne = 1;
    int operandTwo = 2;
    int actual = new Multiplier().multiply(operandOne, operandTwo);
    int expected = operandOne * operandTwo;
    Assert.assertEquals(expected, actual);
}

@Test
public void productOfTwoOtherNumbers() {
    int operandOne = 3;
    int operandTwo = 4;
    int actual = new Multiplier().multiply(operandOne, operandTwo);
    int expected = operandOne * operandTwo;
    Assert.assertEquals(expected, actual);
}
```

Now if your partner is a smartass, they might amend the implementation like this:

```java
public class Multiplier {

    public int multiply(int arg1, int arg2) {
        if (arg1 == 3)
            return 12;
        return 2;
    }
}
```

The tests will pass but this is clearly getting us nowhere.

## What if my partner could not predict the input values?

Enter the randomizer.

```java
@Test
public void productOfTwoNumbers() {
    int operandOne = Randomizer.forType(Integer.class).value();
    int operandTwo = Randomizer.forType(Integer.class).value();
    int actual = new Multiplier().multiply(operandOne, operandTwo);
    int expected = operandOne * operandTwo;
    Assert.assertEquals(expected, actual);
}
```

Now they have no choice but to do a proper implementation! And this also helps ameliorate the first situation, because
although my tests still cannot cover the whole of the possible inputs, at least it will run with different values every
time. If your implementation is susceptible to failure because of pathological inputs, this gives you a better chance
over time of finding them.

### Cool. Does it work for strings too?

Of course. You can probably already figure out how to do it:

```java
String random = Randomizer.forType(String.class).value();
```

### I might want to specify a particular length of string.

That's very likely. So you can do this:

```java
String random = Randomizer.forType(String.class).length(10).value(); // gives a random string of 10 characters
```

### What about maximum and minimum values?

Naturally. This will give a random integer between 100 and 200 (inclusive):

```java
int random = Randomizer.forType(Integer.class).min(100).max(200).value();
```

The minimum and maximum values can both take positive and negative values, so both of these are valid:

```java
int random1 = Randomizer.forType(Integer.class).min(-100).max(100).value();
int random2 = Randomizer.forType(Integer.class).min(-2000).max(-1000).value();
```

### Dates would be useful.

I thought so too, so you can also request random dates, and specify minimum and maximum values:

```java
Date thePresent = new Date();
Date anyRandomDate = Randomizer.forType(Date.class).value();
Date randomDateInTheFuture = Randomizer.forType(Date.class).min(thePresent).value();
Date randomDateInThePast = Randomizer.forType(Date.class).max(thePresent).value();
```

Of course you can specify min _and_ max to generate a random date within a range.

### I wish it supported \<your type here\>!

Yeah, sorry, I only covered Strings, Dates and integers so far. But if you want it to support a different type, feel
free to implement it yourself and raise a pull request. What you'll need to do is create a new subtype of
`Randomizer` in `com.github.richardjwild.randomizer.types` and implement the `value` method (plus any of the other
methods you want to support, such as `min`, `max` and `length`. Then just hook it into `RandomizerFactory` and you're
good to go!

## Backlog

These are the things that are done and waiting to be done:

- [x] Create the randomizer
- [x] Implement for integers
- [x] Add min and max feature
- [x] Implement for Strings
- [x] Add length feature
- [x] Implement for Dates
- [ ] Implement for other primitive types
- [ ] Set up build script to deploy artifacts for Maven, Gradle, etc.
