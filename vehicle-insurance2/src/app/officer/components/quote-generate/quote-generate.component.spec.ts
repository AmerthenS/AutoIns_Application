import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuoteGenerateComponent } from './quote-generate.component';

describe('QuoteGenerateComponent', () => {
  let component: QuoteGenerateComponent;
  let fixture: ComponentFixture<QuoteGenerateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ QuoteGenerateComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(QuoteGenerateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
