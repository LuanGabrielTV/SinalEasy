import { AfterViewInit, Component, OnInit } from '@angular/core';
import { Signal } from '../domain/Signal';
import * as L from 'leaflet';
import { animate } from '@angular/animations';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { AutoCompleteCompleteEvent, AutoCompleteModule } from 'primeng/autocomplete';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';
import { City } from '../domain/City';
import { CityService } from '../services/city.service';
import { DividerModule } from 'primeng/divider';
import { AddressService } from '../services/address.service';
import { State } from '../domain/State';
import { SignalType } from '../domain/SignalType';
import { ButtonModule } from 'primeng/button';
import { CommonModule, formatDate } from '@angular/common';
import { StepsModule } from 'primeng/steps';
import { MenuItem } from 'primeng/api';
import { SignalService } from '../services/signal.service';
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'app-alteracao-sinal',
  standalone: true,
  imports: [FormsModule, CommonModule, AutoCompleteModule, DropdownModule, InputTextModule, ReactiveFormsModule, DividerModule, ButtonModule, StepsModule],
  templateUrl: './alteracao-sinal.component.html',
  styleUrl: './alteracao-sinal.component.scss'
})
export class AlteracaoSinalComponent implements OnInit, AfterViewInit {
  signal: Signal;
  city: City | undefined;
  private map!: L.Map;
  form: FormGroup;
  states: State[] | undefined;
  cities: City[] | undefined;
  filteredCities: City[] = [];
  filteredStates: State[] = [];
  marker: L.CircleMarker | undefined;
  address: string;
  types = ['Construção', 'Reparo', 'Limpeza', 'Meio-ambiente', 'Saúde'];
  statusList = ['Pendente', 'Inicializado', 'Paralisado', 'Finalizado'];
  items: MenuItem[] | undefined;
  signalTypes = SignalType;
  status: number;
  readonly = true;

  constructor(private fBuilder: FormBuilder, private cityService: CityService, private addressService: AddressService, private signalService: SignalService, private route: ActivatedRoute, private router: Router) {
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
    this.address = "";
    this.status = 0;
  }

  ngOnInit() {
    this.readonly = true;
    this.states = [];
    this.cities = [];
    this.items = [
      {
        label: 'Pendente',
        command: (event: any) => { this.status = 0 }
      },
      {
        label: 'Inicializado',
        command: (event: any) => { this.status = 1 }
      },
      {
        label: 'Paralisado',
        command: (event: any) => { this.status = 2 }
      },
      {
        label: 'Finalizado',
        command: (event: any) => { this.status = 3 }
      }
    ];

  }

  ngAfterViewInit() {
    this.route.queryParams.subscribe(params => {
      this.signalService.getSignalById(params['signalId']).subscribe((res) => {
        this.signal = res;
        this.cityService.getCityById(this.signal.cityId!).subscribe((res) => {
          this.city = res;
          this.initMap();
          this.loadStates();
          this.loadData();
        })
      });
    });

  }

  changeMarker(e: L.LeafletMouseEvent) {
    this.marker?.setLatLng(e.latlng);
    this.marker?.redraw();
    this.loadAddress(e.latlng.lat, e.latlng.lng);
  }

  loadData() {
    this.form.get('name')?.setValue(this.signal.name);
    this.form.get('typeOfSignal')?.setValue(this.types[this.signal.typeOfSignal!]);
    this.form.get('date')?.setValue(formatDate(this.signal.date!, 'yyyy-MM-dd', 'en'));
    this.form.get('city')?.setValue(this.city);
    this.form.get('description')?.setValue(this.signal.description);
    this.address = this.signal.address!;
    this.status = this.signal.status!;
    this.form.get('status')?.setValue(this.status);
    this.marker = new L.CircleMarker(new L.LatLng(this.signal.latitude!, this.signal.longitude!), {
      radius: 10 * this.signal.scaleFactor!
    });
    this.marker.addTo(this.map);
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
      this.states?.forEach((e) => {
        if (e.sigla == this.city!.state) {
          this.form.get('state')?.setValue(e);
          this.loadCities();
        }
      })
    });
  }

  loadCities() {
    this.addressService.getCitiesByState(this.form.get('state')?.value?.id!).subscribe((response) => {
      response.forEach((r) => {
        let c = new City();
        c.name = r['nome'];
        c.cityId = r['id'];
        this.cities?.push(c);
      });
      this.filteredCities = Array.from(this.cities!);
    });
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

  changeStatus() {
    this.form.get('status')?.setValue(this.status);
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

  initMap() {
    if (document.getElementsByClassName('map-frame')[0].innerHTML.length > 1000) {
      window.location.reload();
    }
    const baseMapURl = 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png'
    this.map = L.map('map', {
      center: [this.signal.latitude!, this.signal.longitude!],
      zoom: 12,
      zoomControl: false
    });
    this.map.on("click", (e) => {
      this.changeMarker(e);
    });

    L.tileLayer(baseMapURl).addTo(this.map);
  }

  goHome(){
    this.router.navigate(['/']);
  }

  onSubmit() {
    this.signal.name = this.form.get('name')?.value;
    this.signal.address = this.address;
    this.signal.typeOfSignal = this.signalTypes[this.form.get('typeOfSignal')?.value] as unknown as number;
    this.signal.date = this.form.get('date')?.value;
    this.signal.description = this.form.get('description')?.value;
    this.signal.status = this.status;
    this.signal.cityId = this.city?.cityId;
    this.signal.latitude = this.marker?.getLatLng().lat!;
    this.signal.longitude = this.marker?.getLatLng().lng!;
    this.signalService.updateSignal(this.signal).subscribe(_=>{
      this.goHome();
    });
  }

}
