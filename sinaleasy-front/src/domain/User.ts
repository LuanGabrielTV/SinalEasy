export class User {
    email: string | undefined;
    password: string | undefined;
    idUser: string | undefined;
    login: string | undefined;

    constructor(email?: string, password?: string, login?: string) {
        this.email = email;
        this.password = password;
        this.login = login;
    }
}