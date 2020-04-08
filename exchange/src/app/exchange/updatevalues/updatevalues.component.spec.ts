import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdatevaluesComponent } from './updatevalues.component';

describe('UpdatevaluesComponent', () => {
  let component: UpdatevaluesComponent;
  let fixture: ComponentFixture<UpdatevaluesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpdatevaluesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdatevaluesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
