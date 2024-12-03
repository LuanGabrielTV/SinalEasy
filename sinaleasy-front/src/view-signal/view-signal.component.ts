import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Signal } from '../domain/Signal';
import { CityService } from '../services/city.service';
import { SignalService } from '../services/signal.service';
import { City } from '../domain/City';
import * as L from 'leaflet';
import { CommonModule, formatDate } from '@angular/common';
import { TimelineModule } from 'primeng/timeline';

@Component({
  selector: 'app-view-signal',
  standalone: true,
  imports: [TimelineModule, FormsModule, ReactiveFormsModule, CommonModule, RouterModule],
  templateUrl: './view-signal.component.html',
  styleUrl: './view-signal.component.scss'
})
export class ViewSignalComponent {

  signal: Signal;
  city: City | undefined;
  form: FormGroup;
  marker: L.CircleMarker | undefined;
  private map!: L.Map;
  types = ['Construção', 'Reparo', 'Limpeza', 'Meio-ambiente', 'Saúde'];
  statusList = ['Pendente', 'Inicializado', 'Paralisado', 'Finalizado'];
  data: any;
  events: any[] | undefined;

  constructor(private fBuilder: FormBuilder, private cityService: CityService, private signalService: SignalService, private route: ActivatedRoute, private router: Router) {
    this.signal = new Signal();
    this.form = this.fBuilder.group({
      'name': [this.signal.name],
      'typeOfSignal': [this.signal.typeOfSignal],
      'date': [this.signal.date],
      'state': [this.city?.state],
      'city': [this.city?.name],
      'description': [this.signal.description],
      'address': [this.signal.address],
      'status': [this.signal.status]
    });
  }

  ngAfterViewInit() {
    this.route.queryParams.subscribe(params => {
      if (params['signalId'] == null || params['signalId'] == undefined) {
        this.goHome();
        return;
      }
      this.signalService.getSignalById(params['signalId']).subscribe((res) => {
        this.signal = res;
        console.log(this.signal)
        this.cityService.getCityById(this.signal.cityId!).subscribe((res) => {
          this.city = res;
          this.initMap();
          this.loadData();
        })
      });
    });

  }

  loadData() {
    this.form.get('name')?.setValue(this.signal.name);
    this.form.get('typeOfSignal')?.setValue(this.types[this.signal.typeOfSignal!]);
    this.form.get('date')?.setValue(formatDate(this.signal.date!, 'yyyy-MM-dd', 'en'));
    this.form.get('city')?.setValue(this.city!.name);
    this.form.get('state')?.setValue(this.city!.state);
    this.form.get('description')?.setValue(this.signal.description);
    this.form.get('address')?.setValue(this.signal.address!);
    this.form.get('status')?.setValue(this.statusList[this.signal.status!]);
    this.marker = new L.CircleMarker(new L.LatLng(this.signal.latitude!, this.signal.longitude!), {
      radius: 10 * this.signal.scaleFactor!
    });
    this.marker.addTo(this.map);
    this.marker.bindTooltip(this.signal.address!);
    this.events = [
      { status: 'Ordered', date: '15/10/2020 10:30', icon: 'pi pi-shopping-cart', color: '#9C27B0', image: 'game-controller.jpg' },
      { status: 'Processing', date: '15/10/2020 14:00', icon: 'pi pi-cog', color: '#673AB7' },
      { status: 'Shipped', date: '15/10/2020 16:15', icon: 'pi pi-shopping-cart', color: '#FF9800' },
      { status: 'Delivered', date: '16/10/2020 10:00', icon: 'pi pi-check', color: '#607D8B' }
    ];
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

    L.tileLayer(baseMapURl).addTo(this.map);
  }

  goHome() {
    this.router.navigate(['/']);
  }


}
