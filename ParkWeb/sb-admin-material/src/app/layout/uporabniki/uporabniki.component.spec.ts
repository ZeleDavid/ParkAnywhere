import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UporabnikiComponent } from './uporabniki.component';

describe('UporabnikiComponent', () => {
    let component: UporabnikiComponent;
    let fixture: ComponentFixture<UporabnikiComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [UporabnikiComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(UporabnikiComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
