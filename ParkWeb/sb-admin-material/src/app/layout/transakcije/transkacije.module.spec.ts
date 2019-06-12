import { TransakcijeModule } from './transakcije.module';

describe('TransakcijeModule', () => {
    let tablesModule: TransakcijeModule;

    beforeEach(() => {
        tablesModule = new TransakcijeModule();
    });

    it('should create an instance', () => {
        expect(tablesModule).toBeTruthy();
    });
});
