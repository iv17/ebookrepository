export class User {
    firstName: String;
    lastName: String;
    email: String;
    password: String;
    type: String;

    constructor(inter: UserInterface = {}) {
        this.firstName = inter.firstName;
        this.lastName = inter.lastName;
        this.email = inter.email;
        this.password = inter.password;
        this.type = inter.type;
    }
}

interface UserInterface {
    firstName?: String;
    lastName?: String;
    email?: String;
    password?: String;
    type?: String;
}