import { Component, OnInit } from '@angular/core';
import { AddressService } from '../services/address.service';

@Component({
  selector: 'app-cadastro-sinal',
  standalone: true,
  imports: [],
  templateUrl: './cadastro-sinal.component.html',
  styleUrl: './cadastro-sinal.component.scss'
})
export class CadastroSinalComponent implements OnInit {

  ufs = [];
  estado: string = '';

  constructor(private addressService: AddressService) { }

  ngOnInit() {
    this.addressService.getUFs().subscribe((response) => {
      response.forEach((r) => {
        this.ufs.push(r['sigla']);
      });
    });
  }

}
