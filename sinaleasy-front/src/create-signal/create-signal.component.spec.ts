import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateSignalComponent } from './create-signal.component';

describe('CreateSignalComponent', () => {
  let component: CreateSignalComponent;
  let fixture: ComponentFixture<CreateSignalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateSignalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateSignalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
