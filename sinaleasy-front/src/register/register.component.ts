import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { User } from '../domain/User';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ButtonModule, FormsModule, ReactiveFormsModule, ToastModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

  form: FormGroup;
  user: User;

  constructor(private fBuilder: FormBuilder, private router: Router, private userService: UserService, private messageService: MessageService) {
    this.user = new User();
    this.form = this.fBuilder.group({
      'login': [this.user.login, Validators.compose([
        Validators.minLength(2),
        Validators.maxLength(255),
        Validators.required])],
      'password': [this.user.password, Validators.compose([
        Validators.minLength(8),
        Validators.maxLength(20),
        Validators.required])
      ],
      'email': [this.user.email, Validators.compose([
        Validators.required])]
    });
  }

  onSubmit() {
    this.user = new User(this.form.get('email')?.value, this.form.get('password')?.value, this.form.get('login')?.value);
    this.userService.register(this.user).subscribe({
      next: (response) => {
        this.router.navigate(['/login']);
      },
      error: (error) => {
        this.messageService.add({ severity: 'error', summary: 'Erro!', detail: 'Esse email já está cadastrado.' });
      }
    });
  }

  goHome() {
    this.router.navigate(['/']);
  }
}
