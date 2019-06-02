import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DodajComponent } from './dodaj.component';

describe('DodajComponent', () => {
    let component: DodajComponent;
    let fixture: ComponentFixture<DodajComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [DodajComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(DodajComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
