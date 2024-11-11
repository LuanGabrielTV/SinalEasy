import { Signal } from "./Signal";

export class City{
    id: number | undefined;
    name: string | undefined;
    state: string | undefined;
    rating: number | undefined;
    signals: Array<Signal> | undefined;
}