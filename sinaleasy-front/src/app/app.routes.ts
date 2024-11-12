import { Routes } from '@angular/router';
import { CadastroSinalComponent } from '../cadastro-sinal/cadastro-sinal.component';
import { HomeComponent } from '../home/home.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'cadastro-sinal', component: CadastroSinalComponent }
];
