import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PregledComponent } from './pregled.component';

describe('PregledComponent', () => {
  let component: PregledComponent;
  let fixture: ComponentFixture<PregledComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PregledComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PregledComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
