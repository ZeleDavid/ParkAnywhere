import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StatistikaComponent } from './statistika.component';

describe('StatistikaComponent', () => {
  let component: StatistikaComponent;
  let fixture: ComponentFixture<StatistikaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StatistikaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StatistikaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
