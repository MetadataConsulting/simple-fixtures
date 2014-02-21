package org.modelcatalogue.fixtures

import org.codehaus.groovy.control.CompilerConfiguration

/**
 * This mock fixture loader apart from the plugin one works in the unit test.
 * At the moment it doesn't handle wildcards in the fixture paths.
 */
class FixturesLoader {

    final String fixturesDirectory
    final ClassLoader classLoader

    Map<String, Object> fixtures = [:]

    FixturesLoader(String fixturesDirectory) {
        this.fixturesDirectory = fixturesDirectory
        this.classLoader = getClass().getClassLoader()
    }

    FixturesLoader(String fixturesDirectory, ClassLoader classLoader) {
        this.fixturesDirectory = fixturesDirectory
        this.classLoader = classLoader
    }

    Map<String, Object> load(String... fixturesPaths) {
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration()
        compilerConfiguration.scriptBaseClass = FixturesLoaderScript.name

        GroovyShell shell = new GroovyShell(classLoader, new Binding(), compilerConfiguration)

        for (String fixture in fixturesPaths) {
            File fixtureFile = new File("${fixturesDirectory}/${fixture}.groovy")
            if (!fixtureFile.exists()) {
                throw new IllegalArgumentException("Fixture file $fixtureFile.canonicalPath does not exist!")
            }
            FixturesLoaderScript script = shell.parse(fixtureFile)
            script.setLoader this
            script.run()
            fixtures.putAll(script.fixtures)
        }
        fixtures
    }

    def propertyMissing(String name) { fixtures[name] }

}
