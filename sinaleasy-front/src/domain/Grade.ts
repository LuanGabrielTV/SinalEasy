export class Grade{
    rating: number | undefined;
    description: string | undefined;
    date: Date | undefined;
    signalId: string | undefined;

    constructor(rating?:number, description?: string, date?: Date, signalId?: string){
        this.rating = rating;
        this.description = description;
        this.date = date;
        this.signalId = signalId;
    }
    
}