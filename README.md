Simple Fixtures
-------------------------------

Simple test data not only for Grails project.

This is lightweight version of [Grails Fixtures Plugin](http://grails.org/plugin/fixtures) but this one can easily run in unit tests not only the integration tests.

Create Groovy scripts in some folder in your project such as `src/test/fixtures`. Define your fixtures in `fixture` as follows:

```
// src/test/fixtures/fixtureTwo.groovy
import com.exaple.MyPogo

load "path/to/fixtureOne" // loads other fixture such as fixtureOne

fixture {
    fixtureTwo(MyPogo, name: "John Smith", nickname: "Fixture", something: fixtureOne)
}
```

The name of the method denotes the name of the fixture, the first argument is the class of the fixture followed by the arguments for the map constructor.

You can use `load(paths...)` method to load aditional fixutres if you need.

Then in your tests create new `FixtureLoader` with given path and optional class loader to fixtures root, load fixtures you want and access them as properties of loader:

```
FixturesLoader loader = new FixturesLoader("src/main/fixtures")
loader.load 'fixtureTwo'
assert loader.fixtureTwo.name == "John Smith"
```
