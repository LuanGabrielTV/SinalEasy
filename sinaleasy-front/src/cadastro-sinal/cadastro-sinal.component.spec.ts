import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CadastroSinalComponent } from './cadastro-sinal.component';

describe('CadastroSinalComponent', () => {
  let component: CadastroSinalComponent;
  let fixture: ComponentFixture<CadastroSinalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CadastroSinalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CadastroSinalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
