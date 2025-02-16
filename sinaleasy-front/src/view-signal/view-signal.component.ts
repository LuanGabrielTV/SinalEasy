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
import { Status } from '../domain/Status';
import { RatingModule } from 'primeng/rating';
import { ProgressSpinnerModule } from 'primeng/progressspinner';

@Component({
  selector: 'app-view-signal',
  standalone: true,
  imports: [TimelineModule, FormsModule, ReactiveFormsModule, CommonModule, RouterModule, RatingModule, ProgressSpinnerModule],
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
  data: any;
  status = Status;
  events: any[] | undefined;
  isLoadingSignal = true;

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
        this.events = [];
        this.signal.signalMilestones?.forEach((m)=>{
          this.events?.push({status: this.status[m.status!], date:formatDate(m.statusUpdateTime!,'dd/MM/yyyy','en-US')});
        });
        this.cityService.getCityById(this.signal.cityId!).subscribe((res) => {
          this.city = res;
    this.initMap();
    this.loadData();
        })
      });
    });
  }

  loadData() {
    this.isLoadingSignal = false;
    this.form.get('typeOfSignal')?.setValue(this.types[this.signal.typeOfSignal!]);
    this.form.get('date')?.setValue(formatDate(this.signal.date!, 'yyyy-MM-dd', 'en'));
    this.form.get('city')?.setValue(this.city!.name);
    this.form.get('state')?.setValue(this.city!.state);
    this.form.get('description')?.setValue(this.signal.description);
    this.form.get('address')?.setValue(this.signal.address!);
    this.form.get('status')?.setValue(this.status[this.signal.status!]);
    this.marker = new L.CircleMarker(new L.LatLng(this.signal.latitude!, this.signal.longitude!), {
      radius: 10 * this.signal.scaleFactor!
    });
    this.marker.addTo(this.map);
    this.marker.bindTooltip(this.signal.address!);

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
