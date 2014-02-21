package org.modelcatalogue.fixtures

import spock.lang.Specification

/**
 * Created by ladin on 17.02.14.
 */
class FixturesLoaderScriptSpec extends Specification {


    def "Script collects fixtures in named map"() {
        MockScript script = new MockScript()
        script.setLoader(new FixturesLoader("src/test/resources/fixtures"))
        script.run()

        expect:
        script.fixtures
        script.fixtures.DT_double
        script.fixtures.DT_double instanceof DataType
        script.fixtures.DT_double.name == 'double'
        script.fixtures.DT_string
        script.fixtures.DT_string instanceof DataType
        script.fixtures.DT_string.name == 'string'

    }

}

class MockScript extends FixturesLoaderScript {

    Object run() {

        load "dataTypes/DT_double"

        fixture {
            DT_string(DataType, name: 'string')
            println DT_double
        }
    }
}
