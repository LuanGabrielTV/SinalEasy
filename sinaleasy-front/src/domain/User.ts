export class User {
    email: string | undefined;
    password: string | undefined;
    idUser: string | undefined;

    constructor(idUser?: string, email?: string, password?: string) {
        this.email = email;
        this.password = password;
        this.idUser = idUser;
    }
}