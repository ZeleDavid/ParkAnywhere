import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TransakcijeComponent } from './transakcije.component';

describe('TransakcijeComponent', () => {
    let component: TransakcijeComponent;
    let fixture: ComponentFixture<TransakcijeComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [TransakcijeComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(TransakcijeComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
