https://deltaspike.apache.org

The Good
--------

* Environments (although not as good cascading as wished)
* Supports .properties
* ConfigSourceFilter (decrypt Passwörds o.ä.)
* CDI provider
* Default values in getProperty()
* Convenience methods like getString() / getBoolean() via as(Integer.class)

Maybe
-----
* CDI provider (couldn't get it to work)
* MergedConfiguration via isOptional()
* FallbackConfiguration via isOptional()

The Bad
-------

* No Java 8
* No Support for .yml
* No good support for specific file system location for config files
* Auto-Reload (or?)
* Big Jar where Config is just one feature among others

* Could not get it working... 
