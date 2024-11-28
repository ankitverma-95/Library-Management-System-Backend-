# Library Management System - Security Configuration

The security configuration for the Library Management System backend, detailing how roles and permissions are structured to ensure secure access to APIs.

The backend system includes:
- Secure user registration, login mechanisms with password hashing and session management using JWT.
- Role-based authorization to control access to specific resources based on user roles (e.g., Admin, Librarian, User).
- A flexible and scalable RBAC implementation integrated with middleware for route protection.

---

## Overview

The system uses **Spring Security** to handle authorization and authentication. Access to the system's endpoints is controlled based on user roles. The following roles are defined in the application:

- **ADMIN**: Full access to all endpoints.
- **LIBRARIAN**: Limited access to specific endpoints related to managing authors, books, and borrowed books.
- **PUBLIC**: Access only to public endpoints such as registration and login.

---

## Endpoint Access Permissions

| HTTP Method | Endpoint                        | Role(s) Allowed        | Description                                                    |
|-------------|---------------------------------|------------------------|----------------------------------------------------------------|
| `ANY`       | `/users`                       | `ADMIN`                | Access to manage user-related operations.                     |
| `POST`      | `/author`                      | `ADMIN`                | Create a new author.                                          |
| `POST`      | `/book`                        | `ADMIN`                | Add a new book to the collection.                             |
| `DELETE`    | `/book/*`                      | `ADMIN`                | Delete a specific book by its ID.                             |
| `POST`      | `/librarian`                   | `ADMIN`                | Add a new librarian.                                          |
| `GET`       | `/author`                      | `ADMIN`, `LIBRARIAN`   | Fetch author details.                                         |
| `POST`      | `/book/*/user/*`               | `ADMIN`, `LIBRARIAN`   | Assign a book to a user.                                      |
| `GET`       | `/book/borrowed`               | `ADMIN`, `LIBRARIAN`   | View a list of borrowed books.                                |
| `ANY`       | `/register`, `/login`, `/admin`| Public (No restriction)| Access for user registration, login, and admin-related tasks. |

---

## Role Prefixing

Spring Security automatically prefixes roles with **`ROLE_`**. Ensure that roles are defined correctly in your user management system:

- `ADMIN` becomes `ROLE_ADMIN`
- `LIBRARIAN` becomes `ROLE_LIBRARIAN`

---

## Authentication Flow

1. **Public Endpoints**: Endpoints such as `/register` and `/login` are accessible to all users without authentication.
2. **Restricted Endpoints**: Users must log in and have the appropriate role to access restricted endpoints.
3. **Role Hierarchy**: Higher roles (e.g., `ADMIN`) inherit the permissions of lower roles (e.g., `LIBRARIAN`).

---

## Additional Notes

- PostgreSQL has used for store Information.
- Database configuraton such as username and password are managed in application.properties file.

---

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---
