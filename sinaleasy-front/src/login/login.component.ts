import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { User } from '../domain/User';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { Auth } from '../domain/Auth';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ButtonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  form: FormGroup;
  user: User;

  constructor(private fBuilder: FormBuilder, private router: Router, private userService:UserService) {
    this.user = new User();
    this.form = this.fBuilder.group({
      'login': [this.user.login, Validators.compose([
        Validators.required])],
      'password': [this.user.password, Validators.compose([
        Validators.required])
      ]});
  }

  onSubmit() {
    this.user = new User(this.form.get('email')?.value, this.form.get('password')?.value, this.form.get('login')?.value);
    let auth:Auth = new Auth(this.user);
    this.userService.login(auth).subscribe((token: any) =>{
      sessionStorage.setItem('token', JSON.stringify(token));
      this.router.navigate(['/']);
    })
  }

  goHome() {
    this.router.navigate(['/']);
  }
}
