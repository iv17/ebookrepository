import { Category } from "./category";

export class User {
    id: number;
    firstName: String;
    lastName: String;
    email: String;
    password: String;
    type: String;
    categoryName: String;

    constructor(inter: UserInterface = {}) {
        this.id = inter.id;
        this.firstName = inter.firstName;
        this.lastName = inter.lastName;
        this.email = inter.email;
        this.password = inter.password;
        this.type = inter.type;
        this.categoryName = inter.categoryName;
    }
}

interface UserInterface {
    id?: number;
    firstName?: String;
    lastName?: String;
    email?: String;
    password?: String;
    type?: String;
    categoryName?: String;
}