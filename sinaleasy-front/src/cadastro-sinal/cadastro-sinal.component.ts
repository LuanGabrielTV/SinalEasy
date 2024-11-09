import { AfterViewInit, Component, OnInit } from '@angular/core';
import { AddressService } from '../services/address.service';
import * as L from 'leaflet';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AutoCompleteCompleteEvent, AutoCompleteModule } from 'primeng/autocomplete';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';
import { Sinal } from '../domain/Sinal';
import { Cidade } from '../domain/Cidade';


@Component({
  selector: 'app-cadastro-sinal',
  standalone: true,
  imports: [FormsModule, CommonModule, AutoCompleteModule, DropdownModule, InputTextModule, ReactiveFormsModule],
  templateUrl: './cadastro-sinal.component.html',
  styleUrl: './cadastro-sinal.component.scss'
})
export class CadastroSinalComponent implements OnInit, AfterViewInit {

  private map!: L.Map;
  ufs: UF[] | undefined;
  cidades: Cidade[] | undefined;
  cidade: Cidade | undefined;
  uf: UF | undefined;
  selectedUF: number = -1;
  brasiliaCoord: L.LatLng | undefined;
  filteredCidades: any[] = [];
  filteredUFs: any[] = [];
  form: FormGroup;
  sinal: Sinal;
  marker: L.Marker | undefined;
  address: string;

  constructor(private fBuilder: FormBuilder, private addressService: AddressService) {
    this.sinal = new Sinal();
    this.form = this.fBuilder.group({
      'nome': [this.sinal.nome, Validators.compose([
        Validators.minLength(2),
        Validators.maxLength(255),
        Validators.required])],
      'tipo': [this.sinal.tipo, Validators.compose([
        Validators.required])],
      'data': [this.sinal.data, Validators.compose([
        Validators.required])],
      'estado': [this.cidade?.estado, Validators.compose([
        Validators.required])],
      'cidade': [this.cidade?.nome, Validators.compose([
        Validators.required])]
    });
    this.marker = undefined;
    this.address = '';
  }

  ngOnInit() {
    this.ufs = [];
    this.cidades = [];
    this.brasiliaCoord = new L.LatLng(-15.47, -47.56);
    this.addressService.getUFs().subscribe((response) => {
      response.forEach((r) => {
        let u = new UF();
        u.sigla = r['sigla'];
        u.id = r['id'];
        u.nome = r['nome'];
        this.ufs?.push(u);
      });
    });


  }


  getAddress(lat: number, lng: number) {
    this.addressService.getAddress(lat, lng).subscribe((response) => {
      this.address = (response['display_name' as keyof Object] as unknown as string);
    })
  }

  onSubmit() {

  }

  ngAfterViewInit() {
    this.initMap();
    this.map.on("click", (e) => {
      // this.addMarker(e);
    });

  }

  getLocationFromBrowser(){
    let location = navigator.geolocation.getCurrentPosition((pos) => {
      let coords = new L.LatLng(pos.coords.latitude, pos.coords.longitude);
      this.map.panTo(coords);
      this.addressService.getAddress(pos.coords.latitude, pos.coords.longitude).subscribe((response) => {
        let estado = response['address' as keyof object]['state'];
        this.ufs?.forEach((e) => {
          if (e.nome == estado) {
            this.uf = e;
            this.loadCidades();
          }
        })
      })


    }, () => {
      console.log('erro');
    });
  }

  filterCidades(event: AutoCompleteCompleteEvent) {

    let filtered: any[] = [];
    let query = event.query;

    for (let i = 0; i < (this.cidades as any[]).length; i++) {
      let item = (this.cidades as any[])[i];
      if (item.nome.toLowerCase().indexOf(query.toLowerCase()) == 0) {
        filtered.push(item);
      }
    }
    this.filteredCidades = filtered;
  }

  filterUFs(event: AutoCompleteCompleteEvent) {

    let filtered: any[] = [];
    let query = event.query;

    for (let i = 0; i < (this.ufs as any[]).length; i++) {
      let item = (this.ufs as any[])[i];
      if (item.nome.toLowerCase().indexOf(query.toLowerCase()) == 0) {
        filtered.push(item);
      }
    }
    this.filteredUFs = filtered;
  }

  changeUF() {
    this.cidade = undefined;
    this.cidades = [];
    this.map.panTo(this.brasiliaCoord!);
    let address = this.uf?.nome + ', Brazil';
    this.goToAdress(address, 7);
    this.loadCidades();
  }

  changeCidade() {
    let address = this.cidade?.nome + ', ' + this.uf?.nome! + ', Brazil';
    this.goToAdress(address, 13);
  }

  loadCidades() {
    this.addressService.getCidadesByUF(this.uf?.id!).subscribe((response) => {
      response.forEach((r) => {
        let c = new Cidade();
        c.nome = r['nome'];
        c.id = r['id'];
        this.cidades?.push(r);
      });
    });
  }

  private initMap() {
    const baseMapURl = 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png'
    this.map = L.map('map', {
      center: [-15.47, -47.56],
      zoom: 7
    });
    L.tileLayer(baseMapURl).addTo(this.map);
  }

  private goToAdress(address: string, level: number) {
    this.addressService.getCoordinates(address).subscribe((response) => {
      let lat = ((response as object[])[0]['lat' as keyof Object] as unknown as number);
      let lng = ((response as object[])[0]['lon' as keyof Object] as unknown as number);
      let coord = new L.LatLng(lat, lng);
      this.map.panTo(coord);
      this.map.setZoomAround(coord, level);
    });
  }

}

class UF {
  sigla: string | undefined;
  id: string | undefined;
  nome: string | undefined;
}

