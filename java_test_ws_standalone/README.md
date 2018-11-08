Start:

  mvn compile
   
  [first terminal]
    mvn exec:java -Dexec.mainClass=de.lathspell.test.ws_standalone.server.Server
  
  [second terminal]
    exec:java -Dexec.mainClass=de.lathspell.test.ws_standalone.client.Client
