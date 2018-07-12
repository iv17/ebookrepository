export class LoginRequest {
    email: String;
    password: String;

    constructor(inter: LoginRequestInterface = {}) {
        this.email = inter.email;
        this.password = inter.password;
    }
}

interface LoginRequestInterface {
    email?: String;
    password?: String;
}