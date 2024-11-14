import { Component, OnInit } from '@angular/core';
import { AutoCompleteCompleteEvent, AutoCompleteModule } from 'primeng/autocomplete';
import { City } from '../domain/City';
import { State } from '../domain/State';
import * as L from 'leaflet';
import { AddressService } from '../services/address.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { RatingModule } from 'primeng/rating';
import { Signal } from '../domain/Signal';
import { SignalType } from '../domain/SignalType';
import { Status } from '../domain/Status';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, FormsModule, AutoCompleteModule, ButtonModule, RatingModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {
  city: City | undefined;
  state: State | undefined;
  states: State[] | undefined;
  cities: City[] | undefined;
  filteredCities: City[] = [];
  filteredStates: State[] = [];
  private map!: L.Map;
  selected = false;
  rating: number | undefined;
  signals: Signal[];
  signalType = SignalType;
  status = Status;
  markers: L.CircleMarker[];
  selectedIndex: number | undefined;

  constructor(private addressService: AddressService) {
    this.signals = [];
    this.markers = [];
  }

  ngOnInit(): void {
    this.states = [];
    this.cities = [];
    this.loadStates();
  }
  
  ngAfterViewInit() {
    this.initMap();
    this.loadSignals();
  }

  initMap() {
    const baseMapURl = 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png'
    this.map = L.map('map', {
      center: [-15.47, -47.56],
      zoom: 7,
      zoomControl: false
    });

    L.tileLayer(baseMapURl).addTo(this.map);
  }

  loadSignals() {
    this.signals.push(new Signal('Signal 1', new Date(), 'Rua 4', 'Construção de ponte', 0, -15.47, -45.67, 1, 0, 0, 0));
    this.signals.push(new Signal('Signal 2', new Date(), 'Rua 5', 'Reparo de ponte', 1, -15., -45.67, 1, 0, 0, 0));
    this.signals.forEach((s) => {
      let m: L.CircleMarker = new L.CircleMarker(new L.LatLng(s.latitude!, s.longitude!), {
        radius: 10 * s.scaleFactor!
      });
      this.markers.push(m);
      m.addTo(this.map);
    });
  }

  loadStates() {
    this.addressService.getStates().subscribe((response) => {
      response.forEach((r) => {
        let u = new State();
        u.sigla = r['sigla'];
        u.id = r['id'];
        u.name = r['nome'];
        this.states?.push(u);
      });
    });
  }

  loadCities() {
    this.addressService.getCitiesByState(this.state?.id!).subscribe((response) => {
      response.forEach((r) => {
        let c = new City();
        c.name = r['nome'];
        c.cityId = r['id'];
        this.cities?.push(c);
      });
      this.filteredCities = Array.from(this.cities!);
    });
  }

  filterCities(event: AutoCompleteCompleteEvent) {

    let filtered: any[] = [];
    let query = event.query;

    for (let i = 0; i < (this.cities as City[]).length; i++) {
      let item = (this.cities as City[])[i];
      if (item.name!.toLowerCase().indexOf(query.toLowerCase()) == 0) {
        filtered.push(item);
      }
    }
    this.filteredCities = filtered;
  }

  filterStates(event: AutoCompleteCompleteEvent) {

    let filtered: any[] = [];
    let query = event.query;

    for (let i = 0; i < (this.states as any[]).length; i++) {
      let item = (this.states as any[])[i];
      if (item.name.toLowerCase().indexOf(query.toLowerCase()) == 0) {
        filtered.push(item);
      }
    }
    this.filteredStates = filtered;
  }

  changeState() {
    this.city = undefined;
    this.cities = [];
    this.filteredCities = [];
    this.loadCities();
    let address = this.state?.name! + ', Brazil';
    this.goToAdress(address, 7);
  }

  changeCity() {
    let address = this.city?.name + ', ' + this.state?.name! + ', Brazil';
    this.goToAdress(address, 13);
    this.selected = true;
    if (this.city?.rating == undefined) {
      this.city!.rating = 0;
    }
    this.rating = this.city?.rating;
  }

  selectSignal(index: number | undefined){
    this.selectedIndex = index;
    let coord = new L.LatLng(this.markers[index!].getLatLng().lat, this.markers[index!].getLatLng().lng);
    this.map.flyTo(coord, 15, {
      "animate": true,
      "duration": 3
    });
  }

  goToAdress(address: string, level: number) {
    this.addressService.getCoordinates(address).subscribe((response) => {
      let lat = ((response as object[])[0]['lat' as keyof Object] as unknown as number);
      let lng = ((response as object[])[0]['lon' as keyof Object] as unknown as number);
      let coord = new L.LatLng(lat, lng);
      this.map.flyTo(coord, level, {
        "animate": true,
        "duration": 3
      });
    });
  }
}
