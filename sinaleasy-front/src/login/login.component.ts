import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { User } from '../domain/User';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { Auth } from '../domain/Auth';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ButtonModule, FormsModule, ReactiveFormsModule, ToastModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  form: FormGroup;
  user: User;

  constructor(private fBuilder: FormBuilder, private router: Router, private userService: UserService, private messageService: MessageService) {
    this.user = new User();
    this.form = this.fBuilder.group({
      'login': [this.user.login, Validators.compose([
        Validators.required])],
      'password': [this.user.password, Validators.compose([
        Validators.required])
      ]
    });
  }

  onSubmit() {
    this.user = new User(this.form.get('email')?.value, this.form.get('password')?.value, this.form.get('login')?.value);
    let auth: Auth = new Auth(this.user);
    this.userService.login(auth).subscribe({
      next: (token: any) => {
        console.log(token);
        sessionStorage.setItem('token', JSON.stringify(token['token']));
        this.messageService.add({ severity: 'success', summary: 'Bem-vindo!', detail: '' });
        this.router.navigate(['/']);
      },
      error: (error) => {
        this.messageService.add({ severity: 'error', summary: 'Erro!', detail: 'As credenciais estão erradas.' });
      }
    });
  }

  goHome() {
    this.router.navigate(['/']);
  }
}
