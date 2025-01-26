import { Routes } from '@angular/router';
import { CreateSignalComponent } from '../create-signal/create-signal.component';
import { HomeComponent } from '../home/home.component';
import { UpdateSignalComponent } from '../update-signal/update-signal.component';
import { ViewSignalComponent } from '../view-signal/view-signal.component';
import { RegisterComponent } from '../register/register.component';
import { LoginComponent } from '../login/login.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'create-signal', component: CreateSignalComponent },
    { path: 'update-signal', component: UpdateSignalComponent },
    { path: 'view-signal', component: ViewSignalComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'login', component: LoginComponent }
];
