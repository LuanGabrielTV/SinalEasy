import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { AddressService } from '../services/address.service';
import * as L from 'leaflet';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule, formatDate } from '@angular/common';
import { AutoCompleteCompleteEvent, AutoCompleteModule } from 'primeng/autocomplete';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';
import { Signal } from '../domain/Signal';
import { City } from '../domain/City';
import { MultiSelectModule } from 'primeng/multiselect';
import { DividerModule } from 'primeng/divider';
import { PrimeIcons, MenuItem } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { State } from '../domain/State';
import { SignalType } from '../domain/SignalType';
import { SignalService } from '../services/signal.service';
import { Router, RouterModule } from '@angular/router';
import { HomeService } from '../services/home.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-create-signal',
  standalone: true,
  imports: [FormsModule, CommonModule, AutoCompleteModule, DropdownModule, InputTextModule, ReactiveFormsModule, DividerModule, ButtonModule, ToastModule, RouterModule],
  templateUrl: './create-signal.component.html',
  styleUrl: './create-signal.component.scss'
})
export class CreateSignalComponent implements OnInit, AfterViewInit {

  @ViewChild('mapContainer')
  private mapContainer!: ElementRef;
  private map!: L.Map;
  states: State[] | undefined;
  cities: City[] | undefined;
  city: City | undefined;
  brasiliaCoord: L.LatLng | undefined;
  filteredCities: City[] = [];
  filteredStates: State[] = [];
  form: FormGroup;
  signal: Signal;
  marker: L.CircleMarker | undefined;
  address: string;
  types = ['Construção', 'Reparo', 'Limpeza', 'Meio-ambiente', 'Saúde'];
  signalTypes = SignalType;
  userId: string | undefined;

  constructor(private fBuilder: FormBuilder, private addressService: AddressService, private signalService: SignalService, private homeService: HomeService, private userService: UserService, private router: Router) {
    this.signal = new Signal();
    this.form = this.fBuilder.group({
      'name': [this.signal.name, Validators.compose([
        Validators.minLength(2),
        Validators.maxLength(255),
        Validators.required])],
      'typeOfSignal': [this.signal.typeOfSignal, Validators.compose([
        Validators.required])],
      'date': [this.signal.date, Validators.compose([
        Validators.required])],
      'state': [this.city?.state],
      'city': [this.city?.name],
      'description': [this.signal.description, Validators.compose([
        Validators.required])],
    });
    this.marker = undefined;
    this.address = '';
  }

  ngOnInit() {

    if (this.userService.getToken() == null) {
      this.router.navigate(['/login']);
    }else{
      this.userId = this.userService.getToken()["userId"];
    }

    this.states = [];
    this.cities = [];
    this.brasiliaCoord = new L.LatLng(-15.47, -47.56);
    this.loadStates();
  }

  ngAfterViewInit() {
    this.initMap();
    this.map.on("click", (e) => {
      this.addMarker(e);
    });
    this.form.get('date')?.setValue(formatDate(new Date(), 'yyyy-MM-dd', 'en'));
  }

  initMap() {
    const baseMapURl = 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png'
    this.map = L.map(this.mapContainer.nativeElement, {
      center: [-15.47, -47.56],
      zoom: 7
    });

    L.tileLayer(baseMapURl).addTo(this.map);
  }

  addMarker(e: L.LeafletMouseEvent) {
    if (this.marker == undefined) {
      this.marker = L.circleMarker([e.latlng.lat, e.latlng.lng]);
      this.marker.addTo(this.map);
    }
    this.marker.setLatLng(e.latlng);
    this.marker.redraw();

    this.loadAddress(e.latlng.lat, e.latlng.lng);
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
    this.addressService.getCitiesByState(this.form.get('state')?.value?.id!).subscribe((response) => {
      response.forEach((r) => {
        let c = new City();
        c.name = r['nome'];
        c.cityId = String(r['id']);
        this.cities?.push(c);
      });
      this.filteredCities = Array.from(this.cities!);
    });
  }

  goToAdress(address: string, level: number) {
    this.addressService.getCoordinates(address).subscribe((response) => {
      let lat = ((response as object[])[0]['lat' as keyof Object] as unknown as number);
      let lng = ((response as object[])[0]['lon' as keyof Object] as unknown as number);
      let coord = new L.LatLng(lat, lng);
      // this.map.panTo(coord);
      // this.map.setZoomAround(coord, level);
      this.map.flyTo(coord, level, {
        "animate": true,
        "duration": 3
      });
    });
  }

  getAddress(lat: number, lng: number) {
    this.addressService.getAddress(lat, lng).subscribe((response) => {
      this.address = (response['display_name' as keyof Object] as unknown as string);
    })
  }

  loadAddress(lat: number, lng: number) {
    this.addressService.getAddress(lat, lng).subscribe((response) => {
      let responseAddress = response['address' as keyof Object];
      let keys = ([Object.keys(responseAddress)[0], Object.keys(responseAddress)[1]])
      this.address = (responseAddress[keys[0] as keyof Object] as unknown as string) + ', ' + (responseAddress[keys[1] as keyof Object] as unknown as string);
      this.loadStateFromAddress(response);
    });
  }

  loadStateFromAddress(response: Object) {
    let estado = response['address' as keyof object]['state'];
    this.states?.forEach((e) => {
      if (e.name == estado) {
        this.form.get('state')?.setValue(e);
        this.loadCities();
      }
    })
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
    this.form.get('city')?.reset();
    this.cities = [];
    this.filteredCities = [];
    this.loadCities();
    let address = this.form.get('state')?.value?.name + ', Brazil';
    this.goToAdress(address, 7);
  }

  changeCity() {
    let address = this.form.get('city')?.value?.name + ', ' + this.form.get('state')?.value?.name! + ', Brazil';
    this.goToAdress(address, 13);
    this.city = this.form.get('city')?.value;
  }

  onSubmit() {
    this.signal = new Signal(this.form.get('name')?.value!, this.form.get('date')?.value!, this.address, this.form.get('description')?.value!, this.signalTypes[this.form.get('typeOfSignal')?.value] as unknown as number, this.marker?.getLatLng().lat!, this.marker?.getLatLng().lng!, 1, 0, 0, this.city?.cityId!);
    this.signalService.createSignal(this.signal).subscribe(_ => {
      this.homeService.setLatestCity(this.city!);
      this.homeService.setLatestState(this.form.get('state')?.value);
      this.goHome();
    });
  }

  goHome() {
    this.router.navigate(['/']);
  }
}