package org.modelcatalogue.fixtures

import spock.lang.Specification

class FixturesLoaderSpec extends Specification {

    def "mock fixtures loader loads fixture"() {
        FixturesLoader fixturesLoader = new FixturesLoader("src/test/resources/fixtures")

        when:
        fixturesLoader.load("dataTypes/DT_double")
        fixturesLoader.load("dataTypes/DT_string")

        then:
        fixturesLoader.DT_double
        fixturesLoader.DT_double.name == 'double'
        fixturesLoader.DT_string
        fixturesLoader.DT_string.name == 'string'
    }

}
