# Release/Dev Enforcer Rule

The `releasedev-enforcer-rule` is a [(custom) enforcer rule]
(https://maven.apache.org/enforcer/enforcer-api/writing-a-custom-rule.html) to check that at least one of the profiles
`release` and `dev` is activated.

[Here](http://stackoverflow.com/questions/24855678/enforce-exactly-one-of-two-maven-profiles) is a discussion related to
the origin of this enforcer rule.

Here is a usage example of this enforcer rule:

    <plugin>
        <artifactId>maven-enforcer-plugin</artifactId>
        <executions>
            <execution>
                <id>enforce-contour-war</id>
                <goals>
                    <goal>enforce</goal>
                </goals>
                <configuration>
                    <rules>
                        <releaseDevEnforcerRule implementation="com.jamasoftware.maven.enforcer.releasedev.ReleaseDevEnforcerRule" />
                    </rules>
                </configuration>
            </execution>
        </executions>
        <dependencies>
            <dependency>
                <groupId>com.jamasoftware.maven.enforcer</groupId>
                <artifactId>releasedev-enforcer-rule</artifactId>
                <version>...version...</version>
            </dependency>
        </dependencies>
    </plugin>

For example, given the following command-line: `mvn ... -Prelease,dev`, you may expect the following output:

    [main] [INFO] --- maven-enforcer-plugin:...version...:enforce (...) @ ... ---
    [main] [WARNING] Rule 1: com.jamasoftware.maven.enforcer.releasedev.ReleaseDevEnforcerRule failed with message:
    Module requires profile "release" XOR "dev"; "release" profile activated; "dev" profile activated
    ...
    [main] [INFO] BUILD FAILURE
    ...

## License

This project is licensed under [the Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0.txt).

## About Jama

Jama is software for better, faster requirements definition, management, verification and validation, from inception to
production. Our vision is of Modern Product Delivery. Building new products shouldn’t be frustrating and wasteful. It
ought to be enlightening and profitable. We make possible the impossible products of the future. Find more information
on [our web site](http://www.jamasoftware.com/). Jama Software is a fast-growing company, and we are [hiring]
(http://www.jamasoftware.com/company/careers/).
