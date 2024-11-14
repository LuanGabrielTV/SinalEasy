
import { Signal } from "./Signal";

export class City{
    cityId: number | undefined;
    name: string | undefined;
    state: string | undefined;
    rating: number | undefined;
    signals: Array<Signal> | undefined;

    constructor(cityId?: number, name?: string, state?: string, rating?: number, signals?: Array<Signal>){
        this.cityId = cityId;
        this.name = name;
        this.state = state;
        this.rating = rating;
        this.signals = signals;
    }
}