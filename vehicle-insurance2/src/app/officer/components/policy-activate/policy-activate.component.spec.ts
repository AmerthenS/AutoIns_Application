import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PolicyActivateComponent } from './policy-activate.component';

describe('PolicyActivateComponent', () => {
  let component: PolicyActivateComponent;
  let fixture: ComponentFixture<PolicyActivateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PolicyActivateComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PolicyActivateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
