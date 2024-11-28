## Welcome to Hub Products Management app

- You can run this app on docker using `docker compose up --build` exposing the app at http://localhost:8080/
- Or you can run it locally from your IDE 
- Run `mvn test -Dtest=HubControllerTest` in order to run unit test

This app is exposing a rest api which is protected with a basic authentication form
implemented with spring security. It has implemented 3 roles which are distributed between
3 users saved in memory:
### Roles:
- SUPER_ADMIN role can access all endpoints;
- ADMIN can access all endpoints except `/change-price` and `/delete-product/**`;
- USER can just query product data.

Access app by these user/pass:
- superAdmin/superAdmin
- admin/admin
- user/user


