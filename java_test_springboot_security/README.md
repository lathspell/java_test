SpringBoot Security Demo
========================

Start at http://localhost:8080/

* Three different builtin users, "admin", "user" and "machine" (password equals username)
* Three roles: "ADMIN", "USER" and "MACHINE" where user "admin" has them all
* Three namespaces with some exceptions, mostly secured by MVC-Filters in WebSecurityConfig
```
    /admin/             needs ADMIN
    /admin/test1            "
    /user/              needs USER
    /user/test1             "
    /user/test2         !!! has preAuthenticate("hasRole('ADMIN')") !!!
    /rest/              needs MACHINE
    /rest/add?a=1&b=2       "
```