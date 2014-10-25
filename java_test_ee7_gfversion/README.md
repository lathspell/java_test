Goal:
    The war file should be deployed using Glassfish version numbers i.e. as
    MyApp-1:419 then MyApp-1:420 etc.

Idea:
    Using maven-scm-plugin to store this version number in the $buildNumber
    version and then the maven-war-plugin filtering capabilities to replace the
    version number in src/main/webapp/WEB-INF/glassfish-web.xml.

Problem:
    Although that works well with maven, NetBeans 8.0 Patch 1.1 suddenly stopps
    "deploy on save".  The problem does not occur when simply using
    version-identified, only together with a variable.
