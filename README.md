# Ako LMS

The environment:

- IDE: Eclipse Oxygen latest version with JDK 8 from [https://www.eclipse.org/downloads/](https://www.eclipse.org/downloads/)
- Optional Eclipse extension: Spring Suit
- Database: Mariadb from [https://downloads.mariadb.org/](https://downloads.mariadb.org/)
- Mariadb client (not available for MacOS): heidisql from [https://www.heidisql.com/download.php](https://www.heidisql.com/download.php)

Getting started:

1. Create a database using the ako\_db.sql script from the root folder (can use heidisql or command line)
2. Clone the git repo: https://github.com/nburuca/ako
3. Launch eclipse and select open project, browse the
4. From the context menu select run as Java Application or Spring Boot App (only available with Spring Suit extension)
5. Goto browser and hit localhost:8080
6. #5 should result in a login page
7. Till we have a user profile page, use one of the following user credentials with MFA = false
8. After login authentication and authorization success message should be displayed

Default users:

| Username/email | password | MFA Active |
| --- | --- | --- |
| nburuca@terpmail.umd.edu | password | true |
| rdalal@terpmail.umd.edu | password | true |
| tphillips@terpmail.umd.edu | password | false |
| vsadhwani@terpmail.umd.edu | password | false |
| prathod@terpmail.umd.edu | password | true |

How to debug:

- You can use standard eclipse debug feature
- For client code, use chrome&#39;s developer tools

Working with GIT:

- You should fork the origin repo and create a branch in your forked repo.
- Make all the changes to the branch from the forked repo and commit/push changes.
- Create a pull request and issue a merge with the original repo(upstream)

Coding client:

1. Create your components (refer to LoginComponent)
2. Include it in index.html
3. Update app.route.js
4. Test if everything works

Note:

- Ideal setup environment would also include a separate client-side editor (Brackets would be my first choice, because it comes with live preview feature)
- The API also works with cross-origin requests, this can be removed when we are going live.
