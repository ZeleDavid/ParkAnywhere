import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DodajUComponent } from './dodajU.component';

describe('DodajUComponent', () => {
    let component: DodajUComponent;
    let fixture: ComponentFixture<DodajUComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [DodajUComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(DodajUComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
