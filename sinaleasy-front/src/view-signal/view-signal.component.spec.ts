import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewSignalComponent } from './view-signal.component';

describe('ViewSignalComponent', () => {
  let component: ViewSignalComponent;
  let fixture: ComponentFixture<ViewSignalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewSignalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewSignalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
