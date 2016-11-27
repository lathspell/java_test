Caveat: jsf-api at runtime
--------------------------
Problem:
```
    Caused by: java.util.MissingResourceException: Can't find javax.faces.LogStrings bundle
        at java.util.logging.Logger.setupResourceInfo(Logger.java:1946)
        at java.util.logging.Logger.<init>(Logger.java:380)
        at java.util.logging.LogManager.demandLogger(LogManager.java:554)
        at java.util.logging.Logger.demandLogger(Logger.java:455)
        at java.util.logging.Logger.getLogger(Logger.java:553)
        at javax.faces.FactoryFinder.<clinit>(FactoryFinder.java:334)
        ... 50 more
```

Cause:
The class is in the jsf-api but not in the javaee-api artefact.

Solution:
Add jsf-api as runtime dependency:
```
    <dependency>
        <!-- JSF: Implementation -->
        <groupId>com.sun.faces</groupId>
        <artifactId>jsf-api</artifactId>
        <version>2.2.14</version>
        <scope>runtime</scope>
    </dependency> 
```
