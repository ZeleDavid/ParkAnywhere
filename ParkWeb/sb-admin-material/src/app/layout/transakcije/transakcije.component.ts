import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import {map, tap} from 'rxjs/operators';
import * as firebase from 'firebase';
import {HttpClient} from '@angular/common/http';
import * as _ from 'lodash';

interface Transakcija {
  id: any;
  blockId: any;
  amount: any;
  fee: any;
  timestamp: any;
}

@Component({
    selector: 'app-tables',
    templateUrl: './transakcije.component.html',
    styleUrls: ['./transakcije.component.scss']
})
export class TransakcijeComponent implements OnInit {
    displayedColumns = ['blockId', 'amount', 'fee', 'timestamp'];
    dataSource: MatTableDataSource<Transakcija>;

    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild(MatSort) sort: MatSort;

    constructor(private http: HttpClient) {
        this.naloziPodatke();
    }

    ngOnInit() {
    }

    naloziPodatke() {
      let seznam: Transakcija[] = new Array();
      this.http
        .get('http://45.77.58.205:4003/api/v2/wallets/PFfkYKD8uvXk7M7FY6kBTZdeWLpRcHABp3/transactions/received')
        .pipe(
          map(data => _.values(data)),
          tap(podatki => {this.dataSource = new MatTableDataSource(podatki[1]); console.log(podatki[1]); this.dataSource.paginator = this.paginator;
            this.dataSource.sort = this.sort; } )
        )
        .subscribe(preneseno => seznam = preneseno);
    }

    applyFilter(filterValue: string) {
        filterValue = filterValue.trim(); // Remove whitespace
        filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
        this.dataSource.filter = filterValue;
        if (this.dataSource.paginator) {
            this.dataSource.paginator.firstPage();
        }
    }
}
