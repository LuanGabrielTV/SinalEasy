
import { Signal } from "./Signal";

export class City{
    id: number | undefined;
    name: string | undefined;
    state: string | undefined;
    rating: number | undefined;
    signals: Array<Signal> | undefined;

    constructor(id?: number, name?: string, state?: string, rating?: number, signals?: Array<Signal>){
        this.id = id;
        this.name = name;
        this.state = state;
        this.rating = rating;
        this.signals = signals;
    }
}