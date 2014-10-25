# Arquillian

* Always builds a small .war with only one the classes to test.
* Launches the test.war with either the embedded, managed (started/stopped by Arquillian) oder remote (already running) container
* Supported containers are Glassfish, JBoss, TomEE und more

* Minimum runtime (Maven "Total time")
** Maven alone                          2s
** arquillian-glassfish-remote-3.1      6s
** arquillian-glassfish-embedded-3.1   12s
