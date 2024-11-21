import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
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
import { SignalService } from '../services/signal.service';
import { CityService } from '../services/city.service';
import { Router, RouterModule } from '@angular/router';
import { HomeService } from '../services/home.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, FormsModule, AutoCompleteModule, ButtonModule, RatingModule, RouterModule],
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
  selected = false;
  rating: number | undefined;
  signals: Signal[];
  signalType = SignalType;
  status = Status;
  markers: L.CircleMarker[];
  selectedIndex: number | undefined;
  @ViewChild('mapContainer')
  private mapContainer!: ElementRef;
  private map: L.Map | undefined;

  constructor(private addressService: AddressService, private signalService: SignalService, private cityService: CityService, private homeService: HomeService, private router: Router) {
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
    this.reloadLatestValues();
  }

  focusOnSignal(signal: Signal){
    let coord = new L.LatLng(signal.latitude!, signal.longitude!);
    this.map!.flyTo(coord, 15, {
      "animate": true,
      "duration": 1
    });
  }

  reloadLatestValues() {
    let latestCity: City | undefined = this.homeService.getLatestCity();
    let latestState: State | undefined = this.homeService.getLatestState();
    if (latestCity != undefined && latestState != undefined) {
      this.city = latestCity;
      this.state = latestState;
      let address = this.city?.name + ', ' + this.state?.name! + ', Brazil';
      this.goToAdress(address, 13);
      setTimeout(() => {
        this.loadSignals();
      }, 3000);
    }
  }

  initMap() {
    const baseMapURl = 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png'
    this.map = L.map(this.mapContainer.nativeElement, {
      center: [-15.47, -47.56],
      zoom: 7,
      zoomControl: false
    });
    L.tileLayer(baseMapURl).addTo(this.map);
  }

  loadSignals() {
    this.cityService.getCityById(this.city?.cityId!).subscribe((city => {
      if (city != null) {
        this.signalService.getSignalsByCity(this.city?.cityId!).subscribe((signs) => {
          this.signals = signs as unknown as Signal[];
          console.log(this.signals)
          this.signals.forEach((s) => {
            let m: L.CircleMarker = new L.CircleMarker(new L.LatLng(s.latitude!, s.longitude!), {
              radius: 10 * s.scaleFactor!
            });
            m.bindTooltip(s.name!);
            this.markers.push(m);
            m.addTo(this.map!);
          });
        })
      }
    }));
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
    this.filteredStates = Array.from(this.states!);
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
    this.homeService.setLatestState(this.state!);
    let address = this.state?.name! + ', Brazil';
    this.goToAdress(address, 7);
  }

  changeCity() {
    this.signals = [];
    this.markers.forEach((m) => {
      m.remove();
    })
    this.markers = [];
    let address = this.city?.name + ', ' + this.state?.name! + ', Brazil';
    if (this.city?.rating == undefined) {
      this.city!.rating = 0;
    }
    this.rating = this.city?.rating;
    this.selected = true;
    this.homeService.setLatestCity(this.city!);
    this.goToAdress(address, 13);
    setTimeout(() => {
      this.loadSignals();
    }, 3000);
  }

  selectSignal(index: number | undefined) {
    this.selectedIndex = index;
    let coord = new L.LatLng(this.markers[index!].getLatLng().lat, this.markers[index!].getLatLng().lng);
    this.map!.flyTo(coord, 15, {
      "animate": true,
      "duration": 3
    });
  }

  goToCadastroSignal() {
    this.router.navigateByUrl('/cadastro-sinal');
  }

  goToAdress(address: string, level: number) {
    this.addressService.getCoordinates(address).subscribe((response) => {
      let lat = ((response as object[])[0]['lat' as keyof Object] as unknown as number);
      let lng = ((response as object[])[0]['lon' as keyof Object] as unknown as number);
      let coord = new L.LatLng(lat, lng);
      this.map!.flyTo(coord, level, {
        "animate": true,
        "duration": 3
      });
    });
  }

  editSignal(s: Signal) {
    this.router.navigate(
      ['/alteracao-sinal'],
      { queryParams: { signalId: s.signalId } }
    );
    // this.router.navigate(['/alteracao-sinal'],{ queryParams: { signalId: s.signalId } });
  }
}
