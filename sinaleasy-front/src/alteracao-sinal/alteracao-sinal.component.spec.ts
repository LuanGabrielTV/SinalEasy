import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AlteracaoSinalComponent } from './alteracao-sinal.component';

describe('AlteracaoSinalComponent', () => {
  let component: AlteracaoSinalComponent;
  let fixture: ComponentFixture<AlteracaoSinalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AlteracaoSinalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AlteracaoSinalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
