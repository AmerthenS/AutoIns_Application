import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterOfficerComponent } from './register-officer.component';

describe('RegisterOfficerComponent', () => {
  let component: RegisterOfficerComponent;
  let fixture: ComponentFixture<RegisterOfficerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegisterOfficerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegisterOfficerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
