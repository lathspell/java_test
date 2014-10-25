
# Application uses JUL, Glassfish uses JUL

    + Java EE compliant
    + Application does not contain logging implementation/bridge
    + Log levels configuratble via Glassfish web interface
    - Single logfile only
    + 3rd party libraries which uses JUL also log to common logfile(s)

# Application uses SLF4J, Glassfish uses JUL

The slf4j-jdk14 Maven module can be used to write against the SLF4J API and
still have the logs written by Glassfish to the usual server.log using
java.util.logging.

## Application contains slf4j-jdk14

    + Java EE6 compliant
    - Application contains logging implementation/bridge (prone to circular routing)
    + Log levels configurable via Glassfish web interface
    - Single log file only
    + 3rd party libraries that use JUL still also uses common logfile(s)

## Application contains slf4j-api, Glassfish contains slf4j-jdk14

    - Not Java EE6 compliant because it needs Appserver with slf4j bridge
    + Application does not contain logging implementation/bridge
    + Log levels configurable via Glassfish web interface
    - Single log file only
    + 3rd party libraries that use JUL still also uses common logfile(s)

## Application contains slf4j-api, Glassfish contains logback and jul-to-slf4j

This one might be tricky to setup.

    - Not Java EE6 compliant because it needs Appserver with slf4j implementation
    + Application does not contain logging implementation/bridge
    - Log levels not configurable via Glassfish web interface
    + Separate logfiles
    + 3rd party libraries that use JUL still also uses common logfile(s)

## Application contains logback

The app uses its own logback.xml. If a console logger is used the output
even appears in the Netbeans Glassfish output window.

    + Java EE6 compliant
    - Application contains logging implementation
    - Log levels not configurable via Glassfish web interface
    + Separate logfiles
    - 3rd party libraries that use JUL still log to the Glassfish server.log

## Application contains logback and jul-to-slf4j

It seems that this solution crashed the domain every now and then!
Probably due to some circular log routing.

    + Java EE6 compliant
    - Application contains logging implementation and brige
    - Log levels not configurable via Glassfish web interface
    + Separate logfiles
    - 3rd party libraries that use JUL still also log to separate logfiles

See also:
* http://hwellmann.blogspot.de/2010/12/glassfish-logging-with-slf4j-part-2.html
* http://www.ronlievens.org/tutorials/integrate-slf4j-and-logback-with-oracle-glassfish-v3-applications

