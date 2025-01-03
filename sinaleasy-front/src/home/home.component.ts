import { Component, ElementRef, HostListener, OnInit, signal, ViewChild } from '@angular/core';
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
  private changedVotes: Array<string>;

  constructor(private addressService: AddressService, private signalService: SignalService, private cityService: CityService, private homeService: HomeService, private router: Router) {
    this.signals = [];
    this.markers = [];
    this.changedVotes = [];
  }


  ngOnInit(): void {
    this.states = [];
    this.cities = [];
    this.loadStates();
    this.reloadLatestValues();

  }

  ngAfterViewInit() {
    this.initMap();
    if (this.city != undefined) {
      this.loadSignals();
    }
    this.filteredStates = [];
    this.filteredCities = [];
  }

  focusOnSignal(signal: Signal, index: number) {
    let coord = new L.LatLng(signal.latitude!, signal.longitude!);
    this.map!.flyTo(coord, this.map?.getZoom(), {
      "animate": true,
      "duration": 1
    });
  }


  reloadLatestValues() {
    let latestCity: City | undefined = this.homeService.getLatestCity();
    let latestState: State | undefined = this.homeService.getLatestState();
    if (latestCity != undefined && latestState != undefined) {
      this.state = latestState;
      this.cityService.getCityById(latestCity.cityId!).subscribe((city=>{
        this.city = city;
        this.rating = this.city.rating;
        let address = this.city?.name + ', ' + this.state?.name! + ', Brazil';
        this.flyToAddress(address, 13, false, 2);
        this.loadCities();
        this.loadSignals();
      }))
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
    this.signals = [];
    this.signalService.getSignalsByCity(this.city?.cityId!).subscribe((signals) => {
      this.signals = signals as unknown as Signal[];
      this.drawMarkers(this.signals);
      console.log(this.signals);
    })

  }

  drawMarkers(signalList: Signal[]) {
    this.markers = [];
    signalList.forEach((s) => {
      let m: L.CircleMarker = new L.CircleMarker(new L.LatLng(s.latitude!, s.longitude!), {
        radius: s.scaleFactor!,
        className: 'marker'
      });
      m.bindTooltip(s.name!);
      this.markers.push(m);
      setTimeout(() => {
        m.addTo(this.map!);
      }, 2500);
    });
  }

  loadStates() {
    this.states = [];
    this.addressService.getStates().subscribe((response) => {
      console.log(response);
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
    this.cities = [];
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
    console.log(query)
    this.filteredStates = [];
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
    this.rating = 0;
    this.cities = [];
    this.filteredCities = [];
    this.loadCities();
    this.homeService.setLatestState(this.state!);
    let address = this.state?.name! + ', Brazil';
    this.flyToAddress(address, 7, true, 2);
  }

  changeCity() {
    this.signals = [];
    this.markers.forEach((m) => {
      m.remove();
    })
    this.markers = [];
    if (this.city != undefined) {
      let address = this.city?.name + ', ' + this.state?.name! + ', Brazil';
      this.cityService.getCityById(this.city?.cityId!).subscribe((city => {
        this.city = city;
        this.rating = city.rating;
        this.homeService.setLatestCity(this.city!);
        this.loadSignals();
      }))
      this.flyToAddress(address, 13, true, 2);
    }

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
    this.router.navigateByUrl('/create-signal');
  }

  flyToAddress(address: string, level: number, animate: boolean, duration: number) {
    this.addressService.getCoordinates(address).subscribe((response) => {
      let lat = ((response as object[])[0]['lat' as keyof Object] as unknown as number);
      let lng = ((response as object[])[0]['lon' as keyof Object] as unknown as number);
      let coord = new L.LatLng(lat, lng);
      this.map!.flyTo(coord, level, {
        "animate": animate,
        "duration": duration
      });
    });
  }

  editSignal(s: Signal) {
    this.router.navigate(
      ['/update-signal'],
      { queryParams: { signalId: s.signalId } }
    );
  }

  viewSignal(s: Signal) {
    this.router.navigate(
      ['/view-signal'],
      { queryParams: { signalId: s.signalId } }
    );
  }

  likeSignal(s: Signal) {
    s.liked = !s.liked;
    let index = this.changedVotes.indexOf(s.signalId!);
    if (index == -1) {
      this.changedVotes.push(s.signalId!);
    } else {
      this.changedVotes.splice(index, 1);
    }
    // this.signalService.voteOnSignal(this.changedVotes);
  }

  @HostListener('window:beforeunload', ['$event'])
  updateLikes() {
    this.signalService.voteOnSignal(this.changedVotes);
  }

  ngOnDestroy() {
    this.signalService.voteOnSignal(this.changedVotes);
  }
}
