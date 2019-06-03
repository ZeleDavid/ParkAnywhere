import { UporabnikiModule } from './uporabniki.module';

describe('UporabnikiModule', () => {
    let tablesModule: UporabnikiModule;

    beforeEach(() => {
        tablesModule = new UporabnikiModule();
    });

    it('should create an instance', () => {
        expect(tablesModule).toBeTruthy();
    });
});
