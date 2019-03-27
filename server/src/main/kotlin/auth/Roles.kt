package auth

import io.javalin.security.Role

enum class ApiRole : Role { ANYONE, USER, ADMIN, KITCHEN }