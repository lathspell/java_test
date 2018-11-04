<html>
<body>

<h1>Example Spring WebMVC Security Application</h1>
<p>
    This is the unsecured index.jsp.
</p>

<h3>Web</h3>
<p>Secured by using HttpSecurity in WebSecurityConfig.</p>
<ul>
    <li><a href="all/">all/</a></li>
    <li><a href="admin/">admin/</a></li>
    <li><a href="user/">user/</a></li>
</ul>

<h3>REST</h3>
<p>Could also be secured by using HttpSecurity in WebSecurityConfig but is actually secured with @PreAuthorize
    on method level.</p>
<ul>
    <li><a href="rest/admin">rest/admin</a></li>
    <li><a href="rest/user">rest/user</a></li>
    <li><a href="rest/all">rest/all</a></li>
</ul>

<h3>Logout</h3>
<p>
    <a href="/logout">/logout</a>
</p>

</body>
</html>
