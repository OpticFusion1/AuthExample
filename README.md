# AuthExample
Example Sping Boot program which uses JWT

Currently available endpoints:<br>
/api/v1/login:<br>
Request Type: POST<br>
Body: JSON w/ the format {"username":"username","password":"password"}<br>
Returns JWT in format {"jwt":"token_string"}, otherwise FORBIDDEN<br>

/api/v1/register:<br>
Request Type: POST<br>
Body: JSON w/ the format {"username":"username","password":"password"}<br>
Returns OK if account created, otherwise FORBIDDEN<br>

/api/v1/forgotpassword (incomplete)<br>
/api/v1/sessions/renew (unimplemented)<br>
/api/v1/sessions/active (unimplemented)<br>
/api/v1/sessions/revoke (unimplemented)<br>

For this example the tokens are generated with the following params:<br>
IssuedAt -> Current time truncated to ChronoUnit.SECONDS<br>
Expiration -> 3 minutes after IssuedAt<br>
JWTId -> Random UUID which is used to ensure that a token has not been replayed or reused<br>
Signed with HMAC256 SECRET_KEY<br>

