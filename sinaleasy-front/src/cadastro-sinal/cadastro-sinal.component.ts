import { AfterViewInit, Component, OnInit } from '@angular/core';
import { AddressService } from '../services/address.service';
import * as L from 'leaflet';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-cadastro-sinal',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './cadastro-sinal.component.html',
  styleUrl: './cadastro-sinal.component.scss'
})
export class CadastroSinalComponent implements OnInit, AfterViewInit {

  private map!: L.Map;
  ufs: UF[] = [];
  cidades: Cidade[] = [];
  cidade: Cidade | undefined;
  uf: UF | undefined;
  selectedUF: number = -1;
  selectedCidade: number = -1;
  brasiliaCoord: L.LatLng | undefined;

  constructor(private addressService: AddressService) { }

  ngOnInit() {
    this.brasiliaCoord = new L.LatLng(-15.47, -47.56);
    this.addressService.getUFs().subscribe((response) => {
      response.forEach((r) => {
        let u = new UF();
        u.sigla = r['sigla'];
        u.id = r['id'];
        u.nome = r['nome'];
        this.ufs.push(u);
      });
    });
  }

  ngAfterViewInit() {
    this.initMap();
  }

  changeUF() {
    this.selectedCidade = 0;
    this.cidades = [];
    this.map.panTo(this.brasiliaCoord!);
    let address = this.ufs[this.selectedUF]?.nome! + ', Brazil';
    this.goToAdress(address, 7);
    this.loadCidades();
  }

  changeCidade() {
    let address = this.cidades[this.selectedCidade].nome + ', ' + this.ufs[this.selectedUF]?.nome! + ', Brazil';
    this.goToAdress(address, 13);
  }

  loadCidades() {
    this.addressService.getCidadesByUF(this.ufs[this.selectedUF]?.id!).subscribe((response) => {
      response.forEach((r) => {
        let c = new Cidade();
        c.nome = r['nome'];
        c.id = r['id'];
        this.cidades.push(r);
      })
    });
  }

  private initMap() {
    const baseMapURl = 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png'
    this.map = L.map('map', {
      center: [-15.47, -47.56],
      zoom: 5
    });
    L.tileLayer(baseMapURl).addTo(this.map);
  }

  private goToAdress(address: string, level: number) {
    console.log(address);
    this.addressService.getCoordinates(address).subscribe((response) => {
      let lat = ((response as object[])[0]['lat' as keyof Object] as unknown as number);
      let lng = ((response as object[])[0]['lon' as keyof Object] as unknown as number);
      let coord = new L.LatLng(lat, lng);
      console.log(coord);
      this.map.panTo(coord);
      // this.map.setZoom(level);
      this.map.setZoomAround(coord, level);
    });
  }

}

class UF {
  sigla: string | undefined;
  id: string | undefined;
  nome: string | undefined;
}

class Cidade {
  nome: string | undefined;
  id: string | undefined;
}
