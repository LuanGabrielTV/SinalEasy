export class Like{
    signalId: string | undefined;
    like: boolean | undefined;

    constructor(signalId?: string, like?: boolean){
        this.signalId = signalId;
        this.like = like;
    }
}