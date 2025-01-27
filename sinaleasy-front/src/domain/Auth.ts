import { User } from "./User";

export class Auth{
    login: string | undefined;
    password: string | undefined;

    constructor(user:User){
        this.login = user.login;
        this.password = user.password;
    }
}