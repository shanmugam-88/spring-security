import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConversionvaluesComponent } from './conversionvalues.component';

describe('ConversionvaluesComponent', () => {
  let component: ConversionvaluesComponent;
  let fixture: ComponentFixture<ConversionvaluesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConversionvaluesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConversionvaluesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
