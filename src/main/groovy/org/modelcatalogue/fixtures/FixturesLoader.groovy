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
        this.classLoader = Thread.currentThread().getContextClassLoader()
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
            if (fixture.endsWith('*')) {
                File fixturesDir = new File("${fixturesDirectory}/${fixture[0..-2]}")
                if (fixturesDir.exists() && fixturesDir.isDirectory()) {
                    fixturesDir.eachFile {
                        if (!it.isDirectory() && it.name.endsWith('.groovy')) {
                            loadFixuresFromFile(it, shell)
                        }
                    }
                } else {
                    throw new IllegalArgumentException("Directory $fixturesDir.path does not exist")
                }
            } else {
                loadFixuresFromFile(new File("${fixturesDirectory}/${fixture}.groovy"), shell)
            }
        }
        fixtures
    }

    protected loadFixuresFromFile(File fixtureFile, GroovyShell shell) {
        if (!fixtureFile.exists()) {
            throw new IllegalArgumentException("Fixture file $fixtureFile.canonicalPath does not exist!")
        }
        FixturesLoaderScript script = shell.parse(fixtureFile)
        script.setLoader this
        script.run()
        fixtures.putAll(script.fixtures)
    }

    def propertyMissing(String name) { fixtures[name] }

}
