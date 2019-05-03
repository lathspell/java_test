Overview
========

* OAuth 2.0 - process flows for "delegated authorization"
* OpenId Connect - process flows for "federated authentication" based on OAuth 2.0

Terminology
-----------

Using an example case of web shop page that wants to use the identity of a
Single-Sign-On (SSO) server.

* Scope - which permissions should be granted and which denied
* Actors
    * Resource Owner        - the human who's name is used
    * Resource Server       - the database server that stores identities and passwords
    * Client                - the shop page that wants to know the identity of the user
    * Authorization Server  - the SSO engine running OAuth

Clients can be public or confidential. Only the latter can be trusted to store
a secret as they run e.g. on a livingroom TV as opposed to a public client
which might be an app that is distributed through an app store and could be
reverse engineered.






Links
=====

* https://developer.okta.com/blog/2017/06/21/what-the-heck-is-oauth
* https://hackernoon.com/demystifying-oauth-2-0-and-openid-connect-and-saml-12aa4cf9fdba

