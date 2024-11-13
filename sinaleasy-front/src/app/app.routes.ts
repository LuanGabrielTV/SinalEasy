import { Routes } from '@angular/router';
import { CadastroSinalComponent } from '../cadastro-sinal/cadastro-sinal.component';
import { HomeComponent } from '../home/home.component';
import { AlteracaoSinalComponent } from '../alteracao-sinal/alteracao-sinal.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'cadastro-sinal', component: CadastroSinalComponent },
    { path: 'alteracao-sinal', component: AlteracaoSinalComponent }
];
