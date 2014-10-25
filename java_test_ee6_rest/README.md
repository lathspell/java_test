
## Grizzly2

Is a small webserver which is also used internaly by Glassfish.
Everything necessary for REST seems to be available automatically when using
the Jersey Test Framework. The method calls are actually using HTTP
layer so problems with JSON encoding/decoding or HTTP status can be detected.

    ## T 127.0.0.1:55769 -> 127.0.0.1:9998 [AP]
    GET /book/getAuthor HTTP/1.1..User-Agent: Jersey/2.4 (HttpUrlConnection 1.7.0_03)..Host: localhost:9998..Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2..Connection: keep-alive....

    T 127.0.0.1:9998 -> 127.0.0.1:55769 [AP]
    HTTP/1.1 200 OK..Content-Type: application/json..Date: Mon, 04 Nov 2013 23:46:16 GMT..Content-Length: 11....Jules Verne
