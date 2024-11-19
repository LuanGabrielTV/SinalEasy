import * as L from 'leaflet';

export class Signal {
    signalId: string | undefined;
    name: string | undefined;
    date: Date | undefined;
    address: string | undefined;
    description: string | undefined;
    typeOfSignal: number | undefined;
    latitude: number | undefined;
    longitude: number | undefined;
    scaleFactor: number | undefined;
    status: number | undefined;
    numberOfLikes: number | undefined;
    cityId: string | undefined;
    
    constructor(name?: string, date?: Date, address?: string, description?: string, typeOfSignal?: number, latitude?: number, longitude?: number, scaleFactor?: number, status?: number, numberOfLikes?: number, cityId?: string, signalId?: string) {
        this.name = name;
        this.date = date;
        this.address = address;
        this.description = description;
        this.typeOfSignal = typeOfSignal;
        this.latitude = latitude;
        this.longitude = longitude;
        this.scaleFactor = scaleFactor;
        this.status = status;
        this.numberOfLikes = numberOfLikes;
        this.cityId = cityId;
        this.signalId = signalId;
    }

}
