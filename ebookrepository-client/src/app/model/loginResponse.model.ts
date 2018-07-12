import { User } from "./user";

export class LoginResponse {
    user: User;
    token: String;

    constructor(inter: LoginResponseInterface = {}) {
        this.user = inter.user;
        this.token = inter.token;
    }
}

interface LoginResponseInterface {
    user?: User;
    token?: String;
}