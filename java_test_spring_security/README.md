Spring WebMVC Security
======================

Demos for Spring Security features.

Packages
--------
* test1 - Simple AuthenticationManager with password check and different roles
* test2 - AuthenticationManager with UserDetailsService and PasswordEncoder

Q&A
===

What are the key differences between @PreAuthorize and @Secured?
* @Secured is older
* @Secured does not support SpEL
* @Secured only works for Roles that start with "ROLE_"