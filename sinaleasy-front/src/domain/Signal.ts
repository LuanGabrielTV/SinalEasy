import * as L from 'leaflet';
import { Milestone } from './Milestone';
import { Grade } from './Grade';

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
    liked: boolean | undefined;
    signalMilestones: Array<Milestone> | undefined;
    grade: Grade | undefined;
    
    constructor(name?: string, date?: Date, address?: string, description?: string, typeOfSignal?: number, latitude?: number, longitude?: number, scaleFactor?: number, status?: number, numberOfLikes?: number, cityId?: string, signalId?: string, liked?: boolean, signalMilestones?: Array<Milestone>, grade?: Grade) {
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
        this.liked = liked;
        this.signalMilestones = signalMilestones;
        this.grade = grade;
    }

}
